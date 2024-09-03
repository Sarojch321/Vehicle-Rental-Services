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
import com.vehiclerentalservices.app.payloads.RateReviewDto;
import com.vehiclerentalservices.app.services.RateReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/vehicleRentalServices")
public class RateReviewController {
	
	@Autowired
	private RateReviewService rateService;
	
	@GetMapping("/rate")
	public ResponseEntity<List<RateReviewDto>> getAllRate(){
		return ResponseEntity.ok(this.rateService.getAllReview());
		
	}
	
	@GetMapping("/rate/{id}")
	public ResponseEntity<RateReviewDto> getById(@PathVariable int id){
		return ResponseEntity.ok(this.rateService.getRateById(id));
	}
	
	@PostMapping("/user/{id}/rate")
	public ResponseEntity<RateReviewDto> addRate(@Valid @RequestBody RateReviewDto rateDto, @PathVariable int id){
		
		RateReviewDto rate = this.rateService.createRate(rateDto, id);
		return new ResponseEntity<>(rate, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/rate/{id}")
	public ResponseEntity<ApiResponse> deleteRate(@PathVariable int id){
		
		this.rateService.deleteRate(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);	

	}
	
	@PutMapping("/rate/{id}")
	public ResponseEntity<RateReviewDto> updateRate(@Valid @RequestBody RateReviewDto rateDto, @PathVariable int id){
		
		RateReviewDto reviewDto = this.rateService.updateRate(rateDto, id);
		return ResponseEntity.ok(reviewDto);
	}
	
	@GetMapping("/vehicle/{id}/rate")
	public ResponseEntity<List<RateReviewDto>> getByVehicle(@PathVariable int id){
		
		List<RateReviewDto> list = this.rateService.getAllVehicle(id);
		return new ResponseEntity<List<RateReviewDto>>(list, HttpStatus.OK);
	}

}





