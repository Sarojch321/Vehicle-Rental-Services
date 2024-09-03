package com.vehiclerentalservices.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vehiclerentalservices.app.entities.RateReview;
import com.vehiclerentalservices.app.entities.Vehicle;

@Repository
public interface RateReviewRepositary extends JpaRepository<RateReview,Integer> {
	
	List<RateReview> findAllByVehicle(Vehicle vehicle);

}
