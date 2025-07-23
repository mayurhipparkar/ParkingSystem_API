package com.rt.slotDTO;

import com.rt.allEnums.SlotStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSlotResponseDTO {
	
	private int id;
	private String slotId;
	private String VehicleType;
	private SlotStatus status;

}
