package com.vehiclerentalservices.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehiclerentalservices.app.entities.HandOverLocation;
import com.vehiclerentalservices.app.exceptions.ResourceNotFoundException;
import com.vehiclerentalservices.app.payloads.HandOverLocationDto;
import com.vehiclerentalservices.app.repositary.HandOverLocationRepositary;
import com.vehiclerentalservices.app.services.HandOverLocationService;

@Service
public class HandOverLocationServiceImpl implements HandOverLocationService {
	
	@Autowired
	private HandOverLocationRepositary locRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<HandOverLocationDto> getAllLocation(){
		List<HandOverLocation> locs = this.locRepo.findAll();
		List<HandOverLocationDto> location = locs.stream().map(loccs-> this.locationToDto(loccs)).collect(Collectors.toList());
		return location;
	}

	@Override
	public HandOverLocationDto createLocation(HandOverLocationDto locationdto) {
		
		HandOverLocation loc = this.dtoToLocation(locationdto);
		HandOverLocation savedloc = this.locRepo.save(loc);
		return this.locationToDto(savedloc);
	}

	@Override
	public HandOverLocationDto updateLocation(HandOverLocationDto locationdto, Integer id) {
		HandOverLocation loc = this.locRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("HandOverLocation", "id", id));
		
		loc.setDistrict(locationdto.getDistrict());
		loc.setCity(locationdto.getCity());
		loc.setWard(locationdto.getWard());
		loc.setTole(locationdto.getTole());
		
		HandOverLocation updateloc = this.locRepo.save(loc);
		HandOverLocationDto locdto = this.locationToDto(updateloc);
		return locdto;
	}

	@Override
	public HandOverLocationDto getLocationById(Integer id) {
		HandOverLocation loc = this.locRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("HandOverLocation", "id", id));
		
		return this.locationToDto(loc);
	}

	@Override
	public void deleteLocation(Integer id) {
		HandOverLocation loc = this.locRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("HandOverLocation", "id", id));
		this.locRepo.delete(loc);

	}
	
	private HandOverLocation dtoToLocation(HandOverLocationDto locationdto) {
		
		HandOverLocation loc = this.mapper.map(locationdto, HandOverLocation.class);
		return loc;
		
	}
	
	
	public HandOverLocationDto locationToDto(HandOverLocation location) {
		
		HandOverLocationDto locdto = this.mapper.map(location, HandOverLocationDto.class);
		return locdto;
	}

}
