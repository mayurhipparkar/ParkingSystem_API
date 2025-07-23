package com.rt.parkingDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingEntryReqDTO {
	
    private int vehicleId;
    private int slotId;
    private String vehicleType;
    private LocalDate inDate;
    private LocalTime inTime;
    private int userId;
	
}
