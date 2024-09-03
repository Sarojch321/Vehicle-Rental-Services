package com.vehiclerentalservices.app.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HandOverLocationDto {
	
	private int id;
	private String district;
	private String city;
	private String ward;
	private String tole;

}
