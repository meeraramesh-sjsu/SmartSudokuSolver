package org.sjsu.edu.controller;

import org.codehaus.jettison.json.JSONObject;
import org.sjsu.edu.service.RegisterHelperService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveRegisterDeviceInfo(@RequestBody String reginfo) throws Exception {
		RegisterHelperService.saveRegisterInfoToLWM2MServer(reginfo);						
		// new registration
		RegisterHelperService.saveToRegisteredDevices(reginfo);		
		return "Successfully Registered";	
	}

	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public String updateRegisterDeviceInfo(@RequestBody String updateinfo) throws Exception {
		String output = RegisterHelperService.CheckIfRegistered(new JSONObject(updateinfo).getString("_id"));	
		if(output.equalsIgnoreCase("Registered")){
			RegisterHelperService.updateParameters(updateinfo);
			return "Successfully Updated Registeration information";	
		}
		return "You're not registered, can't update";
	}
	
	@RequestMapping(value = "/delete/{objId}/{endpoint}",method=RequestMethod.DELETE)
	public String deleteRegisterDeviceInfo(@PathVariable String objId, @PathVariable String endpoint) throws Exception {
		String output = RegisterHelperService.CheckIfRegistered(objId);	
		if(output.equalsIgnoreCase("Registered")){
			RegisterHelperService.DeregisterDevice(objId , endpoint);
			return "Successfully De-Registered";
		}
		return "You're not registered, can't delete";
	}

}
