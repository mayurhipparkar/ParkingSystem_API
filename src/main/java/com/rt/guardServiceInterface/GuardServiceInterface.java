package com.rt.guardServiceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.rt.guardDTO.AddGuardReqDTO;
import com.rt.guardDTO.AllGuardRespDTO;
import com.rt.guardDTO.FetchGuardDetailsReqDTO;
import com.rt.guardDTO.FetchGuardDetailsRespDTO;

public interface GuardServiceInterface {

	public String addGuard(AddGuardReqDTO addGuardReqDTO);

	public Page<AllGuardRespDTO> getAllGuardsRecord(String role, int page, int size,String statusFilter,String userRole);

	public FetchGuardDetailsRespDTO fetchGuardDataById(FetchGuardDetailsReqDTO fetchGuardDetailsReqDTO);

	public List<AllGuardRespDTO> searchGuardsByName(String query, String role);
	
}
