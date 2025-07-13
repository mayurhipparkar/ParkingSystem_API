package com.rt.vehicleEntryDTO;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddVehicleRequestDto {
	
	private String vehicleType;
	private String vehicleNumber;
	private String ownerName;
	private String contactNumber;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate entryDate;
	
	 @DateTimeFormat(pattern = "HH:mm")
	private LocalTime entryTime;
	 
	 private int userId;
	 private String userRole;
}
