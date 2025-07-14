package com.rt.guardController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rt.guardDTO.AddGuardReqDTO;
import com.rt.guardServiceInterface.GuardServiceInterface;

@RestController
@RequestMapping("/guard")
public class AddGuard {
	@Autowired
	private GuardServiceInterface guardServiceInterface;
	
	@PostMapping("/add")
	public String addGuard(@RequestBody AddGuardReqDTO addGuardReqDTO) {
		
		return guardServiceInterface.addGuard(addGuardReqDTO);
	}

}
