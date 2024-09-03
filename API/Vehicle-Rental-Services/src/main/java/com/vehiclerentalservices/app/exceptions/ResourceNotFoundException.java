package com.vehiclerentalservices.app.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	

	String ResourceName;
	String FieldName;
	long fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s is not found with %s : %s",resourceName, fieldName, fieldValue));
		ResourceName = resourceName;
		FieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}
