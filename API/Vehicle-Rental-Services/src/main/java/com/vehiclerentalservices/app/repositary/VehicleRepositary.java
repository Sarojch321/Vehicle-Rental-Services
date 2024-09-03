package com.vehiclerentalservices.app.repositary;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vehiclerentalservices.app.entities.HandOverLocation;
import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;

@Repository
public interface VehicleRepositary extends JpaRepository<Vehicle, Integer> {
	
	List<Vehicle> findAllByUser(User user);
	
	List<Vehicle> findAllByLocation(HandOverLocation loc);
	
	@Query("select v from Vehicle v where v.name like :key")
	List<Vehicle> searchByName(@Param("key") String name);
	
	@Query("select v from Vehicle v where v.type like :key")
	List<Vehicle> searchByType(@Param("key") String type);
	
	//@Query("select v from Vehicle v where v.handoverloaction.district = :district and v.handoverlocation.city = :city and v.handoverlocation.ward = :ward")
	/*
	 * List<Vehicle> FindByAddress(
	 * 
	 * @Param("district") String district,
	 * 
	 * @Param("city") String city,
	 * 
	 * @Param("ward") String ward);
	 */
	
	//List<Vehicle> findByHandOverLocationDistrictAndHandOverLocationCity(String district, String city);

}
