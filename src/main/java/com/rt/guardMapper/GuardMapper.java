package com.rt.guardMapper;

import org.springframework.stereotype.Component;

import com.rt.guardDTO.AddGuardReqDTO;
import com.rt.userEntity.Users;

@Component
public class GuardMapper {
	
	public Users toEntity(AddGuardReqDTO addGuardReqDTO) {
		return new Users(addGuardReqDTO.getFullname(),addGuardReqDTO.getEmail(),
				addGuardReqDTO.getNumber(),addGuardReqDTO.getAddress(),
				addGuardReqDTO.getPassword());
	
	}

}
