package com.rt.signUpAndLoginController;

import org.springframework.web.bind.annotation.RestController;

import com.rt.signUpAndLoginDTO.ParkingDashBoardInfoDTO;
import com.rt.signUpAndLoginDTO.RequestLoginDTO;
import com.rt.signUpAndLoginDTO.RequestSignUpDTO;
import com.rt.signUpAndLoginDTO.ResponseLoginDTO;
import com.rt.signUpAndLoginserviceImpl.SignUpImplementation;
import com.rt.signUpAndLoginserviceInterface.LoginServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
	private LoginServiceInterface loginService;
	
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
	
	//it is used to fetch all info for DashBoard.
	@GetMapping("/all-vehicle-parking-details")
	public ParkingDashBoardInfoDTO allVehicleAndParkingRecord() {
		ParkingDashBoardInfoDTO dashInfo=loginService.allVehicleAndParkingRecord();
		return dashInfo;
	}
	
	@PostMapping("/reset-password")
	public String resetPassword(@RequestParam String email,@RequestParam String pass) {
		
		String message=loginService.resetPassword(email,pass);
		return message;
		
	}
	

}
