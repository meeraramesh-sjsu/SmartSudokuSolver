package org.sjsu.edu.device.controller;

import org.sjsu.edu.service.ClientMongoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.codehaus.jettison.json.JSONObject;

@RestController
@RequestMapping("/deviceManagement")
public class DeviceManagementWriteAttribute {

	private JSONObject jsonobj;

	@RequestMapping(value="/writeAttributes/0",method=RequestMethod.POST)
	public String writeAttributeObject0(@RequestBody String writeAttribute) throws Exception {
		jsonobj = new JSONObject(writeAttribute);
		String objectInstanceId = jsonobj.getString("objectInstanceId");
		//maxPeriod indicates the time the maximum time in seconds 
		//the LWM2M Client SHOULD wait from the time when sending the last notification to the time sending the next notification
		//and it is greater than minimum period
		String maxPeriod = jsonobj.getString("maxPeriod");
		String minPeriod = jsonobj.getString("minPeriod");
		//when the number of tokens used by the client becomes less than the number specified, the client should send a notification
		String lessThan = jsonobj.getString("lessThan");
		//when the cancelObs attribute is set, the observation will be cancelled
		String cancelObs = jsonobj.getString("cancelObs");
		String emailId = jsonobj.getString("emailId");
		//The attributes are written to a ObservationStats collection in the mongoDB
		ClientMongoService.writeAttribute(objectInstanceId, minPeriod, maxPeriod, lessThan);
		if(cancelObs.contains("yes"))
			ClientMongoService.startOrStopObservation(objectInstanceId,"stop",emailId);
			
		return "The Write Attributre Operation is successfully done.. \n";
	}
	
	
}
