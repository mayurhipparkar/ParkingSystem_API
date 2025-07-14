package com.rt.guardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchGuardDetailsReqDTO {
	
	private int id;
	private String role;
	private int sessionUserId;
	

}
