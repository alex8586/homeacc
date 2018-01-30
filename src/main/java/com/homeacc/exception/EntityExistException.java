package com.homeacc.exception;

public class EntityExistException extends IllegalArgumentException {

	public EntityExistException(String message) {
		super(message);
	}
}
