package com.rt.slotServiceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.rt.allEnums.SlotStatus;
import com.rt.slotDTO.CreateSlotRequestDTO;
import com.rt.slotDTO.CreateSlotResponseDTO;

public interface SlotServiceInterface {
	
	public List<CreateSlotResponseDTO> createMultipleSlots(CreateSlotRequestDTO request );

	public Page<CreateSlotResponseDTO> fetchVehicleSlots(int page,int size,String vType,SlotStatus status);

}
