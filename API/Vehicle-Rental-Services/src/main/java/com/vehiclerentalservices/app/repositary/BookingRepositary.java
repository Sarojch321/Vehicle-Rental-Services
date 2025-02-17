package com.vehiclerentalservices.app.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vehiclerentalservices.app.entities.Booking;
import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.entities.Vehicle;

@Repository
public interface BookingRepositary extends JpaRepository<Booking, Integer>{
	
	List<Booking> findAllByUser(User user);
	
	
	List<Booking> findAllByVehicle(Vehicle vehicle);
	
	List<Booking> findByFlag(int flag);
	
	//@Query("select b from Booking b join v.vehicle v where v.id = :vId")
	//List<Booking> findByBookingAll(@Param("vId") int vId, int flag);
	
	
}
