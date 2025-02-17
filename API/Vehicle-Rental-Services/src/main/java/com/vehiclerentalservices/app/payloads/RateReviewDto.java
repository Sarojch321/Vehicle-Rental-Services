package com.vehiclerentalservices.app.payloads;

import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RateReviewDto {
	
	private int id;	
	private String rate;
	private String review;
	private Vehicle vehicle;
	private User user;

}
