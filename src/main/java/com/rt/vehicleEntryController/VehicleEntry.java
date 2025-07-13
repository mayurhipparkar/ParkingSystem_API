package com.rt.vehicleEntryController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rt.vehicleEntryDTO.AddVehicleRequestDto;
import com.rt.vehicleEntryDTO.AddVehicleRespDto;
import com.rt.vehicleEntryDTO.FetchVehicleInfoRespDto;
import com.rt.vehicleEntryServiceInterface.FetchVehicleInfoInterface;
import com.rt.vehicleEntryServiceInterface.VehicleEntryInterface;

@RestController
@RequestMapping("/entry")
public class VehicleEntry {
	
	@Autowired
	private VehicleEntryInterface vehicleEntryInterface ;
	
	@Autowired
	private FetchVehicleInfoInterface fetchVehicleInfoInterface;
	
	@PostMapping("/addVehicle")
	public AddVehicleRespDto addVehicleInfo(@RequestBody AddVehicleRequestDto addVehicleReqDto) {
		System.out.println(addVehicleReqDto.getUserId()+" "+addVehicleReqDto.getUserRole()+" "+addVehicleReqDto.getVehicleNumber()+" "+addVehicleReqDto.getEntryDate()+" "+addVehicleReqDto.getEntryTime());
		AddVehicleRespDto respDto=vehicleEntryInterface.addVehicleInfo(addVehicleReqDto);
		return respDto;
		
	}
	
	@GetMapping("/fetchVehicleInfo")
	public FetchVehicleInfoRespDto getInfoById(@RequestParam int id) {
		return fetchVehicleInfoInterface.fetchVehicleInfoById(id);
	}
	

}
