package com.rt.vehicleEntryServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.vehicleEntryDTO.AddVehicleRequestDto;
import com.rt.vehicleEntryDTO.AddVehicleRespDto;
import com.rt.vehicleEntryEntity.Vehicle;
import com.rt.vehicleEntryMapper.AddVehicleMapper;
import com.rt.vehicleEntryRepository.VehicleEntryRepo;
import com.rt.vehicleEntryServiceInterface.VehicleEntryInterface;

@Service
public class VehicleEntryServiceImp implements VehicleEntryInterface{
	@Autowired
	private  VehicleEntryRepo vehicleEntryRepo;
	
	@Autowired
	private  AddVehicleMapper addVehicleMapper;

	@Override
	public AddVehicleRespDto addVehicleInfo(AddVehicleRequestDto addVehicleReqDto) {
		
		Vehicle vehicleEntity=addVehicleMapper.toEntity(addVehicleReqDto);
		
		System.out.println("foreign key User "+vehicleEntity.getUsers().getEmail());
		
		Vehicle vehicleInfo=vehicleEntryRepo.save(vehicleEntity);
		
		AddVehicleRespDto vehicleRespDto=addVehicleMapper.toDTO(vehicleInfo);
		
		return vehicleRespDto;
	}

	
}
