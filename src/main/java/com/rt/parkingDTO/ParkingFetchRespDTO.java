package com.rt.parkingDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import com.rt.allEnums.ParkingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingFetchRespDTO {
	
	 	private int parkingId;
	 	private int vehicleId;
	    private String vehicleNumber;
	    private String vehicleType;
	    private int slotId;
	    private String slotValue;
	    private LocalDate inDate;
	    private LocalTime inTime; 
	    private LocalDate outDate;
	    private LocalTime outTime; 
	    private ParkingStatus status;

}
