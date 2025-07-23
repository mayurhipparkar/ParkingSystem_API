package com.rt.slotServiceImplementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rt.allEnums.SlotStatus;
import com.rt.slotDTO.CreateSlotRequestDTO;
import com.rt.slotDTO.CreateSlotResponseDTO;
import com.rt.slotEntity.FourWheelerSlots;
import com.rt.slotEntity.TwoWheelerSlots;
import com.rt.slotRepository.FourWheelerRepository;
import com.rt.slotRepository.TwoWheelerRepository;
import com.rt.slotServiceInterface.SlotServiceInterface;

@Service
public class SlotServiceImpl implements SlotServiceInterface{

	 @Autowired
	    private TwoWheelerRepository twoWheelerRepository;

	    @Autowired
	    private FourWheelerRepository fourWheelerRepository;
	    
// it is used to create slots.
	    @Override
	    public List<CreateSlotResponseDTO> createMultipleSlots(CreateSlotRequestDTO request) {
	        String type = request.getVehicleType() != null ? request.getVehicleType().trim().toLowerCase() : "";
	        int maxSlots = 30;

	        if ("two wheeler".equals(type)) {
	            long currentCount = twoWheelerRepository.count();
	            int allowedToCreate = maxSlots - (int) currentCount;

	            if (allowedToCreate <= 0) {
	                throw new IllegalStateException("Slot limit reached. Maximum 30 slots allowed for two wheeler.");
	            }

	            int slotsToCreate = Math.min(allowedToCreate, request.getSlotCount());
	            List<TwoWheelerSlots> slotsToSave = new ArrayList<>();
	            String prefix = "TW";

	            // Fetch last inserted slot number (assuming slotId format is "TW-001", "TW-002", etc.)
	            String lastSlotId = twoWheelerRepository.findLastSlotId(); // Custom query method
	            int start = 1;
	            if (lastSlotId != null && lastSlotId.startsWith(prefix)) {
	                try {
	                    start = Integer.parseInt(lastSlotId.substring(3)) + 1;
	                } catch (NumberFormatException ignored) {}
	            }

	            for (int i = 0; i < slotsToCreate; i++) {
	                TwoWheelerSlots slot = new TwoWheelerSlots();
	                slot.setSlotId(String.format("%s-%03d", prefix, start + i)); // TW-001, TW-002
	                slot.setVehicleType(type);
	                slot.setStatus(SlotStatus.UNPARKED);
	                slotsToSave.add(slot);
	            }

	            List<TwoWheelerSlots> savedSlots = twoWheelerRepository.saveAll(slotsToSave);

	            return savedSlots.stream()
	                    .map(slot -> new CreateSlotResponseDTO(
	                            slot.getId(),
	                            slot.getSlotId(),
	                            slot.getVehicleType(),
	                            slot.getStatus()))
	                    .toList();
	        }

	        if ("four wheeler".equals(type)) {
	            long currentCount = fourWheelerRepository.count();
	            int allowedToCreate = maxSlots - (int) currentCount;

	            if (allowedToCreate <= 0) {
	                throw new IllegalStateException("Slot limit reached. Maximum 30 slots allowed for four wheeler.");
	            }

	            int slotsToCreate = Math.min(allowedToCreate, request.getSlotCount());
	            List<FourWheelerSlots> slotsToSave = new ArrayList<>();
	            String prefix = "FW";

	            String lastSlotId = fourWheelerRepository.findLastSlotId(); // Custom query method
	            int start = 1;
	            if (lastSlotId != null && lastSlotId.startsWith(prefix)) {
	                try {
	                    start = Integer.parseInt(lastSlotId.substring(3)) + 1;
	                } catch (NumberFormatException ignored) {}
	            }

	            for (int i = 0; i < slotsToCreate; i++) {
	                FourWheelerSlots slot = new FourWheelerSlots();
	                slot.setSlotId(String.format("%s-%03d", prefix, start + i)); // FW-001, FW-002
	                slot.setVehicleType(type);
	                slot.setStatus(SlotStatus.UNPARKED);
	                slotsToSave.add(slot);
	            }

	            List<FourWheelerSlots> savedSlots = fourWheelerRepository.saveAll(slotsToSave);

	            return savedSlots.stream()
	                    .map(slot -> new CreateSlotResponseDTO(
	                            slot.getId(),
	                            slot.getSlotId(),
	                            slot.getVehicleType(),
	                            slot.getStatus()))
	                    .toList();
	        }

	        return Collections.emptyList();
	    }

//fetch all slots according to vehicle type.
	    @Override
	    public Page<CreateSlotResponseDTO> fetchVehicleSlots(int page, int size, String vType, SlotStatus status) {
	        Pageable pageable = PageRequest.of(page, size);

	        if (vType != null) {
	            String type = vType.trim().toLowerCase();

	            if ("four wheeler".equals(type)) {
	                Page<FourWheelerSlots> savedSlots;

	                if (status != null) {
	                    savedSlots = fourWheelerRepository.findByStatus(status, pageable);
	                    } else {
	                    savedSlots = fourWheelerRepository.findAll(pageable);
	                }

	                return savedSlots.map(slot -> new CreateSlotResponseDTO(
	                    slot.getId(),
	                    slot.getSlotId(),
	                    slot.getVehicleType(),
	                    slot.getStatus()));
	            } else if ("two wheeler".equals(type)) {
	                Page<TwoWheelerSlots> savedSlots;

	                if (status != null) {
	                    savedSlots = twoWheelerRepository.findByStatus(status, pageable);
	                } else {
	                    savedSlots = twoWheelerRepository.findAll(pageable);
	                }

	                return savedSlots.map(slot -> new CreateSlotResponseDTO(
	                    slot.getId(),
	                    slot.getSlotId(),
	                    slot.getVehicleType(),
	                    slot.getStatus()));
	            }
	        }

	        return Page.empty();
	    }
}
