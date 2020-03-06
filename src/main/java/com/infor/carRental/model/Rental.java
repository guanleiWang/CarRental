package com.infor.carRental.model;

import java.time.LocalDateTime;

public class Rental {
	private int carId;
	private int userId;
	private LocalDateTime bookFrom;
	private LocalDateTime bookTo;
	private int price;

	public Rental(){
		super();
	}
	
	public Rental(int carId, int userId, LocalDateTime from, LocalDateTime to){
		this.carId = carId;
		this.userId = userId;
		this.bookFrom = from;
		this.bookTo = to;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalDateTime getFrom() {
		return bookFrom;
	}

	public void setFrom(LocalDateTime from) {
		this.bookFrom = from;
	}

	public LocalDateTime getTo() {
		return bookTo;
	}

	public void setTo(LocalDateTime to) {
		this.bookTo = to;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
