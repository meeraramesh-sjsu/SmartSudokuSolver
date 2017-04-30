package org.sjsu.edu.device.controller;

import org.codehaus.jettison.json.JSONObject;
import org.sjsu.edu.service.ClientMongoService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deviceManagement")
public class DiscoverOperationService {

	private JSONObject jsonObj;
	private String endpoint;
	private String objectid;
	private String objinstance;
	private String resourceid;

	@RequestMapping(value="/discover/object",method=RequestMethod.POST)
	public String getObject(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		endpoint = jsonObj.getString("endpoint");
		objinstance = jsonObj.getString("objectInstanceId");
		objectid = jsonObj.getString("objectId");
		
		String discover1;
		if(objectid.equalsIgnoreCase("0")){									// LWM2MSecurityObject
			discover1 = "The requested Object has following attributes: \n"+ClientMongoService.discoverSecurityObject(endpoint);		
		}
		else if(objectid.equalsIgnoreCase("1")){								// LWM2MServerObject
			discover1 = "The requested Object has following attributes: \n"+ClientMongoService.discoverServerObject(objinstance);
		}
		else if(objectid.equalsIgnoreCase("3")){								// DeviceObject	
			discover1 = "The requested Object has following attributes: \n"+ClientMongoService.discoverDeviceObject(objinstance);
		}
		else{
			discover1 = "There's no object with this object id..\n";
		}		
		return discover1;	
	}
	
	@RequestMapping(value="/discover/resource",method=RequestMethod.POST)
	public String getResourceAttribute(@RequestBody String objectResource) throws Exception {
		
		jsonObj = new JSONObject(objectResource);
		endpoint = jsonObj.getString("endpoint");
		objinstance = jsonObj.getString("objectInstanceId");
		objectid = jsonObj.getString("objectId");
		resourceid = jsonObj.getString("resourceId");
		
		String discover2;
		if(objectid.equalsIgnoreCase("0")){									// LWM2MSecurityObject
			
			if(resourceid.equalsIgnoreCase("0")){
				discover2 = "The requested Resource attribute is: "+ClientMongoService.discoverLWM2MServerURI(endpoint);
			}
			else if(resourceid.equalsIgnoreCase("2")){
				discover2 = "The requested Resource attribute is: "+ClientMongoService.discoverSecurityMode(endpoint);
			}
			else{
				discover2 = "There's no Resource to discover with this object id(0).. \n";
			}
		}
		else if(objectid.equalsIgnoreCase("1")){								// LWM2MServerObject
			
			if(resourceid.equalsIgnoreCase("0")){
				discover2 = ClientMongoService.discoverShortServerId(objinstance);
			}
			else if(resourceid.equalsIgnoreCase("7")){
				discover2 = ClientMongoService.discoverBindingMode(objinstance);
			}
			else{
				discover2 = "There's no Resource to discover with this object id(1).. \n";
			}
		}
		else if(objectid.equalsIgnoreCase("3")){								// DeviceObject
			
			if(resourceid.equalsIgnoreCase("0")){
				discover2 = ClientMongoService.discoverManufacturer(objinstance);
			}
			else if(resourceid.equalsIgnoreCase("11")){
				discover2 = ClientMongoService.discoverErrorCode(objinstance);
			}
			else{
				discover2 = "There's no Resource to discover with this object id(3).. \n";
			}
		}
		
		else{
			discover2 = "There's no object with this object id..\n";
		}
		
		return discover2;	
	}
}
