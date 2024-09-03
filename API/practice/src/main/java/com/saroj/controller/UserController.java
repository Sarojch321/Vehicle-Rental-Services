package com.saroj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saroj.entity.User;
import com.saroj.repositary.UserRepo;

@RestController
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/user")
	public List<User> getAllUser() {
		List<User> user = userRepo.findAll();
		return user;
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable int id) {
		User user = userRepo.findById(id).get();
		return user;
	}
	
	@PostMapping("/user")
	public User addUser(@RequestBody User user) {
		User addUser = userRepo.save(user);
		return addUser;
	}
	
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepo.deleteById(id);
	}
	
	@PutMapping("/user/{id}")
	public User updateUser(@RequestBody User user, @PathVariable int id) {
		User extingUser = userRepo.findById(id).get();
		extingUser.setEmail(user.getEmail());
		extingUser.setPassword(user.getPassword());
		extingUser.setPhone(user.getPhone());
		extingUser.setAddress(user.getAddress());
		User updatedUser = userRepo.save(extingUser);
		return updatedUser;
	}

}









