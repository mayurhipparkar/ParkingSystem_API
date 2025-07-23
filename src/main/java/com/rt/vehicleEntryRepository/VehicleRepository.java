package com.rt.vehicleEntryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rt.vehicleAndParkingEntity.Vehicle;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer>{
	
	
	Optional<Vehicle> findById(Integer id);
	
	//fetch today's vehicle entries
	List<Vehicle> findByEntryDateAndVehicleTypeIgnoreCaseAndUsers_Id(
		    LocalDate entryDate,
		    String vehicleType,
		    int userId
		);

	
	Optional<Vehicle> findByVehicleNumberAndEntryDate(String vehicleNumber, LocalDate entryDate);

	 Optional<Vehicle> findTopByVehicleNumber(String vehicleNumber);
	 
	 
	  // Admin
	    Page<Vehicle> findByVehicleType(String vehicleType, Pageable pageable);
	    Page<Vehicle> findByVehicleTypeAndEntryDate(String vehicleType, LocalDate entryDate, Pageable pageable);

	    @Query("SELECT v FROM Vehicle v WHERE " +
	           "LOWER(v.vehicleNumber) LIKE LOWER(CONCAT('%', :vehicleNumber, '%')) AND " +
	           "LOWER(v.vehicleType) LIKE LOWER(CONCAT('%', :vehicleType, '%'))")
	    Page<Vehicle> findByVehicleNumberAndVehicleType(@Param("vehicleNumber") String vehicleNumber,
	                                                    @Param("vehicleType") String vehicleType,
	                                                    Pageable pageable);

	    //Guard
	    Page<Vehicle> findByVehicleTypeAndUsers_Id(String vehicleType, int userId, Pageable pageable);
	    Page<Vehicle> findByVehicleTypeAndEntryDateAndUsers_Id(String vehicleType, LocalDate entryDate, int userId, Pageable pageable);

	    @Query("SELECT v FROM Vehicle v WHERE " +
	           "LOWER(v.vehicleNumber) LIKE LOWER(CONCAT('%', :vehicleNumber, '%')) AND " +
	           "LOWER(v.vehicleType) LIKE LOWER(CONCAT('%', :vehicleType, '%')) AND " +
	           "v.users.id = :userId")
	    Page<Vehicle> findByVehicleTypeAndVehicleNumberLikeAndUsers_Id(@Param("vehicleType") String vehicleType,
	                                                                    @Param("vehicleNumber") String vehicleNumber,
	                                                                    @Param("userId") int userId,
	                                                                    Pageable pageable);

		long countByEntryDate(LocalDate today);//only for dashboard.




}
