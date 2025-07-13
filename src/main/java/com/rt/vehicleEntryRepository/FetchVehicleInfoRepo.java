package com.rt.vehicleEntryRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rt.vehicleEntryEntity.Vehicle;
@Repository
public interface FetchVehicleInfoRepo extends JpaRepository<Vehicle, Integer>{
	
	
	Optional<Vehicle> findById(Integer id);
	

}
