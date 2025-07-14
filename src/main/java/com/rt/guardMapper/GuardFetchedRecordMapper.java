package com.rt.guardMapper;

import org.springframework.stereotype.Component;

import com.rt.guardDTO.FetchGuardDetailsReqDTO;
import com.rt.guardDTO.FetchGuardDetailsRespDTO;
import com.rt.userEntity.Users;

@Component
public class GuardFetchedRecordMapper {
	public Users toEntity(FetchGuardDetailsReqDTO fetchGuardDetailsReqDTO) {
		 Users user=new Users();
		 user.setId(fetchGuardDetailsReqDTO.getId());
		 user.setRole(fetchGuardDetailsReqDTO.getRole());
		 return user;
	}

	public FetchGuardDetailsRespDTO toDTO(Users user) {
		FetchGuardDetailsRespDTO dto=new FetchGuardDetailsRespDTO();
		dto.setId(user.getId());
		dto.setFullname(user.getFullname());
		dto.setEmail(user.getEmail());
		dto.setNumber(user.getNumber());
		dto.setPassword(user.getPassword());
		dto.setAddress(user.getAddress());
		dto.setRole(user.getRole());
		dto.setStatus(user.getStatus());
		 return dto;
	}

}
