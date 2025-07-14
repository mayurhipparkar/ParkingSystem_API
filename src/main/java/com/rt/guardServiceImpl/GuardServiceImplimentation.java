package com.rt.guardServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rt.guardDTO.AddGuardReqDTO;
import com.rt.guardDTO.AllGuardRespDTO;
import com.rt.guardDTO.FetchGuardDetailsReqDTO;
import com.rt.guardDTO.FetchGuardDetailsRespDTO;
import com.rt.guardMapper.GuardFetchedRecordMapper;
import com.rt.guardMapper.GuardMapper;
import com.rt.guardRepository.GuardRepository;
import com.rt.guardServiceInterface.GuardServiceInterface;
import com.rt.userEntity.Users;
@Service
public class GuardServiceImplimentation implements GuardServiceInterface{
	@Autowired
	private GuardRepository guardRepository;
	
	@Autowired
	private GuardMapper guardMapper;

	@Override
	public String addGuard(AddGuardReqDTO addGuardReqDTO) {
	    
		 // Map DTO to Entity
	    Users users = guardMapper.toEntity(addGuardReqDTO);

	    if (addGuardReqDTO.getId() != 0) {
	        // Update existing
	        Optional<Users> existingUserOpt = guardRepository.findById(addGuardReqDTO.getId());

	        if (existingUserOpt.isEmpty()) {
	            return "Guard not found for update.";
	        }

	        Users existingUser = existingUserOpt.get();

	        // Check for duplicate email (excluding current user)
	        if (!existingUser.getEmail().equalsIgnoreCase(addGuardReqDTO.getEmail()) &&
	            guardRepository.existsByEmailIgnoreCase(addGuardReqDTO.getEmail())) {
	            return "Email already exists...!";
	        }

	        // Check for duplicate number (excluding current user)
	        if (!existingUser.getNumber().equals(addGuardReqDTO.getNumber()) &&
	            guardRepository.existsByNumber(addGuardReqDTO.getNumber())) {
	            return "Contact number already exists...!";
	        }

	        // Update fields
	        existingUser.setFullname(addGuardReqDTO.getFullname());
	        existingUser.setEmail(addGuardReqDTO.getEmail());
	        existingUser.setNumber(addGuardReqDTO.getNumber());
	        existingUser.setAddress(addGuardReqDTO.getAddress());
	        existingUser.setPassword(addGuardReqDTO.getPassword());
	        existingUser.setStatus(addGuardReqDTO.getStatus());
	        existingUser.setRole("Guard");

	        guardRepository.save(existingUser);
	        return "Guard updated successfully!";
	    }

	    //Create new user

	    // Set default role and status if not present
	    if (users.getRole() == null || users.getRole().trim().isEmpty()) {
	        users.setRole("Guard");
	    }

	    if (users.getStatus() == null || users.getStatus().trim().isEmpty()) {
	        users.setStatus("Active");
	    }

	    if (!"Guard".equalsIgnoreCase(users.getRole())) {
	        return "Only 'Guard' role is allowed!";
	    }

	    // Validate email and number
	    if (guardRepository.existsByEmailIgnoreCase(users.getEmail())) {
	        return "Email already exists...!";
	    }

	    if (guardRepository.existsByNumber(users.getNumber())) {
	        return "Contact number already exists...!";
	    }

	    guardRepository.save(users);
	    return "Guard added successfully!";
	}
	
//this is all record of guard which is only visible for admin.
	@Override
	public Page<AllGuardRespDTO> getAllGuardsRecord(String role, int page, int size, String statusFilter, String userRole) {
	    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

	    // Only Admin can view guards
	    if (!"Admin".equalsIgnoreCase(userRole)) {
	        return Page.empty();
	    }

	    Page<Users> pagedResult;

	    // Apply status filter if provided
	    if (statusFilter != null && !statusFilter.isEmpty()) {
	        pagedResult = guardRepository.findByRoleAndStatus(role, statusFilter, pageable);
	    } else {
	        pagedResult = guardRepository.findByRole(role, pageable);
	    }

	    // Convert Entity to DTO
	    return pagedResult.map(user -> new AllGuardRespDTO(
	        user.getId(),
	        user.getFullname(),
	        user.getEmail(),
	        user.getNumber(),
	        user.getAddress(),
	        user.getRole(),
	        user.getStatus()
	    ));
	}

	//this is used to fetch specific guard record based on its id to update by admin.
	@Autowired
	private GuardFetchedRecordMapper guardFetchedRecordMapper;
	@Override
	public FetchGuardDetailsRespDTO fetchGuardDataById(FetchGuardDetailsReqDTO fetchGuardDetailsReqDTO) {
		
		Optional<Users> user=guardRepository.findByIdAndRole(fetchGuardDetailsReqDTO.getId(),fetchGuardDetailsReqDTO.getRole());
		if(user.isPresent()) {
			Users userdData=user.get();
		FetchGuardDetailsRespDTO fetchGuardDetailsRespDTO=guardFetchedRecordMapper.toDTO(userdData);
		return fetchGuardDetailsRespDTO;
		}
		return null;
	}

	@Override
	public List<AllGuardRespDTO> searchGuardsByName(String name, String role) {
		List<Users> guards = guardRepository.searchGuardsByNameAndRole(name, role);
		
        return guards.stream()
                     .map(g -> new AllGuardRespDTO(g.getId(), g.getFullname(), g.getEmail(), g.getNumber(), g.getAddress(),g.getRole(),g.getStatus()))
                     .collect(Collectors.toList());
	}


}
