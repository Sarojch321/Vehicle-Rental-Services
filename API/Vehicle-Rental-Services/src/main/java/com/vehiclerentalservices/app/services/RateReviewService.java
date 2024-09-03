package com.vehiclerentalservices.app.services;

import java.util.List;


import com.vehiclerentalservices.app.payloads.RateReviewDto;


public interface RateReviewService {
	
	List<RateReviewDto> getAllReview();
	
	RateReviewDto getRateById(Integer id);
	
	RateReviewDto createRate(RateReviewDto rateRevieDto, Integer uId);
	
	RateReviewDto updateRate(RateReviewDto rateReviewDto, Integer id);
	
	void deleteRate(Integer id);
	
	List<RateReviewDto> getAllVehicle(Integer id);
		

}
