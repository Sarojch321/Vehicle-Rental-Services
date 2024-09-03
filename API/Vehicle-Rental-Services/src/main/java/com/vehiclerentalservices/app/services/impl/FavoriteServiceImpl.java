package com.vehiclerentalservices.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehiclerentalservices.app.entities.Favorite;
import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;
import com.vehiclerentalservices.app.exceptions.ResourceNotFoundException;
import com.vehiclerentalservices.app.payloads.FavoriteDto;
import com.vehiclerentalservices.app.repositary.FavoriteRepositary;
import com.vehiclerentalservices.app.repositary.UserRepositary;
import com.vehiclerentalservices.app.repositary.VehicleRepositary;
import com.vehiclerentalservices.app.services.FavoriteService;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	@Autowired
	private FavoriteRepositary favRepo;
	
	@Autowired
	private UserRepositary userRepo;
	
	@Autowired
	private VehicleRepositary vehicleRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<FavoriteDto> getAllFavorite() {
		List<Favorite> fav = this.favRepo.findAll();
		List<FavoriteDto> favs = fav.stream()
				.map(favList-> this.favoriteToDto(favList)).collect(Collectors.toList());
		return favs;
	}

	@Override
	public FavoriteDto getfavoriteById(Integer id) {
		Favorite fav = this.favRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Favorite", "id", id));
				
		return this.favoriteToDto(fav);
	}

	@Override
	public FavoriteDto createfavorite(FavoriteDto favDto) {
		Favorite fav = this.dtoToFavorite(favDto);
		Favorite favs = this.favRepo.save(fav);
		return this.favoriteToDto(favs);
	}

	@Override
	public FavoriteDto updatefavorite(FavoriteDto favDto, Integer id) {
		Favorite fav = this.favRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Favorite", "id", id));
		
		fav.setUser(favDto.getUser());
		fav.setVehicle(favDto.getVehicle());
		
		Favorite favs = this.favRepo.save(fav);
		FavoriteDto updatefav = this.favoriteToDto(favs);
		return updatefav;
	}

	@Override
	public void deleteFavorite(Integer id) {
		Favorite fav = this.favRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Favorite", "id", id));
		this.favRepo.delete(fav);

	}

	@Override
	public List<FavoriteDto> getAllFavoriteByUser(Integer id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		List<Favorite> fav = this.favRepo.findAllByUser(user);
		List<FavoriteDto> list = fav.stream()
				.map(favs-> this.favoriteToDto(favs)).collect(Collectors.toList());
		return list;
	}

	@Override
	public List<FavoriteDto> getAllFavoriteByVehicle(Integer id) {
		Vehicle vehicle = this.vehicleRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Vehicle", "id", id));
		List<Favorite> fav = this.favRepo.findAllByVehicle(vehicle);
		List<FavoriteDto> list = fav.stream()
				.map(favs-> this.favoriteToDto(favs)).collect(Collectors.toList());
		return list;
	}
	
	private Favorite dtoToFavorite(FavoriteDto favDto) {
		
		Favorite fav = this.mapper.map(favDto, Favorite.class);
		return fav;
		
	}
	
	
	public FavoriteDto favoriteToDto(Favorite fav) {
		
		FavoriteDto favDto = this.mapper.map(fav, FavoriteDto.class);
		return favDto;
		
	}

}
