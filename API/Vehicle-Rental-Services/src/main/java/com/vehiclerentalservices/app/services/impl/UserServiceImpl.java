package com.vehiclerentalservices.app.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vehiclerentalservices.app.entities.User;
import com.vehiclerentalservices.app.exceptions.ResourceNotFoundException;
import com.vehiclerentalservices.app.payloads.UserDto;
import com.vehiclerentalservices.app.repositary.UserRepositary;
import com.vehiclerentalservices.app.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepositary userRepo;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDto createUser(UserDto custdto) {
		User user = this.dtoToUser(custdto);
		user.setPassword(passwordEncoder.encode(custdto.getPassword()));
		user.setDob("2080/10/10");
		user.setPhoto("photo.jpg");
		user.setLicensephoto("lisence.jpg");
		user.setStatus(0);
		user.setActivestatus(0);
		User saveduser = this.userRepo.save(user);
		return this.userToDto(saveduser);
	}

	@Override
	public UserDto updateUser(UserDto custdto, Integer custId) {
		User cust = this.userRepo.findById(custId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", custId));
		
		
		cust.setName(custdto.getName());
		cust.setAddress(custdto.getAddress());
		cust.setMobile(custdto.getMobile());
		cust.setEmail(custdto.getEmail());
		cust.setLicensephoto(custdto.getLicensephoto());
		cust.setPhoto(custdto.getPhoto());
		cust.setDob(custdto.getDob());
		cust.setStatus(custdto.getStatus());
		
		User updateCust = this.userRepo.save(cust);
		UserDto custdto1 = this.userToDto(updateCust);
		
		return custdto1;
	}

	@Override
	public UserDto getUserById(Integer custId) {
		User cust = this.userRepo.findById(custId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", custId));
		
		return this.userToDto(cust);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> custList = users.stream()
				.map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return custList;
	}

	@Override
	public void deleteUser(Integer custId) {
		User cust = this.userRepo.findById(custId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", custId));
		this.userRepo.delete(cust);
	}
	
	
	@Override
	public List<UserDto> getAllByEmail(String email){
		Optional<User> user = this.userRepo.findByEmail(email);
		List<UserDto>list = user.stream()
				.map(users -> this.userToDto(users)).collect(Collectors.toList());
		
		return list;
	}

	@Override
	public List<UserDto> searchPost(String keywords) {
		List<User> user= this.userRepo.searchByName("%"+keywords+"%");
		List<UserDto> list =user.stream()
				.map(users-> this.userToDto(users)).collect(Collectors.toList());
		return list;
	}
	
	
	  @Override public List<UserDto> getByStatus(int nbr){ 
		  List<User> user =this.userRepo.findByStatus(nbr); 
	  List<UserDto> list = user.stream() .map(users
	  -> this.userToDto(users)).collect(Collectors.toList()); return list; }
	 
	
	private User dtoToUser(UserDto custDto) {
		
		User cust = this.mapper.map(custDto, User.class);
		return cust;
		
	}
	
	
	public UserDto userToDto(User cust) {
		
		UserDto custdto = this.mapper.map(cust, UserDto.class);
		return custdto;
		
	}

	@Override
	public UserDto updateByRole(UserDto custdto, Integer custId) {
		User cust = this.userRepo.findById(custId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", custId));
		
		cust.setRole(custdto.getRole());
		
		User updateCust = this.userRepo.save(cust);
		UserDto custdto1 = this.userToDto(updateCust);
		
		return custdto1;
	}

	@Override
	public UserDto updateByStatus(UserDto custdto, Integer flag) {
		User cust = this.userRepo.findById(flag)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", flag));
		
		cust.setStatus(custdto.getStatus());
		
		User updateCust = this.userRepo.save(cust);
		UserDto custdto1 = this.userToDto(updateCust);
		
		return custdto1;
	}


}
