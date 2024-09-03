package com.vehiclerentalservices.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehiclerentalservices.app.payloads.ApiResponse;
import com.vehiclerentalservices.app.payloads.FavoriteDto;
import com.vehiclerentalservices.app.services.FavoriteService;

@RestController
@RequestMapping("api/vehicleRentalServices")
public class FavoriteController {
	
	@Autowired
	private FavoriteService favService;
	
	@GetMapping("/favorite")
	public ResponseEntity<List<FavoriteDto>>getAllfav(){
		return ResponseEntity.ok(this.favService.getAllFavorite());
	}
	
	@GetMapping("/favorite/{id}")
	public ResponseEntity<FavoriteDto> getFavById(@PathVariable int id){
		return ResponseEntity.ok(this.favService.getfavoriteById(id));
	}
	
	@PostMapping("/favorite")
	public ResponseEntity<FavoriteDto> addFav(@RequestBody FavoriteDto favDto){
		
		FavoriteDto fav = this.favService.createfavorite(favDto);
		return new ResponseEntity<>(fav, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/favorite/{id}")
	public ResponseEntity<FavoriteDto> updateFav(@RequestBody FavoriteDto favDto, @PathVariable int id){
		
		FavoriteDto fav = this.favService.updatefavorite(favDto, id);
		return ResponseEntity.ok(fav);
	}
	
	@DeleteMapping("/favorite/{id}")
	public ResponseEntity<ApiResponse> deleteFav(@PathVariable int id){
		
		this.favService.deleteFavorite(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);	
		
	}
	
	@GetMapping("/user/{id}/favorite")
	public ResponseEntity<List<FavoriteDto>> getAllByUser(@PathVariable int id){
		
		List<FavoriteDto> list = this.favService.getAllFavoriteByUser(id);
		return new ResponseEntity<List<FavoriteDto>>(list, HttpStatus.OK);
		
	}
	
	@GetMapping("/vehicle/{id}/favorite")
	public ResponseEntity<List<FavoriteDto>> getAllByVehicle(@PathVariable int id){
	
		List<FavoriteDto> list = this.favService.getAllFavoriteByVehicle(id);
		return new ResponseEntity<List<FavoriteDto>>(list, HttpStatus.OK);
	}

}










