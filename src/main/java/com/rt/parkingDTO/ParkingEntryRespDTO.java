package com.rt.parkingDTO;

import java.math.BigDecimal;
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
    private String contactNumber;
    private String slotId;
    private LocalDate inDate;
    private LocalTime inTime; 
    private LocalDate outDate;
    private LocalTime outTime; 
    private ParkingStatus status;
    
    private BigDecimal hourlyFee;
    private BigDecimal dailyFee;
	public ParkingEntryRespDTO(int parkingId, String vehicleNumber, String vehicleType, String ownerName,
			String contactNumber, String slotId, LocalDate inDate, LocalTime inTime, LocalDate outDate,
			LocalTime outTime, ParkingStatus status) {
		super();
		this.parkingId = parkingId;
		this.vehicleNumber = vehicleNumber;
		this.vehicleType = vehicleType;
		this.ownerName = ownerName;
		this.contactNumber = contactNumber;
		this.slotId = slotId;
		this.inDate = inDate;
		this.inTime = inTime;
		this.outDate = outDate;
		this.outTime = outTime;
		this.status = status;
	}

    
    
	
}
