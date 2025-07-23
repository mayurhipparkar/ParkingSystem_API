package com.rt.signUpAndLoginserviceImpl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.allEnums.ParkingStatus;
import com.rt.parkingDTO.ParkingFeeRespDTO;
import com.rt.parkingRepository.ParkingFeeRepository;
import com.rt.parkingRepository.ParkingManagementRepository;
import com.rt.signUpAndLoginDTO.ParkingDashBoardInfoDTO;
import com.rt.signUpAndLoginDTO.RequestLoginDTO;
import com.rt.signUpAndLoginDTO.ResponseLoginDTO;
import com.rt.signUpAndLoginRepository.UsersRepository;
import com.rt.signUpAndLoginserviceInterface.LoginServiceInterface;
import com.rt.userEntity.Users;
import com.rt.vehicleAndParkingEntity.ParkingFee;
import com.rt.vehicleEntryRepository.VehicleRepository;

@Service
public class LoginServiceImplementation implements LoginServiceInterface{

	@Autowired
	private UsersRepository usersRepository;//it extended the JPA repository 
	
	@Autowired
	private VehicleRepository vehicleRepository;//vehicle repo
	
	@Autowired
	private ParkingManagementRepository parkingManagementRepository;//parking repo for parking detail.
	
	@Autowired
	private ParkingFeeRepository parkingFeeRepository;//fee details
	
	@Override
	public ResponseLoginDTO checkLogin(RequestLoginDTO reqLoginDto) {
		Optional<ResponseLoginDTO> optional = usersRepository.loginUser(reqLoginDto.getEmail(),reqLoginDto.getPassword());
		if (optional.isPresent()) {
			ResponseLoginDTO respLoginDto = optional.get();	
			return respLoginDto;
		}
		return null;	
	}

	//it is used to fetch record for dashboard.
	@Override
	public ParkingDashBoardInfoDTO allVehicleAndParkingRecord() {
		
		 LocalDate today = LocalDate.now();
		
		ParkingDashBoardInfoDTO dashboardInfoDTO = new ParkingDashBoardInfoDTO();
		//total vehicle count
		long totalVehicleCount = vehicleRepository.count();
		dashboardInfoDTO.setTotalVehicleCount(totalVehicleCount);
		
		//total todays vehicle count
		long todayEnteredVehicleCount = vehicleRepository.countByEntryDate(today);
		dashboardInfoDTO.setTodayEnteredVehicleCount(todayEnteredVehicleCount);
		
		//total parked vehicle count
		long totalParkedVehicleCount = parkingManagementRepository.countByStatus(ParkingStatus. EXITED);
	    dashboardInfoDTO.setTotalParkedVehicleCount(totalParkedVehicleCount);
	    
	  //total todays vehicle count
	    long todayParkedCount = parkingManagementRepository.countByInDateAndStatus(today,ParkingStatus. ACTIVE);
	    dashboardInfoDTO.setTodayParkedVehicleCount(todayParkedCount);
	  
	    // Set fee details using DTO for "Two Wheeler"
	    Optional<ParkingFee> twoWheelerFeeOpt = parkingFeeRepository.findByVehicleTypeIgnoreCase("Two Wheeler");
	    if (twoWheelerFeeOpt.isPresent()) {
	        ParkingFee fee = twoWheelerFeeOpt.get();
	        ParkingFeeRespDTO feeRespDTO = new ParkingFeeRespDTO(
	            fee.getVehicleType(),
	            fee.getHourlyFee(),
	            fee.getDailyFee()
	        );
	        dashboardInfoDTO.setTwoWheelerFee(feeRespDTO);
	    }

	    // Set fee details using DTO for "Four Wheeler"
	    Optional<ParkingFee> fourWheelerFeeOpt = parkingFeeRepository.findByVehicleTypeIgnoreCase("Four Wheeler");
	    if (fourWheelerFeeOpt.isPresent()) {
	        ParkingFee fee = fourWheelerFeeOpt.get();
	        ParkingFeeRespDTO feeRespDTO = new ParkingFeeRespDTO(
	            fee.getVehicleType(),
	            fee.getHourlyFee(),
	            fee.getDailyFee()
	        );
	        dashboardInfoDTO.setFourWheelerFee(feeRespDTO);
	    }
		return dashboardInfoDTO;
		
		
	}

	//it is used to reset password only for admin.
	@Override
	public String resetPassword(String email, String newPassword) {
	    Optional<Users> optionalUser = usersRepository.findByEmail(email);

	    if (optionalUser.isPresent()) {
	        Users user = optionalUser.get();

	        // Assuming role is String like "ADMIN", otherwise use user.getRole().equals(Role.ADMIN)
	        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
	            user.setPassword(newPassword); 
	            usersRepository.save(user);    // Save in db.
	            return "Password successfully reset for admin.";
	        } else {
	            return "Only Admin is allowed to reset password.";
	        }

	    } else {
	        return "User not found with the provided email.";
	    }
	}

	
}
