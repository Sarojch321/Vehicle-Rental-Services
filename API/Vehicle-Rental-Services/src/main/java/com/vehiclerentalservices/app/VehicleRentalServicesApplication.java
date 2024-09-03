package com.vehiclerentalservices.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class VehicleRentalServicesApplication{
	
	

	public static void main(String[] args) {
		SpringApplication.run(VehicleRentalServicesApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	

	

}
