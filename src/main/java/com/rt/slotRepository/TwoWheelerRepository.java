package com.rt.slotRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rt.allEnums.SlotStatus;
import com.rt.slotEntity.TwoWheelerSlots;

@Repository
public interface TwoWheelerRepository extends JpaRepository<TwoWheelerSlots,Integer>{
	
	@Query("SELECT t.slotId FROM TwoWheelerSlots t ORDER BY t.id DESC LIMIT 1")
	String findLastSlotId();
	
	Page<TwoWheelerSlots> findByStatus(SlotStatus status, Pageable pageable);
	
  //Get all unparked (available) two-wheeler slots
	List<TwoWheelerSlots> findByStatus(SlotStatus unparked);
	
}
