package org.sjsu.edu.device.controller;

import org.codehaus.jettison.json.JSONObject;
import org.sjsu.edu.service.ClientMongoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deviceManagement")
public class DeviceManagementDelete {

	private JSONObject jsonobject;

	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteObjectInstance(@RequestBody String deleteinfo) throws Exception {
		jsonobject = new JSONObject(deleteinfo);
		String endpoint = jsonobject.getString("endpoint");
		String objinstance = jsonobject.getString("objectInstanceId");
		System.out.println("Deleting the Object Instance within the Device... \n");
		ClientMongoService.executeFactoryResetDevice(endpoint, objinstance);		
		return "Success";
	}
}
