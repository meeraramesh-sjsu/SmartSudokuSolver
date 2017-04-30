package org.sjsu.edu.device.controller;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@RestController
@RequestMapping("/deviceManagement")
public class DeviceManagementRead {

	MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
	DB db = mongoClient.getDB( "rasberryPiDB" );
	DBCollection collection1;
	BasicDBObject whereQuery;
	DBCursor cursor;
	private JSONObject jsonObj;
	private String read; 
	
	@RequestMapping(value="/read/1/shortServerId",method=RequestMethod.POST)
	public String getShortServerID(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", jsonObj.getString("objectInstanceId"));							// check the specific id
		cursor = collection1.find(whereQuery);		
		String r1 = null;
		while (cursor.hasNext()) {
			r1  = cursor.next().toString();			
		}
		if(r1!=null) {jsonObj = new JSONObject(r1);
		read = jsonObj.getString("Short Server Id");}
		return read;		
	}
	
	@RequestMapping(value="/read/1/lifetime",method=RequestMethod.POST)
	public String getLifetime(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", jsonObj.getString("objectInstanceId"));							// check the specific id
		cursor = collection1.find(whereQuery);		
		String r1 = null;
		while (cursor.hasNext()) {
			r1  = cursor.next().toString();			
		}
		if(r1!=null) {jsonObj = new JSONObject(r1);
		read = jsonObj.getString("Lifetime");}
		return read;		
	}
	
	@RequestMapping(value="/read/1/notification",method=RequestMethod.POST)
	public String getNotificationStoring(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", jsonObj.getString("objectInstanceId"));							// check the specific id
		cursor = collection1.find(whereQuery);		
		String r1 = null;
		while (cursor.hasNext()) {
			r1  = cursor.next().toString();			
		}
		if(r1!=null) {jsonObj = new JSONObject(r1);
		read = jsonObj.getString("Notification Storing");}
		return read;			
	}
	
	@RequestMapping(value="/read/1/binding",method=RequestMethod.POST)
	public String getBinding(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", jsonObj.getString("objectInstanceId"));							// check the specific id
		cursor = collection1.find(whereQuery);		
		String r1 = null;
		while (cursor.hasNext()) {
			r1  = cursor.next().toString();			
		}
		if(r1!=null) {jsonObj = new JSONObject(r1);
		read = jsonObj.getString("Binding");}
		return read;		
	}
	
	@RequestMapping(value="/read/1/manufacturer",method=RequestMethod.POST)
	public String getManufacturer(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		collection1 = db.getCollection("DeviceObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", jsonObj.getString("objectInstanceId"));							// check the specific id
		cursor = collection1.find(whereQuery);		
		String r1 = null;
		while (cursor.hasNext()) {
			r1  = cursor.next().toString();			
		}
		if(r1!=null) {jsonObj = new JSONObject(r1);
		read = jsonObj.getString("Manufacturer");}
		return read;			
	}
	
	@RequestMapping(value="/read/1/errorCode",method=RequestMethod.POST)
	public String getErrorCode(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		collection1 = db.getCollection("DeviceObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", jsonObj.getString("objectInstanceId"));							// check the specific id
		cursor = collection1.find(whereQuery);		
		String r1 = null;
		while (cursor.hasNext()) {
			r1  = cursor.next().toString();			
		}
		if(r1!=null) {jsonObj = new JSONObject(r1);
		read = jsonObj.getString("Error Code");}
		return read;		
	}
	
	@RequestMapping(value="/read/1/deviceType",method=RequestMethod.POST)
	public String getDeviceType(@RequestBody String objinstance) throws Exception {
		jsonObj = new JSONObject(objinstance);
		collection1 = db.getCollection("DeviceObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", jsonObj.getString("objectInstanceId"));							// check the specific id
		cursor = collection1.find(whereQuery);		
		String r1 = null;
		while (cursor.hasNext()) {
			r1  = cursor.next().toString();			
		}
		if(r1!=null) {jsonObj = new JSONObject(r1);
		read = jsonObj.getString("Device Type");}
		return read;		
	}
}
