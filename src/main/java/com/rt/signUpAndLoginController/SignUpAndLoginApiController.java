package com.rt.signUpAndLoginController;

import org.springframework.web.bind.annotation.RestController;

import com.rt.signUpAndLoginDTO.RequestLoginDTO;
import com.rt.signUpAndLoginDTO.RequestSignUpDTO;
import com.rt.signUpAndLoginDTO.ResponseLoginDTO;
import com.rt.signUpAndLoginserviceImpl.LoginServiceImplementation;
import com.rt.signUpAndLoginserviceImpl.SignUpImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/users")
public class SignUpAndLoginApiController {
	//sign-up logic start.
	@Autowired
	private SignUpImplementation signUpService;
	
	@PostMapping("/register")
	public String data(@RequestBody RequestSignUpDTO signUpDto) {
		
		String userStatus=signUpService.addUser(signUpDto);
		
		System.out.println(signUpDto.getFullname()+" "+signUpDto.getEmail()+" "+signUpDto.getNumber()+" "+signUpDto.getAddress()+" "+signUpDto.getPassword()+" "+signUpDto.getRole());
		return userStatus;
		
	}
	//sign-up logic end.
	
	//login logic start.
	@Autowired
	private LoginServiceImplementation loginService;
	
	@PostMapping("/login")
	public ResponseLoginDTO checkUserForLogin(@RequestBody RequestLoginDTO loginDto) {
		ResponseLoginDTO userInfo=loginService.checkLogin(loginDto);
		System.out.println(loginDto.getEmail()+" "+loginDto.getPassword());
		
		if(userInfo!=null) {
		return userInfo;
		} 
		return null;
		
	}
	//login logic end.
	

}
