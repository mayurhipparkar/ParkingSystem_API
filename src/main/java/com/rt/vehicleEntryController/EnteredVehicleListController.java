package com.rt.vehicleEntryController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rt.vehicleEntryDTO.EnteredVehicleListRespDTO;
import com.rt.vehicleEntryServiceInterface.EnteredVehicleListInterface;


@RestController
@RequestMapping("/list")
public class EnteredVehicleListController {
	
	@Autowired
	private EnteredVehicleListInterface enteredVehicleListInterface;
	
	//it is used to get whole record based on type with pagination.
	    @GetMapping("/type")
	    public ResponseEntity<Map<String, Object>> getVehicles(
	            @RequestParam String vehicleType,
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            @RequestParam(required = false) String search,
	            @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate entryDate,
	            @RequestParam int userId,
	            @RequestParam String userRole) {

	        Page<EnteredVehicleListRespDTO> vehiclePage = enteredVehicleListInterface.getVehiclesByType(vehicleType,search,page, size,entryDate,userId,userRole);
	        Map<String, Object> response = new HashMap<>();
	        response.put("data", vehiclePage.getContent());
	        response.put("currentPage", vehiclePage.getNumber());
	        response.put("totalItems", vehiclePage.getTotalElements());
	        response.put("totalPages", vehiclePage.getTotalPages());

	        return ResponseEntity.ok(response);
	    } 
	}


