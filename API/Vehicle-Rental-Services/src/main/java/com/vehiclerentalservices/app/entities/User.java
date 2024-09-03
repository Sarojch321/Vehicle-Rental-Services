package com.vehiclerentalservices.app.entities;



import java.util.Collection;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 30, nullable = false)
	private String name;
	
	@Column(length = 50, nullable = false)
	private String address;
	
	@Column(length = 15, nullable = false)
	private String mobile;
	
	@Column(length = 50, nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(length = 200, nullable = false, name = "license_photo")
	private String licensephoto;
	
	@Column(length = 200, nullable = false)
	private String photo;
	
	@Column(length = 15, nullable = false)
	private String dob;
	
	@Column(nullable = false)
	private int status;
	private int activestatus;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Booking> booking;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Vehicle> vehicle;
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Favorite> favorite;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RateReview> rtereview;
	
	@Enumerated(EnumType.STRING)
	private RoleEnum role;

	
	@Override
	public String getUsername() {
		 return this.email;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	
}







