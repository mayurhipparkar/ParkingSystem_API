package com.rt.vehicleEntryMapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.signUpAndLoginRepository.LoginRepository;
import com.rt.userEntity.Users;
import com.rt.vehicleEntryDTO.AddVehicleRequestDto;
import com.rt.vehicleEntryDTO.AddVehicleRespDto;
import com.rt.vehicleEntryEntity.Vehicle;

@Component
public class AddVehicleMapper {
	
	@Autowired
	private LoginRepository loginRepo;//it is used to find user and set to entity. 
	
	public AddVehicleRespDto toDTO(Vehicle vehicle) {
		return new AddVehicleRespDto(vehicle.getId(),vehicle.getVehicleType(),vehicle.getVehicleNumber(),vehicle.getOwnerName(),vehicle.getContactNumber(),vehicle.getEntryDate(),vehicle.getEntryTime());
	}
	
	public Vehicle toEntity(AddVehicleRequestDto addVehicleReqDto) {
		Users users=null;
		Optional<Users> userData = loginRepo.findById(addVehicleReqDto.getUserId());
		if(userData.isPresent()) {
		 users=userData.get();
		}
		return new Vehicle(addVehicleReqDto.getVehicleType(),addVehicleReqDto.getVehicleNumber(),addVehicleReqDto.getOwnerName(),addVehicleReqDto.getContactNumber(),addVehicleReqDto.getEntryDate(),addVehicleReqDto.getEntryTime(),users);
		
		
	}

}
