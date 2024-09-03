package com.vehiclerentalservices.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vehiclerentalservices.app.entities.Favorite;
import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;

@Repository
public interface FavoriteRepositary extends JpaRepository<Favorite,Integer> {

	List<Favorite> findAllByUser(User user);
	
	List<Favorite> findAllByVehicle(Vehicle vehicle);
}
