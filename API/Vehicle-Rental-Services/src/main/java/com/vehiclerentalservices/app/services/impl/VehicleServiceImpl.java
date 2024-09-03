package com.vehiclerentalservices.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehiclerentalservices.app.entities.HandOverLocation;
import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;
import com.vehiclerentalservices.app.exceptions.ResourceNotFoundException;
import com.vehiclerentalservices.app.payloads.VehicleDto;
import com.vehiclerentalservices.app.repositary.HandOverLocationRepositary;
import com.vehiclerentalservices.app.repositary.UserRepositary;
import com.vehiclerentalservices.app.repositary.VehicleRepositary;
import com.vehiclerentalservices.app.services.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {
	
	@Autowired
	private VehicleRepositary vehicleRepo;
	
	@Autowired
	private UserRepositary userRepo;
	
	@Autowired
	private HandOverLocationRepositary locRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<VehicleDto> getAllVehicle() {
		List<Vehicle> vehicle = this.vehicleRepo.findAll();
		List<VehicleDto> vehicles = vehicle.stream()
				.map(vehiclelist-> this.vehicleToDto(vehiclelist)).collect(Collectors.toList());
		
		return vehicles;
	}

	@Override
	public VehicleDto getVehicleById(Integer id) {
		Vehicle vehicle = this.vehicleRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", id));
		
		return this.vehicleToDto(vehicle);
	}

	@Override
	public VehicleDto createVehicle(VehicleDto vehicleDto) {
		Vehicle vehicle = this.dtoToVehicle(vehicleDto);
		Vehicle saveVehicle = this.vehicleRepo.save(vehicle);
		return this.vehicleToDto(saveVehicle);
	}

	@Override
	public VehicleDto updateVehicle(VehicleDto vehicleDto, Integer id) {
		Vehicle vehicle = this.vehicleRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", id));
		
		vehicle.setName(vehicleDto.getName());
		vehicle.setType(vehicleDto.getType());
		vehicle.setAmount(vehicleDto.getAmount());
		vehicle.setStatus(vehicleDto.getStatus());
		vehicle.setBluebookphoto(vehicleDto.getBluebookphoto());
		vehicle.setInsurancephoto(vehicleDto.getInsurancephoto());
		vehicle.setDriverstatus(vehicleDto.getDriverstatus());
		vehicle.setLocation(vehicleDto.getLocation());
		
		Vehicle vehicles = this.vehicleRepo.save(vehicle);
		VehicleDto vehicles1 = this.vehicleToDto(vehicles);
		return vehicles1;
	}

	@Override
	public void deleteVehicle(Integer id) {
		Vehicle vehicle = this.vehicleRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", id));
		this.vehicleRepo.delete(vehicle);

	}

	@Override
	public List<VehicleDto> getVehicleByUser(Integer id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		List<Vehicle> vehicle = this.vehicleRepo.findAllByUser(user);
		
		List<VehicleDto> dtoList = vehicle.stream()
				.map(vehicles-> this.vehicleToDto(vehicles)).collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public List<VehicleDto> getVehicleByLocation(Integer id) {
		HandOverLocation loc = this.locRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("HandOverLocation", "id", id));
		List<Vehicle> vehicle = this.vehicleRepo.findAllByLocation(loc);
		
		List<VehicleDto> dtoList = vehicle.stream()
				.map(vehicles-> this.vehicleToDto(vehicles)).collect(Collectors.toList());
		return dtoList;
	}
	
	@Override
	public List<VehicleDto> searchByName(String keyword){
		
		List<Vehicle> vehicle = this.vehicleRepo.searchByName("%"+keyword+"%");
		List<VehicleDto> vehicleDto = vehicle.stream()
				.map(vehicles-> this.vehicleToDto(vehicles)).collect(Collectors.toList());
		return vehicleDto;
	}
	
	@Override
	public List<VehicleDto> searchByType(String keyword){
		
		List<Vehicle> vehicle = this.vehicleRepo.searchByType(keyword);
		List<VehicleDto> vehicleDto =vehicle.stream()
				.map(vehicles-> this.vehicleToDto(vehicles)).collect(Collectors.toList());
		return vehicleDto;
	}
	 
	
	private Vehicle dtoToVehicle(VehicleDto vehicleDto) {
		
		Vehicle vehicle = this.mapper.map(vehicleDto, Vehicle.class);
		return vehicle;
		
	}
	
	
	public VehicleDto vehicleToDto(Vehicle vehicle) {
		
		VehicleDto vehicleDto = this.mapper.map(vehicle, VehicleDto.class);
		return vehicleDto;
		
	}

	/*
	 * @Override public List<VehicleDto> searchByAddress(String district, String
	 * city) { List<Vehicle> list =
	 * this.vehicleRepo.findByHandOverLocationDistrictAndHandOverLocationCity(
	 * district, city); List<VehicleDto> vehicle = list.stream() .map(vehicles ->
	 * this.vehicleToDto(vehicles)).collect(Collectors.toList()); return vehicle; }
	 */

}
