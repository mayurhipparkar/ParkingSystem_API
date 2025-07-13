package com.rt.vehicleEntryMapper;

import org.springframework.stereotype.Component;

import com.rt.vehicleEntryDTO.FetchVehicleInfoRespDto;
import com.rt.vehicleEntryEntity.Vehicle;

@Component
public class FetchVehicleInfoMapper {
	
	public FetchVehicleInfoRespDto toDTO(Vehicle vehicle){
		return  new FetchVehicleInfoRespDto(vehicle.getId(),vehicle.getVehicleType(),
				vehicle.getVehicleNumber(),vehicle.getOwnerName(),
				vehicle.getContactNumber(),vehicle.getEntryDate(),vehicle.getEntryTime());	
	}

}
