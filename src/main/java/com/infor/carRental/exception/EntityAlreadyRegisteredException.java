package com.infor.carRental.exception;

public class EntityAlreadyRegisteredException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityAlreadyRegisteredException(EntityType type, String property) {
		super(type + "with the same property: '" + property + "' alreay registered!");
	}
	
	public enum EntityType{
		CAR,
		USER
	}
}
