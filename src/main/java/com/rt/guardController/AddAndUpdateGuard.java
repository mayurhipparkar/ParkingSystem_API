package com.rt.guardController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rt.guardDTO.AddGuardReqDTO;
import com.rt.guardDTO.FetchGuardDetailsReqDTO;
import com.rt.guardDTO.FetchGuardDetailsRespDTO;
import com.rt.guardServiceInterface.GuardServiceInterface;

@RestController
@RequestMapping("/guard")
public class AddAndUpdateGuard {
	@Autowired
	private GuardServiceInterface guardServiceInterface;
	
	@PostMapping("/add")
	public String addGuard(@RequestBody AddGuardReqDTO addGuardReqDTO) {
		
		return guardServiceInterface.addGuard(addGuardReqDTO);
	}
	
	
	@PostMapping("/fetch")
	public FetchGuardDetailsRespDTO guardUpdateRecordBasedOnId(@RequestBody FetchGuardDetailsReqDTO fetchGuardDetailsReqDTO) {
		
		return guardServiceInterface.fetchGuardDataById(fetchGuardDetailsReqDTO);
	}

}
