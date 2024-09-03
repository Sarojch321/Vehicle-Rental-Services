package com.vehiclerentalservices.app.payloads;

import com.vehiclerentalservices.app.entities.Vehicle;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehiclePhotoDto {
	
	private int id;
	
	@NotEmpty(message ="Please insert your vehicle's photo !!")
	private String photo;
	
	private Vehicle vehicle;

}
