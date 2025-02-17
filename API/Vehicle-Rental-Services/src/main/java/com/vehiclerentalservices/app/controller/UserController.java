package com.vehiclerentalservices.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vehiclerentalservices.app.payloads.ApiResponse;
import com.vehiclerentalservices.app.payloads.UserDto;
import com.vehiclerentalservices.app.services.FileService;
import com.vehiclerentalservices.app.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/vehicleRentalServices")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// get all user
	@GetMapping("/user")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.ok(this.userService.getAllUser());

	}

	
	  // get user by id
	  
	  @GetMapping("/user/{id}") 
	  public ResponseEntity<UserDto>getUserById(@PathVariable int id) { 
		  return ResponseEntity.ok(this.userService.getUserById(id)); 
	  }
	 

	// add user
	@PostMapping("/user/signup")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto custdto) {
		String email = custdto.getEmail();
		List<UserDto> uEmail = this.userService.getAllByEmail(email);
		if (!uEmail.isEmpty()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse("Exist", true), HttpStatus.OK);
		}

		this.userService.createUser(custdto);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Done", true), HttpStatus.OK);
	}

	// delete user
	@DeleteMapping("/user/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id) {

		this.userService.deleteUser(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);

	}

	// update customer
	@PutMapping("/user/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userdto, @PathVariable int id) {

		UserDto updatedUser = this.userService.updateUser(userdto, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	@PutMapping("/user/role/{id}")
	public ResponseEntity<UserDto> updateUserByRole(@Valid @RequestBody UserDto userdto, @PathVariable int id) {

		UserDto updatedUser = this.userService.updateByRole(userdto, id);
		return ResponseEntity.ok(updatedUser);
	}
	
	@PutMapping("/user/status/{id}")
	public ResponseEntity<UserDto> updateUserByStatus(@Valid @RequestBody UserDto userdto, @PathVariable int id) {

		UserDto updatedUser = this.userService.updateByStatus(userdto, id);
		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/user/email/{email}")
	public ResponseEntity<List<UserDto>> UserByEmail(@PathVariable String email) {
		List<UserDto> emailUser = this.userService.getAllByEmail(email);
		return new ResponseEntity<List<UserDto>>(emailUser, HttpStatus.OK);
	}

	@GetMapping("/user/search/{keyword}")
	public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
		List<UserDto> searchUser = this.userService.searchPost(keyword);
		return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
	}

	@GetMapping("/user/approve/list/{nbr}")
	public ResponseEntity<List<UserDto>> statusUser(@PathVariable int nbr) {
		List<UserDto> searchUser = this.userService.getByStatus(nbr);
		return new ResponseEntity<List<UserDto>>(searchUser, HttpStatus.OK);
	}

	// image serve api

	@GetMapping(value = "/user/userimage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveUserImage(@PathVariable int id, HttpServletResponse response) throws IOException {
		UserDto uDto = this.userService.getUserById(id);
		String imageName = uDto.getPhoto();
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());

	}

	@GetMapping(value = "/user/lisenceimage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveLisenceImage(@PathVariable int id, HttpServletResponse response) throws IOException {
		UserDto uDto = this.userService.getUserById(id);
		String imageName = uDto.getLicensephoto();
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());

	}

	// user photo upload api
	@PostMapping("/user/userimage/{userid}")
	public ResponseEntity<UserDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable int userid)
			throws IOException {

		UserDto udto = this.userService.getUserById(userid);
		String fileName = this.fileService.uploadImage(path, image);
		udto.setPhoto(fileName);
		UserDto utos = this.userService.updateUser(udto, userid);
		return new ResponseEntity<UserDto>(utos, HttpStatus.OK);

	}

	// lisence upload api
	@PostMapping("/user/lisenceimage/{userid}")
	public ResponseEntity<UserDto> uploadLisence(@RequestParam("image") MultipartFile image, @PathVariable int userid)
			throws IOException {

		UserDto udto = this.userService.getUserById(userid);
		String fileName = this.fileService.uploadImage(path, image);
		udto.setLicensephoto(fileName);
		UserDto utos = this.userService.updateUser(udto, userid);
		return new ResponseEntity<UserDto>(utos, HttpStatus.OK);

	}

}
