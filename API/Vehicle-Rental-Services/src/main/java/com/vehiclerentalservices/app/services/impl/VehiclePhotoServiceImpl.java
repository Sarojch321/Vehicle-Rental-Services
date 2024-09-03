package com.vehiclerentalservices.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehiclerentalservices.app.entities.Vehicle;
import com.vehiclerentalservices.app.entities.VehiclePhoto;
import com.vehiclerentalservices.app.exceptions.ResourceNotFoundException;
import com.vehiclerentalservices.app.payloads.VehiclePhotoDto;
import com.vehiclerentalservices.app.repositary.VehiclePhotoRepositary;
import com.vehiclerentalservices.app.repositary.VehicleRepositary;
import com.vehiclerentalservices.app.services.VehiclePhotoService;

@Service
public class VehiclePhotoServiceImpl implements VehiclePhotoService {
	
	@Autowired
	private VehiclePhotoRepositary vPhotoRepo;
	
	@Autowired
	private VehicleRepositary vehicleRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<VehiclePhotoDto> getAllVehiclePhoto() {
		List<VehiclePhoto> vphotos = this.vPhotoRepo.findAll();
		List<VehiclePhotoDto> photto = vphotos.stream()
				.map(vphoto-> this.vPhotoToDto(vphoto)).collect(Collectors.toList());
		return photto;
	}

	@Override
	public VehiclePhotoDto getVehiclePhotoById(Integer id) {
		VehiclePhoto vphoto = this.vPhotoRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("VehiclePhoto", "id", id));
		
		return this.vPhotoToDto(vphoto);
	}

	@Override
	public VehiclePhotoDto createVehiclePhoto(VehiclePhotoDto vehiclePhoto){
		VehiclePhoto vphoto = this.dtoToVPhoto(vehiclePhoto);
		vphoto.setPhoto("defaults.jpg");
		VehiclePhoto savePhoto = this.vPhotoRepo.save(vphoto);
		return this.vPhotoToDto(savePhoto);
	}

	@Override
	public VehiclePhotoDto updateVehiclePhoto(VehiclePhotoDto vehiclePhoto, Integer id) {
		VehiclePhoto vphoto = this.vPhotoRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("VehiclePhoto", "id", id));
		
		vphoto.setPhoto(vehiclePhoto.getPhoto());
		vphoto.setVehicle(vehiclePhoto.getVehicle());
		
		VehiclePhoto vphotos = this.vPhotoRepo.save(vphoto);
		VehiclePhotoDto updatephoto = this.vPhotoToDto(vphotos);
		return updatephoto;
	}

	@Override
	public void deleteVehiclePhoto(Integer id) {
		VehiclePhoto vphoto = this.vPhotoRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("VehiclePhoto", "id", id));
		this.vPhotoRepo.delete(vphoto);

	}

	@Override
	public List<VehiclePhotoDto> getAllVehiclePhotoByVehicle(Integer id) {
		Vehicle vehicle = this.vehicleRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", id));
		List<VehiclePhoto> vphoto = this.vPhotoRepo.findAllByVehicle(vehicle);
		List<VehiclePhotoDto> vphotoDto = vphoto.stream()
				.map(vphotos-> this.vPhotoToDto(vphotos)).collect(Collectors.toList());
		return vphotoDto;
	}
	
	private VehiclePhoto dtoToVPhoto(VehiclePhotoDto vPhoto) {
		
		VehiclePhoto vehiclePhoto = this.mapper.map(vPhoto, VehiclePhoto.class);
		return vehiclePhoto;
		
	}
	
	
	public VehiclePhotoDto vPhotoToDto(VehiclePhoto vphoto) {
		
		VehiclePhotoDto vPhotoDto = this.mapper.map(vphoto, VehiclePhotoDto.class);
		return vPhotoDto;
		
	}

}
