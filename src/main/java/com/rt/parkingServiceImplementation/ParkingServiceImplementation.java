package com.rt.parkingServiceImplementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rt.allEnums.ParkingStatus;
import com.rt.allEnums.SlotStatus;
import com.rt.parkingDTO.ParkingEntryReqDTO;
import com.rt.parkingDTO.ParkingEntryRespDTO;
import com.rt.parkingDTO.ParkingFeeReqDTO;
import com.rt.parkingDTO.ParkingFeeRespDTO;
import com.rt.parkingDTO.ParkingFetchRespDTO;
import com.rt.parkingRepository.ParkingFeeRepository;
import com.rt.parkingRepository.ParkingManagementRepository;
import com.rt.parkingServiceInterface.ParkingServiceInterface;
import com.rt.signUpAndLoginRepository.UsersRepository;
import com.rt.slotEntity.FourWheelerSlots;
import com.rt.slotEntity.TwoWheelerSlots;
import com.rt.slotRepository.FourWheelerRepository;
import com.rt.slotRepository.TwoWheelerRepository;
import com.rt.userEntity.Users;
import com.rt.vehicleAndParkingEntity.Parking;
import com.rt.vehicleAndParkingEntity.ParkingFee;
import com.rt.vehicleAndParkingEntity.Vehicle;
import com.rt.vehicleEntryDTO.FetchVehicleInfoRespDto;
import com.rt.vehicleEntryRepository.VehicleRepository;

@Service
public class ParkingServiceImplementation implements ParkingServiceInterface{
	
	@Autowired
	private FourWheelerRepository fourWheelerRepository;
	
	@Autowired
	private TwoWheelerRepository twoWheelerRepository;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private ParkingManagementRepository parkingManagementRepository;
	
	@Autowired
	private ParkingFeeRepository parkingFeeRepository;// fee repo.

	//it is used to get vehicle list and slot list to parking form.
	@Override
	public Map<String, Object> vehicleAndSlotData(String vType,int userId) {
	    Map<String, Object> data = new HashMap<>();

	    LocalDate today = LocalDate.now();
	
	    System.out.println(">>> [Service] vType received: " + vType);
	    System.out.println(">>> [Service] Fetching data for Date: " + today);

	    if ("two Wheeler".equalsIgnoreCase(vType)) {
	        List<Vehicle> vehicles = vehicleRepository
	            .findByEntryDateAndVehicleTypeIgnoreCaseAndUsers_Id(
	                today,"two wheeler",userId
	            );
	        
	        List<FetchVehicleInfoRespDto> vehicleDTOs = vehicles.stream()
	        	    .map(v -> new FetchVehicleInfoRespDto(
	        	        v.getId(),
	        	        v.getVehicleType(),
	        	        v.getVehicleNumber(),
	        	        v.getOwnerName(),
	        	        v.getContactNumber(),
	        	        v.getEntryDate(),
	        	        v.getEntryTime()
	        	    ))
	        	    .collect(Collectors.toList());

	        List<TwoWheelerSlots> slots = twoWheelerRepository.findByStatus(SlotStatus.UNPARKED);

	        System.out.println(">>> [Service] Two-Wheeler Vehicles Found: " + vehicles.size());
	        System.out.println(">>> [Service] Two-Wheeler Slots Found: " + slots.size());

	        data.put("vehicles", vehicleDTOs);
	        data.put("slots", slots);

	    } else if ("four Wheeler".equalsIgnoreCase(vType)) {
	        List<Vehicle> vehicles = vehicleRepository
	            .findByEntryDateAndVehicleTypeIgnoreCaseAndUsers_Id(
	                today,"four wheeler",userId
	            );
	        
	        
	        List<FetchVehicleInfoRespDto> vehicleDTOs = vehicles.stream()
	        	    .map(v -> new FetchVehicleInfoRespDto(
	        	        v.getId(),
	        	        v.getVehicleType(),
	        	        v.getVehicleNumber(),
	        	        v.getOwnerName(),
	        	        v.getContactNumber(),
	        	        v.getEntryDate(),
	        	        v.getEntryTime()
	        	    ))
	        	    .collect(Collectors.toList());

	        List<FourWheelerSlots> slots = fourWheelerRepository.findByStatus(SlotStatus.UNPARKED);

	        System.out.println(">>> [Service] Four-Wheeler Vehicles Found: " + vehicles.size());
	        System.out.println(">>> [Service] Four-Wheeler Slots Found: " + slots.size());

	        data.put("vehicles", vehicleDTOs);
	        data.put("slots", slots);
	    } else {
	        System.out.println(">>> [Service] No matching type. Check value: " + vType);
	    }

	    return data;
	}
	
	//it is used to save parking record in db.
	@Override
    public String saveParkingEntry(ParkingEntryReqDTO dto) {
		
        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Check ACTIVE session for same vehicle
        parkingManagementRepository.findByVehicleAndStatusOrderByIdDesc(vehicle, ParkingStatus.ACTIVE)
                .ifPresent(activeSession -> {
                    throw new RuntimeException("Vehicle is already parked (ACTIVE)");
                });

        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //converted into entity
        Parking parking = new Parking();
        parking.setVehicle(vehicle);
        parking.setUser(user);
        parking.setInDate(dto.getInDate());
        parking.setInTime(dto.getInTime());
        parking.setStatus(ParkingStatus.ACTIVE);

        if ("two Wheeler".equalsIgnoreCase(dto.getVehicleType())) {
            TwoWheelerSlots slot = twoWheelerRepository.findById(dto.getSlotId())
                    .orElseThrow(() -> new RuntimeException("Two-wheeler slot not found"));
            
         // Set slot status to PARKED
            slot.setStatus(SlotStatus.PARKED); 
            twoWheelerRepository.save(slot); // Save updated slot
            parking.setTwoWheelerSlot(slot);
            
        } else if ("four Wheeler".equalsIgnoreCase(dto.getVehicleType())) {
            FourWheelerSlots slot = fourWheelerRepository.findById(dto.getSlotId())
                    .orElseThrow(() -> new RuntimeException("Four-wheeler slot not found"));
           
            //Set slot status to PARKED
            slot.setStatus(SlotStatus.PARKED); 
            fourWheelerRepository.save(slot); //Save updated slot
            parking.setFourWheelerSlot(slot);
            
        } else {
            throw new RuntimeException("Invalid vehicle type: " + dto.getVehicleType());
        }

       Parking isAdded=parkingManagementRepository.save(parking);
       if(isAdded!=null) {
    	   return "Vehicle Parked Successfully";
       }
       return "Something Wrong...!"; 
    }

	//it is used to fetch all record based on user.
	@Override
	public Map<String, Object> getparkingListByRole(int page, int size, int userId, String userRole) {
	    Map<String, Object> response = new HashMap<>();

	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
	    Page<Parking> parkingPage;

	    if ("ADMIN".equalsIgnoreCase(userRole)) {
	        parkingPage = parkingManagementRepository.findAll(pageable);
	    } else if ("GUARD".equalsIgnoreCase(userRole)) {
	        parkingPage = parkingManagementRepository.findByUser_Id(userId, pageable);
	    } else {
	        throw new RuntimeException("Access denied: unknown user role.");
	    }

	    List<ParkingEntryRespDTO> dtoList = parkingPage.getContent().stream().map(parking -> {
	        String vehicleType = parking.getVehicle().getVehicleType().toString();

	        // Determine slot ID
	        String slotId = null;
	        if (parking.getTwoWheelerSlot() != null) {
	            slotId = parking.getTwoWheelerSlot().getSlotId();
	        } else if (parking.getFourWheelerSlot() != null) {
	            slotId = parking.getFourWheelerSlot().getSlotId();
	        }

	        // Get fees
	        Optional<ParkingFee> feeOpt = parkingFeeRepository.findByVehicleTypeIgnoreCase(vehicleType);
	        BigDecimal hourlyFee = BigDecimal.ZERO;
	        BigDecimal dailyFee = BigDecimal.ZERO;

	        if (feeOpt.isPresent()) {
	            hourlyFee = feeOpt.get().getHourlyFee();
	            dailyFee = feeOpt.get().getDailyFee();
	        }

	        // Create DTO and set fees
	        ParkingEntryRespDTO dto = new ParkingEntryRespDTO(
	            parking.getId(),
	            parking.getVehicle().getVehicleNumber(),
	            vehicleType,
	            parking.getVehicle().getOwnerName(),
	            parking.getVehicle().getContactNumber(),
	            slotId,
	            parking.getInDate(),
	            parking.getInTime(),
	            parking.getOutDate(),
	            parking.getOutTime(),
	            parking.getStatus()
	        );
	        dto.setHourlyFee(hourlyFee);
	        dto.setDailyFee(dailyFee);
	        return dto;
	    }).toList();

	    response.put("data", dtoList);
	    response.put("currentPage", parkingPage.getNumber());
	    response.put("totalItems", parkingPage.getTotalElements());
	    response.put("totalPages", parkingPage.getTotalPages());

	    return response;
	}

	//fetch particular record based on id.
	@Override
	public ParkingFetchRespDTO fetchSingleParkingById(int parkingId) {

	    Parking parking = parkingManagementRepository.findById(parkingId)
	            .orElseThrow(() -> new RuntimeException("Parking record not found with ID: " + parkingId));

	    // Determine slot info
	    int sId = 0;
	    String slotId = null;
	    if (parking.getTwoWheelerSlot() != null) {
	        sId = parking.getTwoWheelerSlot().getId();
	        slotId = parking.getTwoWheelerSlot().getSlotId();
	    } else if (parking.getFourWheelerSlot() != null) {
	        sId = parking.getFourWheelerSlot().getId();
	        slotId = parking.getFourWheelerSlot().getSlotId();
	    }

	    return new ParkingFetchRespDTO(
	            parking.getId(),
	            parking.getVehicle().getId(),
	            parking.getVehicle().getVehicleNumber(),
	            parking.getVehicle().getVehicleType().toString(),
	            sId,
	            slotId,
	            parking.getInDate(),
	            parking.getInTime(),
	            parking.getOutDate(),
	            parking.getOutTime(),
	            parking.getStatus()
	    );
	}

	//exit vehicle parking by using parking id.
	@Override
	public String exitParkedVehicleById(ParkingFetchRespDTO parkingFetchRespDTO) {

	    // Fetch the parking record
	    Parking parking = parkingManagementRepository.findById(parkingFetchRespDTO.getParkingId())
	            .orElseThrow(() -> new RuntimeException("Parking record not found"));

	    // ✅ Check if vehicle is already exited
	    if (parking.getStatus() == ParkingStatus.EXITED) {
	        return "Vehicle already exited";
	    }

	    // Update outDate, outTime, status
	    parking.setOutDate(parkingFetchRespDTO.getOutDate());
	    parking.setOutTime(parkingFetchRespDTO.getOutTime());
	    parking.setStatus(ParkingStatus.EXITED);

	    // Update slot status
	    if (parking.getTwoWheelerSlot() != null) {
	        TwoWheelerSlots slot = parking.getTwoWheelerSlot();
	        slot.setStatus(SlotStatus.UNPARKED);
	        twoWheelerRepository.save(slot);
	    } else if (parking.getFourWheelerSlot() != null) {
	        FourWheelerSlots slot = parking.getFourWheelerSlot();
	        slot.setStatus(SlotStatus.UNPARKED);
	        fourWheelerRepository.save(slot);
	    }

	    // Save updated parking record
	    parkingManagementRepository.save(parking);

	    return "Vehicle exited successfully";
	}
	
	
	//it is used to fetch parking record based on parking status.
	@Override
	public List<ParkingEntryRespDTO> getparkingListByStatusFilter(String status, String role, int id) {
	    ParkingStatus parkingStatus;

	    try {
	        parkingStatus = ParkingStatus.valueOf(status.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        throw new RuntimeException("Invalid parking status: " + status);
	    }

	    List<Parking> parkingList;

	    if ("ADMIN".equalsIgnoreCase(role)) {
	        parkingList = parkingManagementRepository.findByStatus(parkingStatus);
	    } else if ("GUARD".equalsIgnoreCase(role)) {
	        parkingList = parkingManagementRepository.findByStatusAndUser_Id(parkingStatus, id);
	    } else {
	        throw new RuntimeException("Access denied: unknown user role.");
	    }

	    return parkingList.stream().map(parking -> {
	        String slotId = null;
	        if (parking.getTwoWheelerSlot() != null) {
	            slotId = parking.getTwoWheelerSlot().getSlotId();
	        } else if (parking.getFourWheelerSlot() != null) {
	            slotId = parking.getFourWheelerSlot().getSlotId();
	        }

	        return new ParkingEntryRespDTO(
	            parking.getId(),
	            parking.getVehicle().getVehicleNumber(),
	            parking.getVehicle().getVehicleType().toString(),
	            parking.getVehicle().getOwnerName(),
	            parking.getVehicle().getContactNumber(),
	            slotId,
	            parking.getInDate(),
	            parking.getInTime(),
	            parking.getOutDate(),
	            parking.getOutTime(),
	            parking.getStatus()
	        );
	    }).collect(Collectors.toList());
	}
	
	//it is used to fetch parking record based on search of vehicle number.
	@Override
	public List<ParkingEntryRespDTO> getparkingListByVehicleNumber(String vehicleNumber, String role, int userId) {
	    List<Parking> parkingList;

	    if ("ADMIN".equalsIgnoreCase(role)) {
	        parkingList = parkingManagementRepository.findByVehicle_VehicleNumberContainingIgnoreCase(vehicleNumber);
	    } else if ("GUARD".equalsIgnoreCase(role)) {
	        parkingList = parkingManagementRepository.findByVehicle_VehicleNumberContainingIgnoreCaseAndUser_Id(vehicleNumber, userId);
	    } else {
	        throw new RuntimeException("Access denied: unknown user role.");
	    }

	    return parkingList.stream().map(parking -> {
	        String slotId = null;
	        if (parking.getTwoWheelerSlot() != null) {
	            slotId = parking.getTwoWheelerSlot().getSlotId();
	        } else if (parking.getFourWheelerSlot() != null) {
	            slotId = parking.getFourWheelerSlot().getSlotId();
	        }

	        return new ParkingEntryRespDTO(
	            parking.getId(),
	            parking.getVehicle().getVehicleNumber(),
	            parking.getVehicle().getVehicleType().toString(),
	            parking.getVehicle().getOwnerName(),
	            parking.getVehicle().getContactNumber(),
	            slotId,
	            parking.getInDate(),
	            parking.getInTime(),
	            parking.getOutDate(),
	            parking.getOutTime(),
	            parking.getStatus()
	        );
	    }).collect(Collectors.toList());
	}
	
//save parking fee based on vehicle type.
	@Override
	public String setParkingFee(ParkingFeeReqDTO parkingFeeReqDTO) {
	    String vehicleType = parkingFeeReqDTO.getVehicleType().trim().toLowerCase();

	    // Check if record already exists
	    boolean exists = parkingFeeRepository
	                        .findByVehicleTypeIgnoreCase(vehicleType)
	                        .isPresent();

	    if (exists) {
	        return "Fee already set for " + parkingFeeReqDTO.getVehicleType();
	    }
	    
	 //Fetch user
	    Users user = usersRepository.findById(parkingFeeReqDTO.getUserId())
	            .orElseThrow(() -> new RuntimeException("User not found with ID: " + parkingFeeReqDTO.getUserId()));


	    // Convert DTO to Entity and Save
	    ParkingFee fee = new ParkingFee();
	    fee.setVehicleType(parkingFeeReqDTO.getVehicleType());
	    fee.setHourlyFee(parkingFeeReqDTO.getHourlyFee());
	    fee.setDailyFee(parkingFeeReqDTO.getDailyFee());
	    fee.setUser(user);

	    parkingFeeRepository.save(fee);

	    return "Fee set successfully for " + parkingFeeReqDTO.getVehicleType();
	}

	//it is used to get all parking fee records.
	@Override
	public List<ParkingFeeRespDTO> parkingFeeList(String userRole) {
		
		 if (!"ADMIN".equalsIgnoreCase(userRole)) {
		        throw new RuntimeException("Access denied. Only ADMIN can view parking fees.");
		    }
		 	
		 List<ParkingFee> fees = parkingFeeRepository.findAll();
		 
		 List<ParkingFeeRespDTO> feeInfoList = fees.stream()
			        .map(f -> new ParkingFeeRespDTO(
			        	f.getId(),
			            f.getVehicleType(),
			            f.getHourlyFee(),
			            f.getDailyFee()
			        ))
			        .collect(Collectors.toList());
		
		return feeInfoList;
	}

	//it is used to fetch particular parking fee record based on id.
	@Override
	public ParkingFeeRespDTO fetchParkingFeeById(int parkingFeeId) {
	    Optional<ParkingFee> optionalFee = parkingFeeRepository.findById(parkingFeeId);

	    if (optionalFee.isPresent()) {
	        ParkingFee fee = optionalFee.get();

	        ParkingFeeRespDTO dto = new ParkingFeeRespDTO();
	        dto.setParkingFeeId(fee.getId());
	        dto.setHourlyFee(fee.getHourlyFee());
	        dto.setDailyFee(fee.getDailyFee());
	        dto.setVehicleType(fee.getVehicleType());

	        return dto;
	    } else {
	       
	        throw new RuntimeException("Parking fee with ID " + parkingFeeId + " not found.");

	    }
	   
	}

	//it is used to update parking fee record.
	@Override
	public String updateParkingFee(ParkingFeeRespDTO parkingFeeRespDTO) {
	    // Fetch existing fee record by ID
	    ParkingFee existingFee = parkingFeeRepository.findById(parkingFeeRespDTO.getParkingFeeId())
	            .orElseThrow(() -> new RuntimeException("Parking fee record not found with ID: " + parkingFeeRespDTO.getParkingFeeId()));

	    // Update the fields
	    existingFee.setVehicleType(parkingFeeRespDTO.getVehicleType());
	    existingFee.setHourlyFee(parkingFeeRespDTO.getHourlyFee());
	    existingFee.setDailyFee(parkingFeeRespDTO.getDailyFee());

	    // Save updated entity
	    parkingFeeRepository.save(existingFee);

	    return "Parking fee updated successfully for " + parkingFeeRespDTO.getVehicleType();
	}

}



