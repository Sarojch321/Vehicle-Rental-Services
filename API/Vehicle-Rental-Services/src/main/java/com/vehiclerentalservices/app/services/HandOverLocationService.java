package com.vehiclerentalservices.app.services;


import java.util.List;

import com.vehiclerentalservices.app.payloads.HandOverLocationDto;

public interface HandOverLocationService {
	
	List<HandOverLocationDto> getAllLocation();
	
	HandOverLocationDto createLocation(HandOverLocationDto locationdto);
	
	HandOverLocationDto updateLocation(HandOverLocationDto locationdto, Integer id);
	
	HandOverLocationDto getLocationById(Integer id);
	
	void deleteLocation(Integer id);
	
	

}
