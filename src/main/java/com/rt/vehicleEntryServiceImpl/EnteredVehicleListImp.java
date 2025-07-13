package com.rt.vehicleEntryServiceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rt.vehicleEntryDTO.EnteredVehicleListRespDTO;
import com.rt.vehicleEntryEntity.Vehicle;
import com.rt.vehicleEntryRepository.EnteredVehicleRepository;
import com.rt.vehicleEntryServiceInterface.EnteredVehicleListInterface;

@Service
public class EnteredVehicleListImp implements EnteredVehicleListInterface{

	@Autowired
	private EnteredVehicleRepository enteredVehicleRepository;

	@Override
	public Page<EnteredVehicleListRespDTO> getVehiclesByType(String vehicleType, String search, int page, int size, LocalDate entryDate, int userId, String userRole) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
	    Page<Vehicle> pagedResult;

	    boolean isAdmin = "Admin".equalsIgnoreCase(userRole);

	    if (search != null && !search.isEmpty()) {
	        pagedResult = isAdmin
	            ? enteredVehicleRepository.findByVehicleNumberAndVehicleType(vehicleType, search, pageable)
	            : enteredVehicleRepository.findByVehicleTypeAndVehicleNumberAndUsers_Id(vehicleType, search, userId, pageable);
	    } else if (entryDate != null) {
	        pagedResult = isAdmin
	            ? enteredVehicleRepository.findByVehicleTypeAndEntryDate(vehicleType, entryDate, pageable)
	            : enteredVehicleRepository.findByVehicleTypeAndEntryDateAndUsers_Id(vehicleType, entryDate, userId, pageable);
	    } else {
	        pagedResult = isAdmin
	            ? enteredVehicleRepository.findByVehicleType(vehicleType, pageable)
	            : enteredVehicleRepository.findByVehicleTypeAndUsers_Id(vehicleType, userId, pageable);
	    }

	    return pagedResult.map(vehicle -> new EnteredVehicleListRespDTO(
	            vehicle.getId(),
	            vehicle.getVehicleType(),
	            vehicle.getVehicleNumber(),
	            vehicle.getOwnerName(),
	            vehicle.getContactNumber(),
	            vehicle.getEntryDate(),
	            vehicle.getEntryTime()
	    ));
	}


}
