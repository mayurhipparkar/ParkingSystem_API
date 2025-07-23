package com.rt.parkingServiceInterface;

import java.util.List;
import java.util.Map;

import com.rt.parkingDTO.ParkingEntryReqDTO;
import com.rt.parkingDTO.ParkingEntryRespDTO;
import com.rt.parkingDTO.ParkingFeeReqDTO;
import com.rt.parkingDTO.ParkingFeeRespDTO;
import com.rt.parkingDTO.ParkingFetchRespDTO;

public interface ParkingServiceInterface {

	Map<String, Object> vehicleAndSlotData(String vType,int userId);
	
	String saveParkingEntry(ParkingEntryReqDTO dto);

	Map<String, Object> getparkingListByRole(int page, int size, int userId, String userRole);

	ParkingFetchRespDTO fetchSingleParkingById(int parkingId);

	String exitParkedVehicleById(ParkingFetchRespDTO parkingFetchRespDTO);

	String setParkingFee(ParkingFeeReqDTO parkingFeeReqDTO);

	 List<ParkingFeeRespDTO> parkingFeeList(String userRole);

	ParkingFeeRespDTO fetchParkingFeeById(int parkingFeeId);

	String updateParkingFee(ParkingFeeRespDTO parkingFeeRespDTO);

	List<ParkingEntryRespDTO> getparkingListByStatusFilter(String filter, String role, int id);

	List<ParkingEntryRespDTO> getparkingListByVehicleNumber(String search, String role, int id);

}
