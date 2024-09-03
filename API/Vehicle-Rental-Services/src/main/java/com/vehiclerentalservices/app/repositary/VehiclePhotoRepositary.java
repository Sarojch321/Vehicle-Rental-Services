package com.vehiclerentalservices.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vehiclerentalservices.app.entities.Vehicle;
import com.vehiclerentalservices.app.entities.VehiclePhoto;

@Repository
public interface VehiclePhotoRepositary extends JpaRepository<VehiclePhoto,Integer>{
	
	List<VehiclePhoto> findAllByVehicle(Vehicle vehicle);
}
