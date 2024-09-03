package com.vehiclerentalservices.app.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vehiclerentalservices.app.entities.HandOverLocation;

@Repository
public interface HandOverLocationRepositary extends JpaRepository<HandOverLocation, Integer>{

}
