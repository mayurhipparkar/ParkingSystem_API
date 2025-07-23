package com.rt.slotRepository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rt.allEnums.SlotStatus;
import com.rt.slotEntity.FourWheelerSlots;

@Repository
public interface FourWheelerRepository extends JpaRepository<FourWheelerSlots,Integer>{
	
	@Query("SELECT f.slotId FROM FourWheelerSlots f ORDER BY f.id DESC LIMIT 1")
	String findLastSlotId();

	Page<FourWheelerSlots> findByStatus(SlotStatus status, Pageable pageable);
	
	// Get all unparked (available) four-wheeler slots
	List<FourWheelerSlots> findByStatus(SlotStatus unparked);
}
