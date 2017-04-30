package org.sjsu.edu.controller;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.sjsu.edu.model.SudokuBoard;
import org.sjsu.edu.service.RegisterHelperService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import net.minidev.json.JSONValue;

@RestController
@RequestMapping("/sudoku")
public class SudokuGameController {
	static int basic = 10;
	static int premium = 20;
	private JSONObject jsonObj;

@RequestMapping(value="/newuser",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
public String saveRegisterDeviceInfo(@RequestBody String newUser) throws Exception {
	JSONObject jsonObject = new JSONObject(newUser);
	String emailid = jsonObject.getString("emailId");
	String objInstanceId = jsonObject.getString("objectInstanceId");
	String name = jsonObject.getString("name");
	String paymentplan = jsonObject.getString("paymentPlan");
	String amount = jsonObject.getString("amount");
	
		String output = RegisterHelperService.CheckIfRegistered(objInstanceId);
		BasicDBObject basicDBObject = new BasicDBObject();
		
		if(output.equalsIgnoreCase("Registered")){  			
			int noOfTokens = 0;
			if(Integer.parseInt(paymentplan)==0) {
				//basic
				noOfTokens = Integer.parseInt(amount)/basic;
			}
			else if(Integer.parseInt(paymentplan)==1) {
				//premimum
				noOfTokens = Integer.parseInt(amount)/premium;
			}
			else {
				return "Error: Incorrect payment plan";
			}
			List<String> tokens = new ArrayList<String>();
			MongoClient mongoclient = new MongoClient("localhost",27018);
			DB db = mongoclient.getDB("SudokuServer");
			DBCollection collection = (DBCollection) db.getCollection("userTokens");
			BasicDBObject updateQuery = new BasicDBObject();

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("_id", emailid);							// check the specific id
			DBCursor cursor = collection.find(whereQuery);		
			if(cursor.size() > 0) {
				String rowval = cursor.next().toString();
				JSONObject jsoObject = new JSONObject(rowval);
				JSONArray jsonArray = jsoObject.getJSONArray("tokens");
				for(int i=0;i<jsonArray.length();i++) {
					tokens.add(jsonArray.getString(i));
			}
			/*if(jsonArray.length()==0) {
				updateQuery.append("$set", new BasicDBObject().append("paymentplan", paymentplan).append("amount",amount).append("name",name));
				BasicDBObject where = new BasicDBObject("_id",emailid);
				
			}*/
			}
			else {
			basicDBObject.append("_id", emailid).append("name", name).append("amount", amount).append("paymentplan", paymentplan).append("amount", amount);
			collection.insert(basicDBObject);
			tokens = generateTokens(emailid,noOfTokens);
			}
			String tokensString = String.join("\n ", tokens);
			return tokensString;
		}	
		
		return "Please register first";
}

private List<String> generateTokens(String emailid, int noOfTokens) {
		// TODO Auto-generated method stub
		System.out.println("no of tokens =" + noOfTokens);
		List<String> tokens = new ArrayList<String>();
		MongoClient mongoclient = new MongoClient("localhost",27018);
		DB db = mongoclient.getDB("SudokuServer");
		DBCollection collection = (DBCollection) db.getCollection("userTokens");
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.append("_id", emailid);
		for(int i=0;i<noOfTokens;i++) {
			String token = Integer.toString(i) + emailid;
			tokens.add(token);
		}
		basicDBObject.append("tokens", tokens);
		collection.insert(basicDBObject);
		return tokens;
}

@RequestMapping(value="/gettokens",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
public String getAllAvailableTokens(@RequestBody String emailid) throws JSONException {
		JSONObject jsonObject = new JSONObject(emailid);
		List<String> availableTokens = new ArrayList<String>();
		MongoClient mongoclient = new MongoClient("localhost", 27018);
		DB db = mongoclient.getDB("SudokuServer");
		DBCollection collection = (DBCollection) db.getCollection("userTokens");

		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("emailid", jsonObject.getString("emailid"));
		DBCursor dbCursor = collection.find(whereQuery);
		JSONObject jsonObj;
		String rowVal;
		if(dbCursor.size()==0) return "No tokens available";
		while(dbCursor.hasNext()) {
			rowVal = dbCursor.next().toString();
			jsonObj = new JSONObject(rowVal);
			System.out.println(jsonObj.get("token") + " " + jsonObj.getBoolean("available"));
			boolean available = jsonObj.getBoolean("available");		
			if(available==true) availableTokens.add(jsonObj.getString("token"));
		}

		String tokenString = String.join("\n ", availableTokens);
		return tokenString;
	}

@RequestMapping(value="/validate",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
private String validataAndUpdateToken(@RequestBody String sudokuUserInput) throws JSONException {
	String emailId = new JSONObject(sudokuUserInput).getString("emailId");
	String token = new JSONObject(sudokuUserInput).getString("token");
	MongoClient mongoclient = new MongoClient("localhost", 27018);
	DB db = mongoclient.getDB("SudokuServer");
	DBCollection collection = (DBCollection) db.getCollection("userTokens");
	
	BasicDBObject whereQuery = new BasicDBObject();
	whereQuery.put("_id", emailId);
	DBCursor dbCursor = collection.find(whereQuery);
	if(dbCursor.size()==0) return "Invalid emailId";
	jsonObj = new JSONObject(dbCursor.next().toString());
	
	JSONArray arrObj = jsonObj.getJSONArray("tokens");
	if(arrObj.length()==0) return "Invalid tokens: zero left";
	boolean found = false;
	for(int i=0;i<arrObj.length();i++) {
	if(arrObj.getString(i).equals(token)) {
		found = true; break; }	
	}
	
	if(found) {
		BasicDBObject basicDBObject = new BasicDBObject("_id",emailId);
		BasicDBObject pullObj = new BasicDBObject("tokens",token);
		BasicDBObject update = new BasicDBObject("$pull",pullObj);
		collection.update(basicDBObject, update);
		return "Success";
	} 
	 return "Invalid token, Token already used or Token not valid";
}

@RequestMapping(value="/getSudokuBoard",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
public String getSudokuBoard(@RequestBody SudokuBoard sudokuUserInput) throws JSONException {
	
	List<String> sudokuBoard = new ArrayList<String>();
	String result = new String();
	for(int i=0;i<sudokuUserInput.getBoard().length;i++) {
		for(int j=0;j<sudokuUserInput.getBoard()[i].length;j++)
		result = result + sudokuUserInput.getBoard()[i][j] + " ";
		sudokuBoard.add(result);
		result=new String();
	}
		
	String outputSudoku = String.join("/n ", sudokuBoard);
	return outputSudoku;
	
}

@RequestMapping(value="/notify/{emailId}",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
public String getSudokuBoard(@PathVariable String emailId,@RequestBody String numOfTokens) throws JSONException {
	jsonObj = new JSONObject(numOfTokens);
	System.out.println("Received notificaiton from client: number of Tokens as " + jsonObj.getString("numOfTokens"));
	return "Notification received";
}

}