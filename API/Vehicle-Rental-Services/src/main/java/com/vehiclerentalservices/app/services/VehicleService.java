package com.vehiclerentalservices.app.services;

import java.util.List;

import com.vehiclerentalservices.app.payloads.VehicleDto;

public interface VehicleService {
	
	List<VehicleDto> getAllVehicle();
	
	VehicleDto getVehicleById(Integer id);
	
	VehicleDto createVehicle(VehicleDto vehicleDto);
	
	VehicleDto updateVehicle(VehicleDto vehicleDto, Integer id);
	
	void deleteVehicle(Integer id);
	
	List<VehicleDto> getVehicleByUser(Integer id);
	
	List<VehicleDto> getVehicleByLocation(Integer id);
	
	List<VehicleDto> searchByName(String keyword);
	
	List<VehicleDto> searchByType(String ketword);
	

	//List<VehicleDto> searchByAddress(String district, String city);
	

}
