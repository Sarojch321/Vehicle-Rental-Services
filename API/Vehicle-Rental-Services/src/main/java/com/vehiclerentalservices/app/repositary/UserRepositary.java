package com.vehiclerentalservices.app.repositary;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vehiclerentalservices.app.entities.User;

@Repository
public interface UserRepositary extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.name like :key")
	List<User> searchByName(@Param("key") String name); 
	
	@Query("select u from User u where u.email like :key")
	Optional<User> findByEmail(@Param("key") String email);
	
	List<User>findByStatus(int status);
	
}
