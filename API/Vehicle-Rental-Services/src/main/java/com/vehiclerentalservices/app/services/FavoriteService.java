package com.vehiclerentalservices.app.services;

import java.util.List;

import com.vehiclerentalservices.app.payloads.FavoriteDto;

public interface FavoriteService {
	
	List<FavoriteDto> getAllFavorite();
	
	FavoriteDto getfavoriteById(Integer id);
	
	FavoriteDto createfavorite(FavoriteDto favDto);

	FavoriteDto updatefavorite(FavoriteDto favDto, Integer id);
	
	void deleteFavorite(Integer id);
	
	List<FavoriteDto> getAllFavoriteByUser(Integer id);
	
	List<FavoriteDto> getAllFavoriteByVehicle(Integer id);


}
