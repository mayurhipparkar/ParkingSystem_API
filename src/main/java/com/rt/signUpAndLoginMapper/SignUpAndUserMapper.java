package com.rt.signUpAndLoginMapper;

import org.springframework.stereotype.Component;

import com.rt.signUpAndLoginDTO.RequestSignUpDTO;
import com.rt.signUpAndLoginDTO.ResponseSignUpDTO;
import com.rt.userEntity.Users;

@Component
public class SignUpAndUserMapper {
	
	public ResponseSignUpDTO toDTO(Users users) {
		return new ResponseSignUpDTO(users.getId(),users.getFullname(),users.getEmail(),users.getAddress(),users.getRole(),users.getStatus());
	}

	
public  Users toEntity(RequestSignUpDTO signDto) {
		return new Users(signDto.getFullname(),signDto.getEmail(),signDto.getNumber(),signDto.getAddress(),signDto.getPassword(),signDto.getRole());
		
	}

}
