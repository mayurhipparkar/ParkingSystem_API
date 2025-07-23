package com.rt.vehicleEntryServiceInterface;

import java.time.LocalDate;

import org.springframework.data.domain.Page;

import com.rt.vehicleEntryDTO.EnteredVehicleListRespDTO;

public interface EnteredVehicleListInterface {
	
	Page<EnteredVehicleListRespDTO> getVehiclesByType(String vehicleType,int page, int size,String search,LocalDate entryDate,int userId,String userRole);
	
}
