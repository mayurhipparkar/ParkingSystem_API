package com.rt.signUpAndLoginserviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.signUpAndLoginDTO.RequestLoginDTO;
import com.rt.signUpAndLoginDTO.ResponseLoginDTO;
import com.rt.signUpAndLoginRepository.LoginRepository;
import com.rt.signUpAndLoginserviceInterface.LoginServiceInterface;

@Service
public class LoginServiceImplementation implements LoginServiceInterface{

	@Autowired
	private LoginRepository loginRepo;//it extended the JPA repository 
	
	@Override
	public ResponseLoginDTO checkLogin(RequestLoginDTO reqLoginDto) {
		Optional<ResponseLoginDTO> optional = loginRepo.loginUser(reqLoginDto.getEmail(),reqLoginDto.getPassword());
		if (optional.isPresent()) {
			ResponseLoginDTO respLoginDto = optional.get();	
			return respLoginDto;
		}
		return null;	
	}
	
}
