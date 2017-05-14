package org.sjsu.edu.service;
import java.net.UnknownHostException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mongodb.*;
											// HERE objid is objectinstanceid
public class RegisterHelperService {
	static JSONObject jsonObj;
	//Registration
	public static void saveRegisterInfoToLWM2MServer(String reginfo) throws UnknownHostException, JSONException{
		System.out.println("In update server object " + reginfo);
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		jsonObj = new JSONObject(reginfo);
		
		String a1 = jsonObj.getString("_id");					// a1 - get _id
		String a2 = jsonObj.getString("Lifetime");					
		String a3 = jsonObj.getString("Notification Storing");
		String a4 = jsonObj.getString("Binding");
		String a5 = jsonObj.getString("Registration Update Trigger");		
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", a1);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("_id", a1).append("Lifetime", a2)
									.append("Notification Storing", a3).append("Binding", a4)
									.append("Registration Update Trigger", a5);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	public static void updateParameters(String updateinfo) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		jsonObj = new JSONObject(updateinfo);
		String a1 = jsonObj.getString("_id");					// a1 - get _id
		String a2 = jsonObj.getString("Lifetime");					
		String a3 = jsonObj.getString("Binding");

		BasicDBObject whereQuery = new BasicDBObject().append("_id", a1);							// check the specific id		
		BasicDBObject newDocument = new BasicDBObject().append("Lifetime", a2).append("Binding", a3);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void saveToRegisteredDevices(String reginfo) throws UnknownHostException, JSONException{
		System.out.println("New device from server" + reginfo);
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		System.out.println("RegInfo  " + reginfo);
		BasicDBObject document = new BasicDBObject();

		jsonObj = new JSONObject(reginfo);
		String a1 = jsonObj.getString("_id");		
		
		document.append("Device Endpoint Name", new JSONObject(reginfo).getString("endpoint"));// get server object id
		document.append("_id", a1);
		document.append("Status", "Registered");
		document.append("Device Type", jsonObj.getString("type"));
		collection2.insert(document);
	}

	
	public static String CheckIfRegistered(String objectInstanceId) throws UnknownHostException, JSONException{
		String result4;
		 String m = null;

		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectInstanceId);							// check the specific id
		DBCursor cursor = collection2.find(whereQuery);		
		while (cursor.hasNext()) {
			m = cursor.next().toString();			
		}
		if(m != null){
		jsonObj = new JSONObject(m);
		result4 = jsonObj.getString("Status");		
		}
		else{
		result4 = "false";
		}
		return result4;
	}
	
	public static void DeregisterDevice(String objid, String endpoint) throws UnknownHostException {
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		
		DBCollection collection = db.getCollection("LWM2MSecurityObject");			// 1st collection		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);
		collection.remove(whereQuery);
		
		DBCollection collection1 = db.getCollection("LWM2MServerObject");			// 2nd collection
		BasicDBObject whereQuery1 = new BasicDBObject().append("_id", objid);
		collection1.remove(whereQuery1);
		
		DBCollection collection2 = db.getCollection("RegisteredDevices");			// 3rd collection
		BasicDBObject whereQuery2 = new BasicDBObject().append("_id", objid);
		collection2.remove(whereQuery2);
	}

	public static String getClientEndpointName(String objectInstanceId) throws UnknownHostException, JSONException{
		System.out.println(objectInstanceId);
		MongoClient mongoClient = new MongoClient( "localhost" , 27018);
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectInstanceId);							// check the specific id
		DBCursor cursor = collection2.find(whereQuery);		
		String o = null;
		while (cursor.hasNext()) {
			o = cursor.next().toString();			
		}
		jsonObj = new JSONObject(o);
		String result5 = jsonObj.getString("Device Endpoint Name");
		return result5;
	}
	
	public static String getDeviceURI(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection1 = db.getCollection("DeviceContact");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		String z = null;
		while (cursor.hasNext()) {
			z = cursor.next().toString();			
		}
		jsonObj = new JSONObject(z);
		String result2 = jsonObj.getString("Contact");
		return result2;
		}
	
	public static String getobjectinstanceid() throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("ObjectInstanceID");
		String temp = null;
		DBCursor cursor = collection2.find();
		while(cursor.hasNext()) {
		    temp = cursor.next().toString();
		}
		jsonObj = new JSONObject(temp);							// get's the last document(last used id) in the collection
		String objectinstanceid = jsonObj.getString("_id");

		int x = Integer.parseInt(objectinstanceid);
		x++;													// increment by 1
		String y = String.valueOf(x);							// convert to string
		BasicDBObject document1 = new BasicDBObject();
		document1.put("_id",y);								// insert the incremented value to db, so next time +1 from that..
		collection2.insert(document1);
		
		return objectinstanceid;
	}
	
public static void RegistrationUpdate(String endpoint , String objid) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "Serverdb" );
		DBCollection collection2 = db.getCollection("RegisteredDevices");
		
		BasicDBObject whereQuery = new BasicDBObject().append("Device Endpoint Name", endpoint);
		
		BasicDBObject document = new BasicDBObject();
		document.append("_id", objid);					// update corresponding object instance id
		DBObject update = new BasicDBObject("$set", document);
		collection2.updateMulti(whereQuery, update);		
	}

public static String createNewServerObject(String endpoint, String objid) throws UnknownHostException{
	
	MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
	DB db = mongoClient.getDB( "Serverdb" );
	DBCollection collection1 = db.getCollection("LWM2MServerObject");
	
	BasicDBObject document = new BasicDBObject();
	document.append("_id", objid);
	document.append("Device Endpoint Name", endpoint);
	document.append("Object Id", "1");
	document.append("Short Server Id", "12345");
	document.append("Lifetime", "");
	document.append("Disable", "");
	document.append("Disable Timeout", "");
	document.append("Notification Storing", "");
	document.append("Binding", "");
	document.append("Registration Update Trigger", "");
	
	collection1.insert(document);
	DBCursor cursor = collection1.find(document);
	String q = null;
	while (cursor.hasNext()) {
		q = cursor.next().toString();			
	}
	return q;
}
		
}