package com.vehiclerentalservices.app.services;


import java.util.List;

import com.vehiclerentalservices.app.payloads.VehiclePhotoDto;

public interface VehiclePhotoService {
	
	List<VehiclePhotoDto> getAllVehiclePhoto();
	
	VehiclePhotoDto getVehiclePhotoById(Integer id);
	
	VehiclePhotoDto createVehiclePhoto(VehiclePhotoDto vehiclePhoto);
	
	VehiclePhotoDto updateVehiclePhoto(VehiclePhotoDto vehiclePhoto, Integer id);
	
	void deleteVehiclePhoto(Integer id);
	
	List<VehiclePhotoDto> getAllVehiclePhotoByVehicle(Integer id); 

}
