package com.infor.carRental.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Car {
	private int id;
	private String plate;
	private Optional<Integer> rentalPrice = Optional.empty();
	private Optional<Availabilities> availablities = Optional.empty();
	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	public Car(){
		super();
	}
	
	public Car(String plate) {
		this.id = atomicInteger.incrementAndGet();
		this.plate = plate;
	}

	public int getId() {
		return id;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Optional<Integer> getRentalPrice() {
		return rentalPrice;
	}

	public void setRentalPrice(int rentalPrice) {
		this.rentalPrice = Optional.of(rentalPrice);
	}

//	public Optional<LocalDateTime> getAvailableFrom() {
//		return availableFrom;
//	}
//
//	public void setAvailableFrom(Optional<LocalDateTime> availableFrom) {
//		this.availableFrom = availableFrom;
//	}
//
//	public Optional<LocalDateTime> getAvailableTo() {
//		return availableTo;
//	}
//
//	public void setAvailableTo(Optional<LocalDateTime> availableTo) {
//		this.availableTo = availableTo;
//	}
	
	public void setAvailable(LocalDateTime from, LocalDateTime to) {
		if (!this.availablities.isPresent()) {
			this.availablities = Optional.of(new Availabilities());
		}
		
		this.availablities.get().initialize(from, to);
	}
	
	public void offline() {
		if (this.availablities.isPresent()) {
			this.availablities.get().clear();
		}
	}
	
	public boolean rent(LocalDateTime from, LocalDateTime to) {
		return this.availablities.get().reserve(from, to);
	}
	
	public boolean isAvailable(LocalDateTime from, LocalDateTime to) {
		if (this.availablities.isPresent()) {
			return this.availablities.get().isAvailable(from, to);
		}
		
		return false;
	}
}
