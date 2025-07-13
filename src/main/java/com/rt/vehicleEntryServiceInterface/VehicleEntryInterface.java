package com.rt.vehicleEntryServiceInterface;

import com.rt.vehicleEntryDTO.AddVehicleRequestDto;
import com.rt.vehicleEntryDTO.AddVehicleRespDto;

public interface VehicleEntryInterface {
	
	AddVehicleRespDto addVehicleInfo(AddVehicleRequestDto addVehicleReqDto);

}
