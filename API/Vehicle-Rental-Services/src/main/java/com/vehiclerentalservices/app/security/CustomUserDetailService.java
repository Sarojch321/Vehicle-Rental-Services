package com.vehiclerentalservices.app.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.exceptions.ResourceNotFoundException;
import com.vehiclerentalservices.app.repositary.UserRepositary;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepositary userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email " + username, 0));
		return user;
	}

}
