package com.vehiclerentalservices.app.payloads;


import com.vehiclerentalservices.app.entities.HandOverLocation;
import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
	
	private int id;
	
	private String dateFrom;
	
	private String dateTo;
	
	private String totalAmount;

	private String driverNeed;
	
	private int flag;

	private User user;
	
	private Vehicle vehicle;
	
    private HandOverLocation pickupLocation;

    private HandOverLocation dropoffLocation;

}
