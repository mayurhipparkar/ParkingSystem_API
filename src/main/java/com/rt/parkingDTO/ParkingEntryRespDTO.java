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
public class ParkingEntryRespDTO {
	
    private int parkingId;
    private String vehicleNumber;
    private String vehicleType;
    private String ownerName;
    private String slotId;
    private LocalDate inDate;
    private LocalTime inTime; 
    private LocalDate outDate;
    private LocalTime outTime; 
    private ParkingStatus status;
	
}
