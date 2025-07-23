package com.rt.parkingDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingFeeReqDTO {
	
	 	private String vehicleType;
	    private BigDecimal hourlyFee;
	    private BigDecimal dailyFee;
	    private int userId;


}
