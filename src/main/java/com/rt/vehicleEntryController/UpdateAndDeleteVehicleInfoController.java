package com.rt.vehicleEntryController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rt.vehicleEntryDTO.UpdateVehicleInfoReqDto;
import com.rt.vehicleEntryServiceInterface.UpdateAndDeleteVehicleInfoInterface;

@RestController
@RequestMapping("/update")
public class UpdateAndDeleteVehicleInfoController {
	
	@Autowired
	private UpdateAndDeleteVehicleInfoInterface updateAndDeleteVehicleInfoInterface;
	
	//mapped for update existing vehicle record.
	@PostMapping("/update-vehicle")
	public boolean updateVehicleInfo(@RequestBody UpdateVehicleInfoReqDto updateVehicleInfoReqDto) {
		boolean updatedData=updateAndDeleteVehicleInfoInterface.updateVehicleInfo(updateVehicleInfoReqDto);
		return updatedData;
		
	}
}
