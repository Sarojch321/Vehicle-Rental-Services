package com.vehiclerentalservices.app.payloads;

import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.HandOverLocation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDto {
	
	private int id;
	
	private String name;
	
	private String type;
	
	private String amount;
	private int status;
	
	private String bluebookphoto;
	
	private String insurancephoto;
	
	private String driverstatus;
	
	private User user;
	private HandOverLocation location;

}
