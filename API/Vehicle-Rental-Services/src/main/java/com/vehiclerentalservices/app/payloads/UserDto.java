package com.vehiclerentalservices.app.payloads;



import com.vehiclerentalservices.app.entities.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
	
	private int id;
	
	@Size(min = 4, message = "Name Must be minimum 4 character !!")
	private String name;
	
	
	private String address;
	
	
	private String mobile;
	
	@Email(message = "email is not valid !!")
	private String email;
	

	@Size(min = 4, max = 10, message = "Password must be in between 4 to 10 chracters !!")
	private String password;
	
	
	private String licensephoto;
	
	
	private String photo;
	
	
	private String dob;
	private int status;
	private int activestatus;
	
	private RoleEnum role;
	
}
