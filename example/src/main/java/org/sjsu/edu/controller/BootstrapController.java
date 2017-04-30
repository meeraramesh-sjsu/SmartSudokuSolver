package org.sjsu.edu.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@RestController
@RequestMapping("/bootstrap")
public class BootstrapController {

	static JSONObject jsonObj;
	DBCollection collection;
	DBCursor cursor;
	MongoClient mongoClient;

	private MongoClient getMongoClient(int portNum) {
		return new MongoClient("localhost",portNum);
	}
	
	private String putLWM2MServerInfo(String endPointClientName) throws JSONException {
		DB db = mongoClient.getDB("Server");
		collection = db.getCollection("ObjectInstanceID");
		cursor = collection.find();
		int objId = 0;
		String objectinstanceid = "0";
		if(cursor.hasNext()) {
		String objectInstance = ""; 
		while(cursor.hasNext()) {
		     objectInstance = cursor.next().toString();
		}
		jsonObj = new JSONObject(objectInstance);			// get's the last document(last used id) in the collection
		objectinstanceid = jsonObj.getString("_id");
		}// increment by 1
		int x = Integer.parseInt(objectinstanceid);
		x++; 
		String objectInstanceId = String.valueOf(x); 
		
		//A collection maintains the object instance id's and each client gets a new object instance id					
		BasicDBObject document1 = new BasicDBObject();
		document1.put("_id",objectInstanceId);	
		collection.insert(document1);
		collection = db.getCollection("LWM2MServerObject");
		BasicDBObject documentServer = new BasicDBObject();
		documentServer.append("_id", objectinstanceid);
		documentServer.append("Device Endpoint Name", endPointClientName);
		documentServer.append("Object Id", "1");
		documentServer.append("Short Server Id", "12345");
		documentServer.append("Lifetime", "");
		documentServer.append("Notification Storing", "");
		documentServer.append("Binding", "UDP");
		documentServer.append("Registration Update Trigger", "");
		collection.insert(documentServer);
		cursor = collection.find(documentServer);
		String LWM2MServerInfo = null;
		while (cursor.hasNext()) {
			LWM2MServerInfo = cursor.next().toString();			
		}
		return LWM2MServerInfo;
	}
	
	private String putLWM2MSecurityInfo(String endPointClientName) {
		DB db = mongoClient.getDB("Server");
		DBCollection collection = db.getCollection("LWM2MSecurityObject");
		BasicDBObject documentSecurity = new BasicDBObject();
		documentSecurity.put("Client Name",endPointClientName);
		documentSecurity.append("Object Id", "0");
		documentSecurity.append("LWM2M Server URI", "http://localhost:8080/register/");	
		documentSecurity.append("Bootstrap Server", "False");
		documentSecurity.append("Security Mode","3");			 
		collection.insert(documentSecurity);
		DBCursor cursor = collection.find(documentSecurity);
		String LWM2MSecurityInfo = null;
		while(cursor.hasNext()) {
			LWM2MSecurityInfo  = cursor.next().toString();
		}			
		return LWM2MSecurityInfo;
	}
	
	@RequestMapping("")
	public String bootstrap(@RequestParam(value="clientname") String endPointClientName) throws Exception {
	mongoClient = getMongoClient(27018);
	String lwm2mSecurityServer = putLWM2MSecurityInfo(endPointClientName)+" & \n"+  putLWM2MServerInfo(endPointClientName) + "\n";
	return lwm2mSecurityServer;	
	}
}
