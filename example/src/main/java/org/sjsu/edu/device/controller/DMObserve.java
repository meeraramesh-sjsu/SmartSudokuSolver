package org.sjsu.edu.device.controller;

import org.sjsu.edu.service.ClientMongoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.codehaus.jettison.json.JSONObject;

@RestController
@RequestMapping("/deviceManagement")
public class DMObserve {

	private JSONObject jsonobj;

	@RequestMapping(value="/observe/start",method=RequestMethod.POST)
	public String startObserve(@RequestBody String observeValue) throws Exception {
		jsonobj = new JSONObject(observeValue);
		String objectInstanceId = jsonobj.getString("objectInstanceId");
		String obsStatus = jsonobj.getString("status");
		String emailId = jsonobj.getString("emailId");
		
			ClientMongoService.startOrStopObservation(objectInstanceId,obsStatus,emailId);
			
		return "Observation started.. \n";
	}
	
	@RequestMapping(value="/observe/stop",method=RequestMethod.POST)
	public String stopObserve(@RequestBody String observeValue) throws Exception {
		jsonobj = new JSONObject(observeValue);
		jsonobj = new JSONObject(observeValue);
		String objectInstanceId = jsonobj.getString("objectInstanceId");
		String obsStatus = jsonobj.getString("status");
		String emailId = jsonobj.getString("emailId");
		
		ClientMongoService.startOrStopObservation(objectInstanceId, obsStatus, emailId);
		
		return "Observation cancelled \n";
	}
	
	
}
