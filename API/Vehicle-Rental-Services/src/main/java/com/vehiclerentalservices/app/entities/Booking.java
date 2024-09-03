package com.vehiclerentalservices.app.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 15, nullable = false)
	private String dateFrom;
	
	@Column(length = 15, nullable = false)
	private String dateTo;
	
	@Column(nullable = false)
	private String totalAmount;

	@Column(length = 10, nullable = false)
	private String driverNeed;
	
	// 0 if not booking approve by owner
	private int flag;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	@ManyToOne
	@JsonIgnore
	private Vehicle vehicle;
	
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "pickup_location_id")
    private HandOverLocation pickupLocation;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "dropoff_location_id")
    private HandOverLocation dropoffLocation;
	
	
}









