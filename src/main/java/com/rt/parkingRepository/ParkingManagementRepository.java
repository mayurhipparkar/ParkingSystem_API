package com.rt.parkingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.allEnums.ParkingStatus;
import com.rt.vehicleAndParkingEntity.Parking;
import com.rt.vehicleAndParkingEntity.Vehicle;

@Repository
public interface ParkingManagementRepository extends JpaRepository<Parking, Integer>{

	Optional<Parking> findByVehicleAndStatusOrderByIdDesc(Vehicle vehicle, ParkingStatus active);
	
	Page<Parking> findByUser_Id(int userId, Pageable pageable);

	long countByStatus(ParkingStatus active);//it is used for dashboard.

	long countByInDateAndStatus(LocalDate today, ParkingStatus active);//dashboard

	List<Parking> findByStatus(ParkingStatus parkingStatus);

	List<Parking> findByStatusAndUser_Id(ParkingStatus parkingStatus, int id);

	List<Parking> findByVehicle_VehicleNumberContainingIgnoreCase(String vehicleNumber);

	List<Parking> findByVehicle_VehicleNumberContainingIgnoreCaseAndUser_Id(String vehicleNumber, int userId);
	

}
