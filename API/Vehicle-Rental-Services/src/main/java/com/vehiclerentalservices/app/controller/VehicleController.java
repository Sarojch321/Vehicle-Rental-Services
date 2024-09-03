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
import com.vehiclerentalservices.app.payloads.VehicleDto;
import com.vehiclerentalservices.app.services.FileService;
import com.vehiclerentalservices.app.services.VehicleService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/vehicleRentalServices")
public class VehicleController {
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@GetMapping("/vehicle")
	public  ResponseEntity<List<VehicleDto>> getAllVehicle(){
		return ResponseEntity.ok(this.vehicleService.getAllVehicle());
	}
	
	@GetMapping("/vehicle/{id}")
	public  ResponseEntity<VehicleDto> getVehicleById(@PathVariable int id){
		return ResponseEntity.ok(this.vehicleService.getVehicleById(id));
		
	}
	
	@PostMapping("/create/vehicle")
	public ResponseEntity<VehicleDto> addVehicle(@Valid @RequestBody VehicleDto vehicleDto){
		
		VehicleDto vehicle = this.vehicleService.createVehicle(vehicleDto);
		return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
		
	}
	
	@PutMapping("vehicle/{id}")
	public ResponseEntity<VehicleDto> updateVehicle(@Valid @RequestBody VehicleDto vehicleDto, @PathVariable int id){
		VehicleDto dtos = this.vehicleService.updateVehicle(vehicleDto, id);
		return ResponseEntity.ok(dtos);
	}
	
	@DeleteMapping("/vehicle/{id}")
	public ResponseEntity<ApiResponse> deleteVehicle(@PathVariable int id){
		this.vehicleService.deleteVehicle(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}/vehicle")
	public  ResponseEntity<List<VehicleDto>> getAllVehicleByUser(@PathVariable int id){
		List<VehicleDto> vehicle = this.vehicleService.getVehicleByUser(id);
		return new ResponseEntity<List<VehicleDto>>(vehicle, HttpStatus.OK);
		
	}
	
	@GetMapping("/handingloc/{id}/vehicle")
	public  ResponseEntity<List<VehicleDto>> getAllVehiclebyLocation(@PathVariable int id){
		List<VehicleDto> list = this.vehicleService.getVehicleByLocation(id);
		return new ResponseEntity<List<VehicleDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/vehicle/search/{name}")
	public ResponseEntity<List<VehicleDto>> getByName(@PathVariable String name){
		
		List<VehicleDto> list = this.vehicleService.searchByName(name);
		return new ResponseEntity<List<VehicleDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/vehicle/searchs/{type}")
	public ResponseEntity<List<VehicleDto>> getByType(@PathVariable String type){
		List<VehicleDto> list = this.vehicleService.searchByType(type);
		return new ResponseEntity<List<VehicleDto>>(list, HttpStatus.OK);
	}
	
	/*
	 * @GetMapping("/vehicle/address") public ResponseEntity<List<VehicleDto>>
	 * getByAddress(
	 * 
	 * @RequestParam String district,
	 * 
	 * @RequestParam String city){ List<VehicleDto> vehicleDto =
	 * this.vehicleService.searchByAddress(district, city); return new
	 * ResponseEntity<List<VehicleDto>>(vehicleDto, HttpStatus.OK); }
	 */
	
	//image serve api
	
	@GetMapping(value ="/vehicle/bluebookimage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveBluebookImage(
			@PathVariable int id, HttpServletResponse response
			)throws IOException{
		VehicleDto vdto = this.vehicleService.getVehicleById(id);
		String imageName = vdto.getBluebookphoto();
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());
		
	}
	
	@GetMapping(value ="/vehicle/insuranceimage/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveInsuranceImage(
			@PathVariable int id, HttpServletResponse response
			)throws IOException{
		VehicleDto vdto = this.vehicleService.getVehicleById(id);
		String imageName = vdto.getInsurancephoto();
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());
		
	}
	
	//bluebook upload api
	
		@PostMapping("/vehicle/bluebookimage/{vehicleid}")
		public ResponseEntity<VehicleDto> uploadLisence(
				@RequestParam("image") MultipartFile image,@PathVariable int vehicleid)throws IOException{
			
			
			VehicleDto vdto = this.vehicleService.getVehicleById(vehicleid);
			String fileName = this.fileService.uploadImage(path, image);
			vdto.setBluebookphoto(fileName);
			VehicleDto vdtos = this.vehicleService.updateVehicle(vdto, vehicleid);
			return new ResponseEntity<VehicleDto>(vdtos, HttpStatus.OK);
			
		}

		
		//insurance upload api
		
			@PostMapping("/vehicle/insuranceimage/{vehicleid}")
			public ResponseEntity<VehicleDto> uploadInsurance(
					@RequestParam("image") MultipartFile image,@PathVariable int vehicleid)throws IOException{
				
				
				VehicleDto vdto = this.vehicleService.getVehicleById(vehicleid);
				String fileName = this.fileService.uploadImage(path, image);
				vdto.setInsurancephoto(fileName);
				VehicleDto vdtos = this.vehicleService.updateVehicle(vdto, vehicleid);
				return new ResponseEntity<VehicleDto>(vdtos, HttpStatus.OK);
				
			}
}





