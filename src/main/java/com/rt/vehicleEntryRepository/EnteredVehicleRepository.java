package com.rt.vehicleEntryRepository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rt.vehicleEntryEntity.Vehicle;

public interface EnteredVehicleRepository extends JpaRepository<Vehicle,Integer>{

	//this method only for admin.
		Page<Vehicle> findByVehicleType(String vehicleType, Pageable pageable);
		Page<Vehicle> findByVehicleTypeAndEntryDate(String vehicleType, LocalDate entryDate, Pageable pageable);
		@Query("SELECT v FROM Vehicle v WHERE LOWER(v.vehicleNumber) LIKE LOWER(CONCAT('%', :vehicleNumber, '%')) AND LOWER(v.vehicleType) LIKE LOWER(CONCAT('%', :vehicleType, '%'))")
			Page<Vehicle> findByVehicleNumberAndVehicleType(@Param("vehicleNumber") String vehicleNumber,
				                                                 @Param("vehicleType") String vehicleType,
				                                                 Pageable pageable);
	
		
		//this methods are used for other roles.
	 	Page<Vehicle> findByVehicleTypeAndUsers_Id(String vehicleType, int userId, Pageable pageable);
	    Page<Vehicle> findByVehicleTypeAndVehicleNumberAndUsers_Id(String vehicleType, String vehicleNumber, int userId, Pageable pageable);
	    Page<Vehicle> findByVehicleTypeAndEntryDateAndUsers_Id(String vehicleType, LocalDate entryDate, int userId, Pageable pageable);
	
	   
	

}
