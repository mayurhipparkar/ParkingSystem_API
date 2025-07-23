package com.rt.slotController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rt.allEnums.SlotStatus;
import com.rt.slotDTO.CreateSlotRequestDTO;
import com.rt.slotDTO.CreateSlotResponseDTO;
import com.rt.slotServiceInterface.SlotServiceInterface;

@RestController
@RequestMapping("/api")
public class SlotController {
	@Autowired
	private SlotServiceInterface slotServiceInterface;
	// it is used to create slots.
	@PostMapping("/create-multiple-slots")
    public List<CreateSlotResponseDTO> createMultipleSlots(@RequestBody CreateSlotRequestDTO request) {
		System.out.println("slot data in api : "+request.getVehicleType()+" "+request.getSlotCount());
		List<CreateSlotResponseDTO> slotList=slotServiceInterface.createMultipleSlots(request);
		
		return slotList;
        
	}
	
	
// it is used to fetch slots according to vehicle type.
	@PostMapping("/slots")
    public ResponseEntity<Map<String, Object>> fetchVehicleSlots(@RequestParam String vType,
    		 @RequestParam (required = false)String status,
    		 @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size) {
		
		SlotStatus enumStatus = null;
	    if (status != null) {
	        try {
	            enumStatus = SlotStatus.valueOf(status.toUpperCase()); // handles lowercase input too
	        } catch (IllegalArgumentException e) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value: " + status);
	        }
	    }
		System.out.println("slot data in api : "+vType);
		Page<CreateSlotResponseDTO> slotList=slotServiceInterface.fetchVehicleSlots(page,size,vType,enumStatus);
		 Map<String, Object> response = new HashMap<>();
		 	response.put("data", slotList.getContent());
	        response.put("currentPage", slotList.getNumber());
	        response.put("totalItems", slotList.getTotalElements());
	        response.put("totalPages", slotList.getTotalPages());
	        if (!slotList.isEmpty()) {
	            response.put("vehicleType", slotList.getContent().get(0).getVehicleType());
	        } else {
	            response.put("vehicleType", vType);
	            }
	        
	        if (!slotList.isEmpty()) {
	            response.put("status", slotList.getContent().get(0).getStatus());
	        } else {
	            response.put("status", status);
	            }
	        
	        return ResponseEntity.ok(response);
        
	}
	
	

}
