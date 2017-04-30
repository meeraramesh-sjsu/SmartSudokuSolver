package org.sjsu.edu.device.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class NotifyJob implements Job
{
	private String url;
	private JSONObject request;
	private HttpHeaders headers;
	private HttpEntity<String> entity;
	private RestTemplate restTemplate;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		SchedulerContext schedulerContext;
		try {
			schedulerContext = context.getScheduler().getContext();
			String emailId = (String) schedulerContext.get("emailId");
			String lessThan = (String) schedulerContext.get("lessThan");
			
			@SuppressWarnings("resource")
			MongoClient mongoclient = new MongoClient("localhost", 27018);
			@SuppressWarnings("deprecation")
			DB db = mongoclient.getDB("SudokuServer");
			DBCollection collection = (DBCollection) db.getCollection("userTokens");

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("emailid", emailId);
			DBCursor dbCursor = collection.find(whereQuery);
			int numOfTokens=0;
			if(dbCursor.size()!=0)
			while(dbCursor.hasNext()) {
				numOfTokens++;
			}
			if(numOfTokens <= Integer.parseInt(lessThan)) {
				url = "http://localhost:8080/sudoku/notify/" + emailId;
				
				// create request body
				request = new JSONObject();
				request.put("numOfTokens", Integer.toString(numOfTokens));
				
				// set headers
				headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				entity = new HttpEntity<String>(request.toString(), headers);
				
				restTemplate.postForObject(url, entity, String.class);
				System.out.println("Sent notification");
			}
				
			System.out.println("Hello Quartz!");
		} catch (SchedulerException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}


}