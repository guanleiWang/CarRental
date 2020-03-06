package com.infor.carRental.exception;

import com.infor.carRental.exception.EntityAlreadyRegisteredException.EntityType;

public class EntityIsNotRegisteredException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityIsNotRegisteredException(EntityType type, int id) {
		super(type + " with the id: '" + id + "' is not registered yet!");
	}
}
