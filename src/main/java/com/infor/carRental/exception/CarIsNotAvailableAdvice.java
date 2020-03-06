package com.infor.carRental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CarIsNotAvailableAdvice {
	@ResponseBody
	@ExceptionHandler(CarIsNotAvailableException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public String carIsNotAvailable(CarIsNotAvailableException e) {
		return e.getMessage();
	}
}

