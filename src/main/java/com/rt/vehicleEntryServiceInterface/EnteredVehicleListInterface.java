package com.rt.vehicleEntryServiceInterface;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.rt.vehicleEntryDTO.EnteredVehicleListRespDTO;

public interface EnteredVehicleListInterface {
	
	Page<EnteredVehicleListRespDTO> getVehiclesByType(String vehicleType,String search, int page, int size,LocalDate entryDate,int userId,String userRole);
	
}
