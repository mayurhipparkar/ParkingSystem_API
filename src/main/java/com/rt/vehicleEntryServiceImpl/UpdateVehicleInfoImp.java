package com.rt.vehicleEntryServiceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.vehicleEntryDTO.UpdateVehicleInfoReqDto;
import com.rt.vehicleEntryEntity.Vehicle;
import com.rt.vehicleEntryMapper.UpdateVehicleInfoMapper;
import com.rt.vehicleEntryRepository.UpdateAndDeleteVehicleInfoRepo;
import com.rt.vehicleEntryServiceInterface.UpdateAndDeleteVehicleInfoInterface;

@Service
public class UpdateVehicleInfoImp implements UpdateAndDeleteVehicleInfoInterface{
@Autowired
private UpdateAndDeleteVehicleInfoRepo updateAndDeleteVehicleInfoRepo;
	@Autowired
	private UpdateVehicleInfoMapper updateVehicleInfoMapper;
	
	// it is used to update existing vehicle record and display the change.
	@Override
	public boolean updateVehicleInfo(UpdateVehicleInfoReqDto updateVehicleInfoReqDto) {
		Vehicle vehicle=updateVehicleInfoMapper.toEntity(updateVehicleInfoReqDto);
		Vehicle updatedData=updateAndDeleteVehicleInfoRepo.save(vehicle);
		if(updatedData!=null) {
			System.out.println("after updation id :"+updatedData.getId()+" "+" before updation id :"+updateVehicleInfoReqDto.getId());
			return true;
		}
		
		return false;
	}
	
}
