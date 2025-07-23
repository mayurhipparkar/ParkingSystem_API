package com.rt.signUpAndLoginserviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rt.signUpAndLoginDTO.RequestSignUpDTO;
import com.rt.signUpAndLoginMapper.SignUpAndUserMapper;
import com.rt.signUpAndLoginRepository.UsersRepository;
import com.rt.signUpAndLoginserviceInterface.SignUpInterface;
import com.rt.userEntity.Users;

@Service
public class SignUpImplementation implements SignUpInterface{
	@Autowired
	private UsersRepository userRepo;//it extended the JPA repository 
	
	@Autowired
	private SignUpAndUserMapper mapper;//Mapper class to convert DTO to Entity and vis versa.
 
	//this is helper method which is used to convert lowercase role into first latter uppercase.
	private String capitalizeFirstLetterOrDefaultGuard(String input) {
	    if (input == null || input.trim().isEmpty()) {
	        return "Guard"; 
	    }
	    return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

	@Override
	public String addUser(RequestSignUpDTO signUpDto) { 
		String inputRole = signUpDto.getRole();

	    //Check if role is Admin or Guard only
	    if (!"Admin".equalsIgnoreCase(inputRole) && !"Guard".equalsIgnoreCase(inputRole)) {
	        return "Only roles 'Admin' and 'Guard' are allowed.";
	    }

	    //first letter uppercase, rest lowercase
	    String convertedRole = capitalizeFirstLetterOrDefaultGuard(inputRole);
	    signUpDto.setRole(convertedRole);

	    //Convert DTO to Entity
	    Users users = mapper.toEntity(signUpDto);
	  
	    // default status
	    if(users.getStatus()==null) {
	    	 users.setStatus("Active");
	    }

	    //Handle Admin registration
	    if ("Admin".equalsIgnoreCase(convertedRole)) {
	        boolean adminExists = userRepo.existsByRoleIgnoreCase("Admin");

	        if (adminExists) {
	            Users existing = userRepo.findByEmailAndRole(signUpDto.getEmail(), "Admin");
	            if (existing != null) {
	                return "Admin already exists with this email.";
	            }
	            return "Only one Admin allowed.";
	        }

	        userRepo.save(users);
	        return "Admin registered successfully, now able to login.";
	    }

	    //Handle Guard registration
	    else {
	        boolean emailExists = userRepo.existsByEmailIgnoreCase(signUpDto.getEmail());

	        if (emailExists) {
	            return "Email already exists.";
	        }

	        userRepo.save(users);
	        return "Guard registered successfully, now able to login.";
	    }
	}

}
