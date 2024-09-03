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
import com.vehiclerentalservices.app.payloads.HandOverLocationDto;
import com.vehiclerentalservices.app.services.HandOverLocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/vehicleRentalServices")
public class HandOverLocationController {
	
	@Autowired
	private HandOverLocationService locservice;
	
	@GetMapping("/handingloc")
	public ResponseEntity<List<HandOverLocationDto>> getAllLoc(){
		return ResponseEntity.ok(this.locservice.getAllLocation());
	}
	
	@GetMapping("/handingloc/{id}")
	public ResponseEntity<HandOverLocationDto> getById(@PathVariable int id){
		
		return ResponseEntity.ok(this.locservice.getLocationById(id));
		
	}
	
	@PostMapping("/handingloc")
	public ResponseEntity<HandOverLocationDto> addLoccation(@Valid @RequestBody HandOverLocationDto locdto){
		
		HandOverLocationDto location = this.locservice.createLocation(locdto);
		return new ResponseEntity<>(location, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/handingloc/{id}")
	public ResponseEntity<ApiResponse> deleteLoc(@PathVariable int id){
		
		this.locservice.deleteLocation(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);
	}
	
	@PutMapping("/handingloc/{id}")
	public ResponseEntity<HandOverLocationDto> updateLoc(@Valid @RequestBody HandOverLocationDto locdto, @PathVariable int id){
		
		HandOverLocationDto updateloc = this.locservice.updateLocation(locdto, id);
		return ResponseEntity.ok(updateloc);
	}

}




