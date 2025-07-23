package com.rt.signUpAndLoginDTO;


import com.rt.parkingDTO.ParkingFeeRespDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDashBoardInfoDTO {
	
	 private long totalVehicleCount;
	    private long totalParkedVehicleCount;
	    
	    private long todayEnteredVehicleCount;
	    private long todayParkedVehicleCount;
	    private ParkingFeeRespDTO twoWheelerFee;
	    private ParkingFeeRespDTO fourWheelerFee;

}
