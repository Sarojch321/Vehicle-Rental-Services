package com.vehiclerentalservices.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehiclerentalservices.app.security.JwtResponse;
import com.vehiclerentalservices.app.exceptions.PasswordNotFoundException;
import com.vehiclerentalservices.app.payloads.JwtRequest;
import com.vehiclerentalservices.app.security.JwtTokenHelper;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/vehicleRentalServices")
public class JwtController {
	
		@Autowired
	  private UserDetailsService userDetailsService;
		@Autowired
	    private AuthenticationManager manager;
		@Autowired
	    private JwtTokenHelper helper;

	    @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request,Authentication authenticattion ){
System.out.println(request);
	        this.doAuthenticate(request.getUsername(), request.getPassword());


	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
	        String token = this.helper.generateToken(userDetails);

	        com.vehiclerentalservices.app.security.JwtResponse response = JwtResponse.builder()
	                .jwtToken(token)
	                .build();
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }

	    private void doAuthenticate(String email, String password){
	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            manager.authenticate(authentication);
	        } catch (BadCredentialsException e) {
	           throw new PasswordNotFoundException();
	        }
	    }

}
