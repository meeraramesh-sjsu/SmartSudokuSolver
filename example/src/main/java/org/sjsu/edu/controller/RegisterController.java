package org.sjsu.edu.controller;

import org.sjsu.edu.service.RegisterHelperService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

	@RequestMapping(value="/save/{objInstanceId}",method=RequestMethod.POST)
	public String saveRegisterDeviceInfo(@PathVariable String objInstanceId, @RequestBody String reginfo) throws Exception {
		RegisterHelperService.saveRegisterInfoToLWM2MServer(reginfo);						
		// new registration
		RegisterHelperService.saveToRegisteredDevices(objInstanceId, reginfo);		
		return "Successfully Registered";	
	}

	@RequestMapping(value="/update/{objInstanceId}",method=RequestMethod.PUT)
	public String updateRegisterDeviceInfo(@PathVariable String objInstanceId, @RequestBody String updateinfo) throws Exception {
		String output = RegisterHelperService.CheckIfRegistered(objInstanceId);	
		if(output.equalsIgnoreCase("Registered")){
			RegisterHelperService.updateParameters(updateinfo);
			return "Successfully Updated Registeration information";	
		}
		return "You're not registered, can't update";
	}
	
	
	@RequestMapping(value = "/delete/{objInstanceId}/{endPoint}",method={RequestMethod.DELETE})
	public String deleteRegisterDeviceInfo(@PathVariable String objInstanceId, @PathVariable String endPoint) throws Exception {
		String output = RegisterHelperService.CheckIfRegistered(objInstanceId);	
		if(output.equalsIgnoreCase("Registered")){
			RegisterHelperService.DeregisterDevice(objInstanceId , endPoint);
			return "Successfully De-Registered";
		}
		return "You're not registered, can't delete";
	}

}
