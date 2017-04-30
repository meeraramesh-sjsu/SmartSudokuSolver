package org.sjsu.edu.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class InsertMongoService {
	private MongoClient mongoClient;
	private static DB db;
	private static DBCollection collection;
	
	public void InsertMongoService() {
		// TODO Auto-generated constructor stub
		mongoClient = new MongoClient("localhost",27018);
		db = mongoClient.getDB("Server");
	}
	
	public String createNewServerObject(String endpoint, String objectInstanceId, DBCursor cursor) {
		collection = db.getCollection("LWM2MServerObject");
		BasicDBObject documentServer = new BasicDBObject();
		documentServer.append("_id", objectInstanceId);
		documentServer.append("Device Endpoint Name", endpoint);
		documentServer.append("Object Id", "1");
		documentServer.append("Short Server Id", "12345");
		documentServer.append("Lifetime", "");
		documentServer.append("Notification Storing", "");
		documentServer.append("Binding", "");
		documentServer.append("Registration Update Trigger", "");
		collection.insert(documentServer);
		cursor = collection.find(documentServer);
		String LWM2MServerInfo = null;
		while (cursor.hasNext()) {
			LWM2MServerInfo = cursor.next().toString();			
		}
		return LWM2MServerInfo;
	}
	
	
}
