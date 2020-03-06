package com.infor.carRental.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infor.carRental.exception.CarIsNotAvailableException;
import com.infor.carRental.exception.EntityAlreadyRegisteredException;
import com.infor.carRental.exception.EntityAlreadyRegisteredException.EntityType;
import com.infor.carRental.exception.EntityIsNotRegisteredException;
import com.infor.carRental.model.Car;
import com.infor.carRental.model.Rental;
import com.infor.carRental.model.User;

@RestController
public class CarRentalController {
	// Thread safe list
	private CopyOnWriteArrayList<Car> allCars = new CopyOnWriteArrayList<>();
	private CopyOnWriteArrayList<User> allUsers = new CopyOnWriteArrayList<>();
	private CopyOnWriteArrayList<Rental> allRentals = new CopyOnWriteArrayList<>();

	private static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	@PostMapping("/register/car")
	public int registerCar(@RequestBody Map<String, String> params) {
		String plate = params.get("plate");

		if (allCars.stream().anyMatch(oneCar -> oneCar.getPlate().equals(plate))) {
			throw new EntityAlreadyRegisteredException(EntityType.CAR, plate);
		}

		Car newCar = new Car(plate);
		allCars.addIfAbsent(newCar);

		return newCar.getId();
	}

	@PostMapping("/register/user")
	public int registerUser(@RequestBody Map<String, String> params) {
		String name = params.get("name");

		if (allUsers.stream().anyMatch(oneUser -> oneUser.getName().equals(name))) {
			throw new EntityAlreadyRegisteredException(EntityType.USER, name);
		}

		User newUser = new User(name);
		allUsers.addIfAbsent(newUser);

		return newUser.getId();
	}

	@PostMapping("/register/car/availablity")
	public void registerCarAvailablity(@RequestBody Map<String, String> params) {
		int carId = Integer.valueOf(params.get("id"));		
		LocalDateTime availableFrom = LocalDateTime.parse(params.get("availableFrom"),
				DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		LocalDateTime availableTo = LocalDateTime.parse(params.get("availableTo"),
				DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		int price = Integer.valueOf(params.get("rentalPrice"));
		
		// If the car is registered
		if (allCars.stream().anyMatch(oneCar -> oneCar.getId() == carId)) {
			allCars.stream().filter(oneCar -> oneCar.getId() == carId).forEach(oneCar -> {
				oneCar.setAvailable(availableFrom, availableTo);
				oneCar.setRentalPrice(price);
			});
		} else {
			throw new EntityIsNotRegisteredException(EntityType.CAR, carId);
		}
	}

	@GetMapping("/search/cars")
	public CopyOnWriteArrayList<Car> searchAvailableCars(@RequestBody Map<String, String> params) {
		// No format checking here
		LocalDateTime availableFrom = LocalDateTime.parse(params.get("availableFrom"),
				DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		LocalDateTime availableTo = LocalDateTime.parse(params.get("availableTo"),
				DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		int maxRentalPrice = Integer.parseInt(params.get("maxRentalPrice"));

		return allCars.stream().filter(car -> car.isAvailable(availableFrom, availableTo))
				.filter(car -> car.getRentalPrice().get() <= maxRentalPrice)
				.collect(Collectors.toCollection(CopyOnWriteArrayList::new));
	}

	@PostMapping("/book/car")
	public void bookCar(@RequestBody Map<String, String> params) {
		// No format checking here
		LocalDateTime bookFrom = LocalDateTime.parse(params.get("bookFrom"),
				DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		LocalDateTime bookTo = LocalDateTime.parse(params.get("bookTo"),
				DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
		int carId = Integer.valueOf(params.get("carId"));	
		int userId = Integer.valueOf(params.get("userId"));	
		
		if (allCars.stream().noneMatch(car -> car.getId() == carId)) {
			throw new EntityIsNotRegisteredException(EntityType.CAR, carId);
		}

		if (allUsers.stream().noneMatch(user -> user.getId() == userId)) {
			throw new EntityIsNotRegisteredException(EntityType.USER, userId);
		}

		// check if the car is available during the requested time slot
		Optional<Car> car = allCars.stream()
			.filter(oneCar -> oneCar.getId() == carId && oneCar.isAvailable(bookFrom, bookTo))
			.findFirst();
		
		if (!car.isPresent()) {
			//TODO: better error message
			throw new CarIsNotAvailableException(carId, "during period " + bookFrom + " to " + bookTo);
		}
		
		if (!car.get().rent(bookFrom, bookTo)) {
			//TODO: better error message
			throw new CarIsNotAvailableException(carId, "failed to reserve");
		}
		
		Rental newRental = new Rental(carId, userId, bookFrom, bookTo);
		int price = car.get().getRentalPrice().get();
		newRental.setPrice(price);
		
		allRentals.addIfAbsent(newRental);
	}
	
}
