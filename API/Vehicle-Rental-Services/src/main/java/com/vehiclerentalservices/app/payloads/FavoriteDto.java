package com.vehiclerentalservices.app.payloads;

import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FavoriteDto {
	
	private int id;
	
	private User user;
	
	private Vehicle vehicle;

}
