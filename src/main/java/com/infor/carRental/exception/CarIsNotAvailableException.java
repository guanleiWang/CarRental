package com.infor.carRental.exception;

import com.infor.carRental.exception.EntityAlreadyRegisteredException.EntityType;

public class CarIsNotAvailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CarIsNotAvailableException(int id, String reason) {
		super("car with the id: '" + id + "' is not available due to: " + reason);
	}
}
