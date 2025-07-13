package com.rt.vehicleEntryMapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.signUpAndLoginRepository.LoginRepository;
import com.rt.userEntity.Users;
import com.rt.vehicleEntryDTO.UpdateVehicleInfoReqDto;
import com.rt.vehicleEntryDTO.UpdateVehicleInfoRespDto;
import com.rt.vehicleEntryEntity.Vehicle;

@Component
public class UpdateVehicleInfoMapper {
	
	@Autowired
	private LoginRepository loginRepo;//it is used to find user and set to entity. 
	
	public UpdateVehicleInfoRespDto toDTO(Vehicle vehicle) {
		return new UpdateVehicleInfoRespDto(vehicle.getId(),vehicle.getVehicleType(),vehicle.getVehicleNumber(),vehicle.getOwnerName(),vehicle.getContactNumber(),vehicle.getEntryDate(),vehicle.getEntryTime());
		
	}
	
	public Vehicle toEntity(UpdateVehicleInfoReqDto UpdateVehicleInfoReqDto) {
		Users users=null;
		Optional<Users> userData = loginRepo.findById(UpdateVehicleInfoReqDto.getSessionUserId());
		
		if(userData.isPresent()) {
		 users=userData.get();
			
		}
		
		return new Vehicle(UpdateVehicleInfoReqDto.getId(),UpdateVehicleInfoReqDto.getVehicleType(),UpdateVehicleInfoReqDto.getVehicleNumber(),UpdateVehicleInfoReqDto.getOwnerName(),UpdateVehicleInfoReqDto.getContactNumber(),UpdateVehicleInfoReqDto.getEntryDate(),UpdateVehicleInfoReqDto.getEntryTime(),users);
		
		
	}

}
