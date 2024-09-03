package com.vehiclerentalservices.app.exceptions;

public class PasswordNotFoundException extends RuntimeException{
	
	    public PasswordNotFoundException() {
	        super(String.format("Invalid username or password"));
	    }

	    public PasswordNotFoundException(String message) {
	        super(message);
	    }
	}
