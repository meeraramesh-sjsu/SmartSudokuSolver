package org.sjsu.edu.device.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class DeviceManagementNotify {

	private static HttpHeaders headers;
	private static HttpEntity<String> entity;
	private static String url;
	private static JSONObject request;
	private static RestTemplate restTemplate = new RestTemplate();

	public static String Notify(String emailid, String objInstance, String lessThan) throws JSONException {
		System.out.println("In New thread");
		MongoClient mongoclient = new MongoClient("localhost", 27018);
		DB db = mongoclient.getDB("SudokuServer");
		DBCollection collection = (DBCollection) db.getCollection("userTokens");

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("emailid", emailid);
		DBCursor dbCursor = collection.find(whereQuery);

		int numOfTokens=0;
		if(dbCursor.size()==0) return Integer.toString(0);
		while(dbCursor.hasNext()) {
			numOfTokens++;
		}
		
		url = "http://localhost:8080/sudoku/notify/" + objInstance;
		
		// create request body
		request = new JSONObject();
		request.put("numOfTokens", Integer.toString(numOfTokens));
		
		// set headers
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		entity = new HttpEntity<String>(request.toString(), headers);
		
		restTemplate.postForObject(url, entity, String.class);
		System.out.println("Sent notification");
		return "Notification sent";
	}
}
