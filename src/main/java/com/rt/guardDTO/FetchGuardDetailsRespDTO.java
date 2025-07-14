package com.rt.guardDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchGuardDetailsRespDTO {
	
	private int id;
	private String fullname;
	private String email;
	private String number;
	private String password;
	private String address;
	private String role;
	private String status;

}
