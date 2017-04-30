package org.sjsu.edu.service;

import java.net.UnknownHostException;
import java.security.acl.NotOwnerException;
import java.util.Date;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.sjsu.edu.device.controller.DeviceManagementNotify;
import org.sjsu.edu.device.controller.NotifyJob;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class ClientMongoService {

	private volatile static boolean exit = false;
	
	public static void createNewServerObject(String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
			
		DBObject dbObject1 = (DBObject)JSON.parse(newvalue);			// newvalue has _id field 
		collection1.insert(dbObject1);
	}
	
	static String create1;											// set new values to new object instance
	public static String setNewValuesNewObjectInstance(String objid) throws UnknownHostException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");

		BasicDBObject whereQuery = new BasicDBObject().append("_id", objid);

		BasicDBObject newDocument = new BasicDBObject().append("_id", objid).append("Lifetime", "86400").append("Disable", "").append("Disable Timeout","86400").append("Notification Storing", "True").append("Binding", "U").append("Registration Update Trigger", "");
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);

		DBCursor cursor = collection1.find(newDocument);
		while (cursor.hasNext()) {
			create1 = cursor.next().toString();			
		}
		return create1;
	}

	static String discover1;
	public static String discoverSecurityObject(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			discover1 = cursor.next().toString();			
		}
		return discover1;
	}
	
	static String discover2;
	public static String discoverServerObject(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			discover2 = cursor.next().toString();			
		}
		return discover2;
	}
	
	static String discover3;
	public static String discoverDeviceObject(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("DeviceObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			discover3 = cursor.next().toString();			
		}
		return discover3;
	}
	
	static String d1;
	private static JSONObject jsonObj;
	private static String discover;
	public static String discoverLWM2MServerURI(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d1 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d1);
		discover = jsonObj.getString("LWM2M Server URI");
		discover = "{'LWM2M Server URI' : '"+discover +"'}";
		return discover;
	}
	
	static String discover5;
	static String d2;
	public static String discoverSecurityMode(String endpoint) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", endpoint);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d2 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d2);
		discover5 = jsonObj.getString("Security Mode");
		discover5 = "{'Security Mode' : '"+discover5+"'}";
		return discover5;
	}
	
	static String discover6;
	static String d3;
	public static String discoverShortServerId(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d3 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d3);
		discover6 = jsonObj.getString("Short Server Id");
		discover6 = "{'Short Server Id' : '"+discover6+"'}";
		return discover6;
	}
	
	static String discover7;
	static String d4;
	public static String discoverBindingMode(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d4 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d4);
		discover7 = jsonObj.getString("Binding");
		discover7 = "{'Binding' : '"+discover7+"'}";
		return discover7;
	}
	
	static String discover8;
	static String d5;
	public static String discoverManufacturer(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("DeviceObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d5 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d5);
		d5 = jsonObj.getString("Manufacturer");
		discover8 = "{'Manufacturer' : '"+d5+"'}";
		return discover8;
	}

	static String discover9;
	static String d6;
	public static String discoverErrorCode(String objectinstance) throws UnknownHostException, JSONException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("DeviceObject");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectinstance);							// check the specific id
		DBCursor cursor = collection1.find(whereQuery);		
		while (cursor.hasNext()) {
			d6 = cursor.next().toString();			
		}
		jsonObj = new JSONObject(d6);
		d6 = jsonObj.getString("Error Code");
		discover9 = "{'Error Code' : '"+d6+"'}";
		return discover9;
	}
	
	public static void writeLifetime(String objectinstance, String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("Lifetime", newvalue);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	public static void writeNotificationStoring(String objectinstance, String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("Notification Storing", newvalue);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	public static void writeBinding(String objectinstance, String newvalue) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
				
		BasicDBObject newDocument = new BasicDBObject().append("Binding", newvalue);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);			
	}
	
	
	public static void writeAttributeSMSSecurityMode(String endpoint, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("SMS Security Mode", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void writeAttributeClientHoldOffTime(String endpoint, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", endpoint);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("Client Hold-Off Time", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void writeAttributeDisable(String objectinstance, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("Disable", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
	public static void writeAttributeDisableTimeout(String objectinstance, String newattribute) throws UnknownHostException, JSONException{
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );
		DBCollection collection1 = db.getCollection("LWM2MServerObject");
		
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objectinstance);							// check the specific id
		
		BasicDBObject newDocument = new BasicDBObject().append("Disable Timeout", newattribute);
		DBObject update = new BasicDBObject("$set", newDocument);
		collection1.updateMulti(whereQuery, update);
	}
	
public static void executeFactoryResetDevice(String endpoint , String objinstance) throws UnknownHostException {
		
		MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
		DB db = mongoClient.getDB( "rasberryPiDB" );					// Bring device to the initial configuration
		
		DBCollection collection = db.getCollection("LWM2MServerObject");				// collection1
		BasicDBObject whereQuery = new BasicDBObject().append("_id", objinstance);
		collection.remove(whereQuery);
		
		DBCollection collection1 = db.getCollection("LWM2MSecurityObject");				// collection2
		BasicDBObject whereQuery1 = new BasicDBObject().append("_id", endpoint);
		collection1.remove(whereQuery1);

		DBCollection collection2 = db.getCollection("DeviceObject");						// collection3
		BasicDBObject whereQuery2 = new BasicDBObject().append("_id", objinstance);
		collection2.remove(whereQuery2);
	}

public static void writeAttribute(String objectInstanceId, String minPeriod, String maxPeriod, String greaterThan) {
	MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
	DB db = mongoClient.getDB( "rasberryPiDB" );
	DBCollection collection1 = db.getCollection("ObservationTableStats");

	BasicDBObject newDocument = new BasicDBObject().append("_id", objectInstanceId).append("minPeriod", minPeriod).append("maxPeriod", maxPeriod).append("greaterThan", greaterThan);
	collection1.insert(newDocument);
}

public static void startOrStopObservation(String objectInstanceId, String obsStatus, String emailId) throws SchedulerException, JSONException {
	MongoClient mongoClient = new MongoClient( "localhost" , 27018 );
	DB db = mongoClient.getDB( "rasberryPiDB" );
	Scheduler  scheduler = new StdSchedulerFactory().getScheduler();
	if(obsStatus.equalsIgnoreCase("start")) {
		DBCollection collection1 = db.getCollection("ObservationTable");
		
		DBCollection collection = db.getCollection("ObservationTableStats");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", objectInstanceId);							// check the specific id
		DBCursor cursor = collection.find(whereQuery);		
	
		String lessThan = new JSONObject(cursor.next().toString()).getString("lessThan");
		String minPeriod = new JSONObject(cursor.next().toString()).getString("minPeriod");
		BasicDBObject insertDC = new BasicDBObject().append("_id", objectInstanceId).append("status", obsStatus)
								.append("Date", new Date().toString());							// check the specific id
		
		collection1.insert(insertDC);
		
		JobDetail job = JobBuilder.newJob(NotifyJob.class)
				.withIdentity("dummyJobName", "group1").build();
		
		// Trigger the job to run on the next round minute
				Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity("dummyTriggerName", "group1")
					.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
							.withIntervalInSeconds(Integer.parseInt(minPeriod)).repeatForever())
					.build();

				// schedule it
				
				scheduler.getContext().put("emailId", emailId);
				scheduler.getContext().put("lessThan", lessThan);
				scheduler.start();
				scheduler.scheduleJob(job, trigger);

		 notifyServer(objectInstanceId, emailId, lessThan);
	}
	else {
	scheduler.deleteJob(JobKey.jobKey("dummyJobName", "group1"));
	DBCollection collection1 = db.getCollection("ObservationTable");

	BasicDBObject whereQuery = new BasicDBObject().append("_id", objectInstanceId);							// check the specific id
	
	BasicDBObject newDocument = new BasicDBObject().append("status", obsStatus).append("Date", new Date().toString());
	DBObject update = new BasicDBObject("$set", newDocument);
	
	if(obsStatus.equalsIgnoreCase("stop")) {
		exit = true;
		System.out.println("Stopped sending notification, stopped the thread");
	}
	collection1.update(whereQuery, update);
	}
}


public static void notifyServer(final String objectInstanceId, final String emailid, final String lessThan) {
	System.out.println("New thread started which will send the notification to server");
	Thread t = new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				DeviceManagementNotify.Notify(emailid, objectInstanceId, lessThan);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void stop(){
	        exit = true;
	    }
		
	}); 
	t.start();
}

};
