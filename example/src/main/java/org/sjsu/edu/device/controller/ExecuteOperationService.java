package org.sjsu.edu.device.controller;

import javax.annotation.Resource;

import org.codehaus.jettison.json.JSONObject;
import org.sjsu.edu.service.ClientMongoService;
import org.sjsu.edu.service.InsertMongoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deviceManagement")
public class ExecuteOperationService {

	
	private JSONObject jsonobject;

	@RequestMapping(value="/execute/4",method=RequestMethod.POST)
	public String executeDeviceResources(@RequestBody String resourceid1 ) throws Exception {
		jsonobject = new JSONObject(resourceid1);
		String resourceid = jsonobject.getString("resourceId");
		String execute2 = null;
		if(resourceid.equalsIgnoreCase("4")){					// REBOOT	
			
			Thread.sleep(5000);						// load for 5 seconds
			execute2 = "Reboot successful.. \nThe registration still persists with the same object instance id..\n";
		}
		
		return execute2;	
	}
	
}