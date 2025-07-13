package com.rt.signUpAndLoginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginDTO {
	private int id;
	
	private String fullname;
	
	private String email;
	
	private String role;
	
	private String Status;

}
