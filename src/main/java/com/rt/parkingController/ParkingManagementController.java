package com.rt.parkingController;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rt.parkingDTO.ParkingEntryReqDTO;
import com.rt.parkingDTO.ParkingEntryRespDTO;
import com.rt.parkingDTO.ParkingFeeReqDTO;
import com.rt.parkingDTO.ParkingFeeRespDTO;
import com.rt.parkingDTO.ParkingFetchRespDTO;
import com.rt.parkingServiceInterface.ParkingServiceInterface;

@RestController
@RequestMapping("/api")
public class ParkingManagementController {

    @Autowired
    private ParkingServiceInterface parkingServiceInterface;

    @GetMapping("/vehicle-and-slot-data")
    public ResponseEntity<Map<String, Object>> vehicleAndSlotData(@RequestParam String vType,@RequestParam int userId) {
        System.out.println(">>> [API] GET /api/vehicle-and-slot-data");
        System.out.println(">>> [API] Vehicle Type Received: " + vType);

        if (vType == null || vType.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Vehicle type is required"));
        }

        Map<String, Object> data = parkingServiceInterface.vehicleAndSlotData(vType,userId);

        // Log sizes for debugging
        Object vehicleList = data.get("vehicles");
        Object slotList = data.get("slots");

        System.out.println(">>> [API] Vehicles returned: " + (vehicleList instanceof java.util.List ? ((java.util.List<?>) vehicleList).size() : 0));
        System.out.println(">>> [API] Slots returned: " + (slotList instanceof java.util.List ? ((java.util.List<?>) slotList).size() : 0));

        return ResponseEntity.ok(data);
    }
    
    //it is used to assign slot to vehicle.
    @PostMapping("/assign-slot")
    public String saveParkingEntry(@RequestBody ParkingEntryReqDTO parkingEntryReqDTO) {
    	
    	String message=parkingServiceInterface.saveParkingEntry(parkingEntryReqDTO);
    	return message;
    }
    
    //it is used to fetch all.
    @GetMapping("/all-parking-list")
    public ResponseEntity<Map<String, Object>> getparkingListByRole(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String userRole,
            @RequestParam int userId) {

        Map<String, Object> response = parkingServiceInterface.getparkingListByRole(page, size, userId, userRole);
        return ResponseEntity.ok(response);
    }
    
    //it is used to fetch parking record based on status.
    @GetMapping("/all-parking-list-by-filter")
    public List<ParkingEntryRespDTO> getparkingListByStatusFilter(@RequestParam String status,@RequestParam String role,@RequestParam int id) {
    	List<ParkingEntryRespDTO> list=	parkingServiceInterface.getparkingListByStatusFilter(status,role,id);
		return list;
    }
    
    
  //it is used to fetch parking record based on status.
    @GetMapping("/all-parking-list-by-filter-number")
    public List<ParkingEntryRespDTO> getparkingListByVehicleNumber(@RequestParam String search,@RequestParam String role,@RequestParam int id) {
    	List<ParkingEntryRespDTO> list=	parkingServiceInterface.getparkingListByVehicleNumber(search,role,id);
		return list;
    }
    
    //it is used to fetch particular record based on id.
    @GetMapping("/fetch-parking")
    public ParkingFetchRespDTO fetchSingleParkingById(@RequestParam int parkingId) {
    	ParkingFetchRespDTO respDto=parkingServiceInterface.fetchSingleParkingById(parkingId);
		return respDto;
    }
    
    //it is used to exit vehicle form parking.
    @PostMapping("/exit-vehicle-parking")
    public String exitParkedVehicleById(@RequestBody ParkingFetchRespDTO parkingFetchRespDTO) {
    	String message=parkingServiceInterface.exitParkedVehicleById(parkingFetchRespDTO);
		return message;
    }
    
   //it is used to set parking fee based on vehicle type and then save. 
    @PostMapping("/save-parking-fee")
    public String setParkingFee(@RequestBody ParkingFeeReqDTO parkingFeeReqDTO) {
    	String message=parkingServiceInterface.setParkingFee(parkingFeeReqDTO);
		return message;
    }
    
    
  //it is used to fetch all record of parking fee.
    @GetMapping("/parking-fee-list")
    public  List<ParkingFeeRespDTO> parkingFeeList(@RequestParam String userRole) {
    	 List<ParkingFeeRespDTO> respDto=parkingServiceInterface.parkingFeeList(userRole);
		return respDto;
    }
    
  //it is used to fetch all record of parking fee.
    @GetMapping("/fetch-parking-fee")
    public  ParkingFeeRespDTO fetchParkingFeeById(@RequestParam int parkingFeeId) {
    	 ParkingFeeRespDTO respDto=parkingServiceInterface.fetchParkingFeeById(parkingFeeId);
		return respDto;
    }
    
    
    //it is used to update parking fee record.
    @PostMapping("/update-parking-fee")
    public  String updateParkingFee(@RequestBody ParkingFeeRespDTO parkingFeeRespDTO) {
    	 String message=parkingServiceInterface.updateParkingFee(parkingFeeRespDTO);
		return message;
    }
    
    
}
