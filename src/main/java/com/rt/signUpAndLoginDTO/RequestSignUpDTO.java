package com.rt.signUpAndLoginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestSignUpDTO {
	private String fullname;
	private String email;
	private String number;
	private String address;
	private String password;
	private String role;

}
