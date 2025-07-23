package com.rt.vehicleEntryServiceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.vehicleAndParkingEntity.Vehicle;
import com.rt.vehicleEntryDTO.AddVehicleRequestDto;
import com.rt.vehicleEntryDTO.AddVehicleRespDto;
import com.rt.vehicleEntryMapper.AddVehicleMapper;
import com.rt.vehicleEntryRepository.VehicleRepository;
import com.rt.vehicleEntryServiceInterface.VehicleEntryInterface;

@Service
public class VehicleEntryServiceImp implements VehicleEntryInterface{
	@Autowired
	private  VehicleRepository vehicleEntryRepo;
	
	@Autowired
	private  AddVehicleMapper addVehicleMapper;

	@Override
	public AddVehicleRespDto addVehicleInfo(AddVehicleRequestDto addVehicleReqDto) {
		
		
		// Check if the vehicleNumber already exists for the same date
        Optional<Vehicle> existingVehicle = vehicleEntryRepo
                .findByVehicleNumberAndEntryDate(addVehicleReqDto.getVehicleNumber(), addVehicleReqDto.getEntryDate());

        if (existingVehicle.isPresent()) {
            throw new RuntimeException("Vehicle with number '" + addVehicleReqDto.getVehicleNumber() + 
                "' is already entered for date " + addVehicleReqDto.getEntryDate());
        }
        
        
     //Is vehicle number already used for another type (Four-Wheeler / Two-Wheeler)?
        Optional<Vehicle> vehicleWithSameNumberDifferentType = vehicleEntryRepo
                .findTopByVehicleNumber(addVehicleReqDto.getVehicleNumber());

        if (vehicleWithSameNumberDifferentType.isPresent()) {
            String existingType = vehicleWithSameNumberDifferentType.get().getVehicleType();
            String newType = addVehicleReqDto.getVehicleType();

            if (!existingType.equalsIgnoreCase(newType)) {
                throw new RuntimeException("Vehicle number '" + addVehicleReqDto.getVehicleNumber() +
                        "' is already registered as a " + existingType + ". It cannot be registered as a " + newType + ".");
            }
        }
        
		
		Vehicle vehicleEntity=addVehicleMapper.toEntity(addVehicleReqDto);
		
		System.out.println("foreign key User "+vehicleEntity.getUsers().getEmail());
		
		Vehicle vehicleInfo=vehicleEntryRepo.save(vehicleEntity);
		
		AddVehicleRespDto vehicleRespDto=addVehicleMapper.toDTO(vehicleInfo);
		
		return vehicleRespDto;
	}

	
}
