package com.vehiclerentalservices.app.services;



import java.util.List;

import com.vehiclerentalservices.app.payloads.UserDto;



public interface UserService {
	
	
	UserDto createUser(UserDto custdto);
	
	
	UserDto updateUser(UserDto custdto, Integer custId);
	
	
	UserDto getUserById(Integer custId);
	
	
	List<UserDto> getAllUser();
	
	UserDto updateByRole(UserDto custdto, Integer custId);
	
	
	void deleteUser(Integer custId);
	
	List<UserDto> searchPost(String keywords);
	
	List<UserDto> getAllByEmail(String email);
	
	List<UserDto> getByStatus(int nbr);
	
	UserDto updateByStatus(UserDto custdto, Integer flag);
	

}