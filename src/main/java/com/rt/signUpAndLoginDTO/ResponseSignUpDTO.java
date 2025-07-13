package com.rt.signUpAndLoginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSignUpDTO {
	private int id;
	
	private String fullname;
	
	private String email;
		  
	private String number;
		  
	private String address;
		  	
	private String role;

	private String status;

	public ResponseSignUpDTO(String fullname, String email, String number, String address,String role) {
		super();
		this.fullname = fullname;
		this.email = email;
		this.number = number;
		this.address = address;
		this.role = role;
	}

	public ResponseSignUpDTO(int id,String fullname, String email,String address,String role,String status) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.address = address;
		this.role = role;
		this.status=status;
	}


}
