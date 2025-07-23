package com.rt.vehicleEntryServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.vehicleAndParkingEntity.Vehicle;
import com.rt.vehicleEntryDTO.FetchVehicleInfoRespDto;
import com.rt.vehicleEntryMapper.FetchVehicleInfoMapper;
import com.rt.vehicleEntryRepository.VehicleRepository;
import com.rt.vehicleEntryServiceInterface.FetchVehicleInfoInterface;

@Service
public class FetchVehicleInfoImp implements FetchVehicleInfoInterface{
	@Autowired
	private VehicleRepository fetchVehicleInfoRepo;
	
	@Autowired
	private FetchVehicleInfoMapper fetchVehicleInfoMapper;
	
	@Override
	public FetchVehicleInfoRespDto fetchVehicleInfoById(int id) {
		
		Optional<Vehicle> optionalWrapper=fetchVehicleInfoRepo.findById(id);
		
		if(optionalWrapper.isPresent()) {
			Vehicle entityResponse=optionalWrapper.get();
			FetchVehicleInfoRespDto fetchVehicleInfoRespDto =fetchVehicleInfoMapper.toDTO(entityResponse);
		
			System.out.println("fetch id for update :"+fetchVehicleInfoRespDto.getId());
			return fetchVehicleInfoRespDto;
			
		}
		return null;
	}

}
