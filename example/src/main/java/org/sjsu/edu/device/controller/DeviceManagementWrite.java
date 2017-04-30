package org.sjsu.edu.device.controller;

import org.codehaus.jettison.json.JSONObject;
import org.sjsu.edu.service.ClientMongoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deviceManagement")
public class DeviceManagementWrite {

	private JSONObject jsonobj;

	@RequestMapping(value="/write/3/1",method=RequestMethod.POST)
	public String writeLifetime(@RequestBody String lifetime) throws Exception {
		jsonobj = new JSONObject(lifetime);
		ClientMongoService.writeLifetime(jsonobj.getString("objectInstanceId"),jsonobj.getString("lifetime"));		
		return "Success";	
	}
	
	@RequestMapping(value="/write/3/6",method=RequestMethod.POST)
	public String writeNotificationStoring(@RequestBody String notification) throws Exception {
		jsonobj = new JSONObject(notification);
		ClientMongoService.writeNotificationStoring(jsonobj.getString("objectInstanceId"),jsonobj.getString("notification"));		
		return "Success";	
	}
	
	@RequestMapping(value="/write/3/7",method=RequestMethod.POST)
	public String writeBinding(@RequestBody String binding) throws Exception {
		jsonobj = new JSONObject(binding);
		ClientMongoService.writeBinding(jsonobj.getString("objectInstanceId"),jsonobj.getString("bindingMode"));		
		return "Success";	
	}
	
}