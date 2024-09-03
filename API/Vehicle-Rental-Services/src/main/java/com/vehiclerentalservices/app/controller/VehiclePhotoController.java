package com.vehiclerentalservices.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.vehiclerentalservices.app.payloads.VehiclePhotoDto;
import com.vehiclerentalservices.app.services.FileService;
import com.vehiclerentalservices.app.services.VehiclePhotoService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/vehicleRentalServices")
public class VehiclePhotoController {
	
	@Autowired
	private VehiclePhotoService vphotoService;
	
	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;
	
	@GetMapping("/vehiclephoto")
	public ResponseEntity<List<VehiclePhotoDto>> getAllPhoto(){
		return ResponseEntity.ok(this.vphotoService.getAllVehiclePhoto());
		
	}
	
	@GetMapping("/vehiclephoto/{id}")
	public ResponseEntity<VehiclePhotoDto> getPhotoById(@PathVariable int id){
		return ResponseEntity.ok(this.vphotoService.getVehiclePhotoById(id));
	}
	
	@GetMapping("vehicle/{id}/vehiclephoto")
	public ResponseEntity<List<VehiclePhotoDto>> getPhotoByVehicle(@PathVariable int id){
		
		List<VehiclePhotoDto> photos = this.vphotoService.getAllVehiclePhotoByVehicle(id);
		return new ResponseEntity<List<VehiclePhotoDto>>(photos, HttpStatus.OK);
		
		
	}
	
	@PostMapping("/vehiclephoto")
	public ResponseEntity<VehiclePhotoDto> createPhoto(@Valid @RequestBody VehiclePhotoDto photoDto){
		
		VehiclePhotoDto vphotoDto = this.vphotoService.createVehiclePhoto(photoDto);
		return new ResponseEntity<>(vphotoDto, HttpStatus.CREATED);
		
	}

	@PutMapping("/vehiclephoto/{id}")
	public ResponseEntity<VehiclePhotoDto> updatePhoto(@Valid @RequestBody VehiclePhotoDto photoDto, @PathVariable int id){
		
		VehiclePhotoDto photo = this.vphotoService.updateVehiclePhoto(photoDto, id);
		return ResponseEntity.ok(photo);
	}
	
	public ResponseEntity<ApiResponse> deletePhoto(@PathVariable int id){
		
		this.vphotoService.deleteVehiclePhoto(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);	
	}
	
	@PostMapping("/vehiclephoto/image/{photoid}")
	public ResponseEntity<VehiclePhotoDto> uploadImage(
			@RequestParam("image") MultipartFile image,@PathVariable int photoid)throws IOException{
		
		VehiclePhotoDto dtos;
		VehiclePhotoDto vphoto = this.vphotoService.getVehiclePhotoById(photoid);
		String fileName = this.fileService.uploadImage(path, image);
		vphoto.setPhoto(fileName);
		 dtos = this.vphotoService.updateVehiclePhoto(vphoto, photoid);
		return new ResponseEntity<VehiclePhotoDto>(dtos, HttpStatus.OK);
		
	}
	
	@GetMapping(value ="/vehiclephoto/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(
			@PathVariable int id, HttpServletResponse response
			)throws IOException{
		
		VehiclePhotoDto vDto = this.vphotoService.getVehiclePhotoById(id);
		String imageName = vDto.getPhoto();
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());
		
	}
	
	
	
}











