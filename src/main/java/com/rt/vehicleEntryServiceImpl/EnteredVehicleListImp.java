package com.rt.vehicleEntryServiceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rt.vehicleAndParkingEntity.Vehicle;
import com.rt.vehicleEntryDTO.EnteredVehicleListRespDTO;
import com.rt.vehicleEntryRepository.VehicleRepository;
import com.rt.vehicleEntryServiceInterface.EnteredVehicleListInterface;

@Service
public class EnteredVehicleListImp implements EnteredVehicleListInterface{
	  @Autowired
	    private VehicleRepository enteredVehicleRepository;

	    @Override
	    public Page<EnteredVehicleListRespDTO> getVehiclesByType(String vehicleType, int page, int size, String search,
	                                                              LocalDate entryDate, int userId, String userRole) {
	        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
	        Page<Vehicle> pagedResult;

	        boolean isAdmin = "Admin".equalsIgnoreCase(userRole);

	        if (search != null && !search.isEmpty()) {
	            pagedResult = isAdmin
	                ? enteredVehicleRepository.findByVehicleNumberAndVehicleType(search, vehicleType, pageable)
	                : enteredVehicleRepository.findByVehicleTypeAndVehicleNumberLikeAndUsers_Id(vehicleType, search, userId, pageable);
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
