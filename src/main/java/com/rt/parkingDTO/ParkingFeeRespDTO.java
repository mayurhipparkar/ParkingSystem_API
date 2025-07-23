package com.rt.parkingDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingFeeRespDTO {
	
		private int parkingFeeId;
	 	private String vehicleType;
	    private BigDecimal hourlyFee;
	    private BigDecimal dailyFee;
	    //it is used for to show info on dashboard.
		public ParkingFeeRespDTO(String vehicleType, BigDecimal hourlyFee, BigDecimal dailyFee) {
			super();
			this.vehicleType = vehicleType;
			this.hourlyFee = hourlyFee;
			this.dailyFee = dailyFee;
		}
	    
	    


}
