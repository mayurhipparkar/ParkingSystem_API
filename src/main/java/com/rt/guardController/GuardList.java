package com.rt.guardController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rt.guardDTO.AllGuardRespDTO;
import com.rt.guardServiceInterface.GuardServiceInterface;

@RestController
@RequestMapping("/guard")
public class GuardList {
	
	@Autowired
	private GuardServiceInterface guardServiceInterface;
	
	@GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllGuards(
            @RequestParam String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam String userRole,
            @RequestParam(required = false) String statusFilter
            ) {
		System.out.println("statusFilter and userRole"+userRole +" "+statusFilter);
		 Page<AllGuardRespDTO> guardData = guardServiceInterface.getAllGuardsRecord(role,page,size,statusFilter,userRole);
	        Map<String, Object> response = new HashMap<>();
	        response.put("data", guardData.getContent());
	        response.put("currentPage", guardData.getNumber());
	        response.put("totalItems", guardData.getTotalElements());
	        response.put("totalPages", guardData.getTotalPages());

	        return ResponseEntity.ok(response);
	}
	
	// it is used to search guard record on keyup by using name .
	 @GetMapping("/search")
	    public ResponseEntity<List<AllGuardRespDTO>> searchGuards(@RequestParam String search,
	                                                       @RequestParam String role) {
		 System.out.println("guard search :"+search +" "+role);
	        List<AllGuardRespDTO> result = guardServiceInterface.searchGuardsByName(search, role);
	        for (AllGuardRespDTO allGuardRespDTO : result) {
				System.out.println("searched guard records :"+allGuardRespDTO.getFullname()+" "+allGuardRespDTO.getEmail()+" "+allGuardRespDTO.getStatus()+" "+allGuardRespDTO.getRole());
			}
	        return ResponseEntity.ok(result);
	    }

}
