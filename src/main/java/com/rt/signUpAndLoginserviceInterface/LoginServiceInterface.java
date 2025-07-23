package com.rt.signUpAndLoginserviceInterface;

import org.springframework.stereotype.Service;

import com.rt.signUpAndLoginDTO.ParkingDashBoardInfoDTO;
import com.rt.signUpAndLoginDTO.RequestLoginDTO;
import com.rt.signUpAndLoginDTO.ResponseLoginDTO;

@Service
public interface LoginServiceInterface {

	ResponseLoginDTO checkLogin(RequestLoginDTO reqLoginDto);
	
	ParkingDashBoardInfoDTO allVehicleAndParkingRecord();

	String resetPassword(String email, String pass);
	

}
