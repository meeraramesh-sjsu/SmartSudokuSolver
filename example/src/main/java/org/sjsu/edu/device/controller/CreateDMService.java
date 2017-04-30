package org.sjsu.edu.device.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.sjsu.edu.service.ClientMongoService;
import org.sjsu.edu.service.RegisterHelperService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@RestController
@RequestMapping("/deviceManagement")
public class CreateDMService {

	MongoClient mongoClient = new MongoClient("localhost",27018);
	DB db = mongoClient.getDB( "Serverdb" );
	DBCollection collection;
	private JSONObject jsonObj;
	
	@RequestMapping(value="/create/{objInstanceId}",method=RequestMethod.POST)
	public String createServerObject(@RequestBody String serverObjInfo, @PathVariable String objInstanceId) throws Exception {
		
		ClientMongoService.createNewServerObject(serverObjInfo);		// newvalue already contains the objInstance
		
		String newinfo = ClientMongoService.setNewValuesNewObjectInstance(objInstanceId);		// return new values
		
		return newinfo;
		
	}
	
}