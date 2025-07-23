package com.rt.parkingRepository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.parkingDTO.ParkingFeeRespDTO;
import com.rt.vehicleAndParkingEntity.ParkingFee;

@Repository
public interface ParkingFeeRepository extends JpaRepository<ParkingFee, Integer>{

	Optional<ParkingFee> findByVehicleTypeIgnoreCase(String vehicleType);

	Optional<ParkingFee> findByVehicleType(String string);


}
