package org.sjsu.edu.controller;
import java.io.*;

import org.codehaus.jettison.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sjsu.edu.service.RegisterHelperService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class DeviceManagementController{ 
	private static JSONObject request;
	private static String discover;
	private static String discoverResource;
	private static String execute;
	
	public  static void execute() {
		
		BufferedReader bufferRead;
		RestTemplate restTemplate = new RestTemplate();
		String url;
		HttpHeaders headers;
		HttpEntity<String> entity;
		String read;
		
		try {
				do {///TODO name or object instance
				System.out.println("Enter the object instance id of device to be managed - ");	// first see if device is registered or not
				bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String objInstance = bufferRead.readLine();
				String checkRegister = RegisterHelperService.CheckIfRegistered(objInstance);

				if(checkRegister.equalsIgnoreCase("false")){
					System.out.println("The device is not registered to the server \n");
					continue;
				}

				String deviceURI = "localhost";				// device ipaddress+port

				// get object instance id corresponding to the endpoint name
				String name = RegisterHelperService.getClientEndpointName(objInstance);		

				String choice;
		
					System.out.println("Select what you want to do.. \n");
					System.out.println("1. Device Management \n2. Information Reporting \n0. Exit");
					BufferedReader bufferobj = new BufferedReader(new InputStreamReader(System.in));
					choice = bufferobj.readLine();

					switch(choice){
					case "1":

						
						String choice1;
				
							System.out.println("Select what you want to do.. \n");
							System.out.println("1.1 Read \n1.2 Discover \n1.3 Write  \n1.4 Write Attributes \n1.5 Execute \n1.6 Create \n1.7 Delete  \n0. Exit");
							BufferedReader bufferobj1 = new BufferedReader(new InputStreamReader(System.in));
							choice1 = bufferobj1.readLine();

							switch(choice1){
							case "1.1":

								String choice2;
								
									System.out.println("Select the resource to read.. \n");	
									System.out.println("1. Short Server ID(/1/instance/0) \n2. Lifetime(/1/instance/1) \n3. Notification Storing(/1/instance/6)  \n4. Binding(/1/instance/7) \n5. Manufacturer Name(/3/instance/0) \n6. Device Type(/3/instance/17) \n7. Error Code(/3/instance/11) \n0. Exit");
									BufferedReader bufferobj2 = new BufferedReader(new InputStreamReader(System.in));
									choice2 = bufferobj2.readLine();
									restTemplate = new RestTemplate();
									switch(choice2){
									case "1":
										
										url = "http://" + deviceURI + ":8080/deviceManagement/read/1/shortServerId";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);


										// set headers
										 headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
									 entity = new HttpEntity<String>(request.toString(), headers);
										read = restTemplate.postForObject(url, entity, String.class);

										System.out.println("Output from Device .... \n");
										System.out.println("The Short Server ID is: "+read +"\n");
										break;

									case "2":

										url = "http://" + deviceURI + ":8080/deviceManagement/read/1/lifetime";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);

										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										read = restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("The Lifetime of device is: "+read +"seconds \n");
										break;

									case "3":

										url = "http://" + deviceURI + ":8080/deviceManagement/read/1/notification";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);

										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										read = restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("The value of Notification Storing is: "+read +"\n");
										break;	

									case "4":

										url = "http://" + deviceURI + ":8080/deviceManagement/read/1/binding";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);

										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										read = restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("The device supported Binding Mode is: "+read +"\n");
										break;	

									case "5":

										url = "http://" + deviceURI + ":8080/deviceManagement/read/1/manufacturer";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);

										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										read = restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("The Manufacturer Name of device is: "+read +"\n");
										break;	

									case "6":

										url = "http://" + deviceURI + ":8080/deviceManagement/read/1/deviceType";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);

										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										read = restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("The device type is: "+read +"\n");
										break;	

									case "7":

										url = "http://" + deviceURI + ":8080/deviceManagement/read/1/errorCode";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);

										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										read = restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("The Error Code at device is: "+read +"\n");
										break;	

									case "0":
										break;

									default:
										System.out.println("Sorry.. Enter a choice.. \n");
										break;			
									}		
								
								break;					// break of case 1.1

							case "1.2":

								String choice3;
								
									System.out.println("Select what to discover.. \n");
									System.out.println("1. By Object ID \n2. By Resource ID \n0. Exit");
									BufferedReader bufferobj21 = new BufferedReader(new InputStreamReader(System.in));
									choice3 = bufferobj21.readLine();

									switch(choice3){
									case "1":

										System.out.println("Enter the object id to discover(0,1 or 3) \n");
										System.out.println("0 - LWM2MSecurity");
										System.out.println("1- LWM2MServer");
										System.out.println("3 - Device Object");
										BufferedReader bufferobj3 = new BufferedReader(new InputStreamReader(System.in));
										String objectid = bufferobj3.readLine();

										url = "http://" + deviceURI + ":8080/deviceManagement/discover/object";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);
										request.put("objectId", objectid);
										request.put("endpoint", name);

										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										discover = restTemplate.postForObject(url, entity, String.class);
										
										System.out.println("Output from Device .... \n");
										System.out.println(discover +"\n");
										break;

									case "2":

										System.out.println("Enter the object id to discover(0,1 or 3) \n");
										BufferedReader bufferobj31 = new BufferedReader(new InputStreamReader(System.in));
										String objectid1 = bufferobj31.readLine();
										
										System.out.println("Enter the Resource id {0,1,2,3,7,11}  \n");
										System.out.println("1 (Object ID) , 0 - Short Server Id");
										System.out.println("1 (Object ID) , 7 - Binding Mode");
										BufferedReader bufferobj311 = new BufferedReader(new InputStreamReader(System.in));
										String resourceid = bufferobj311.readLine();
										
										
										url = "http://" + deviceURI + ":8080/deviceManagement/discover/resource";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);
										request.put("objectId", objectid1);
										request.put("endpoint", name);
										request.put("resourceId", resourceid);
										
										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										discoverResource = restTemplate.postForObject(url, entity, String.class);
										
										System.out.println("Output from Device .... \n");
										System.out.println(discoverResource +"\n");									
										break;	

									case "0":
										break;

									default:
										System.out.println("Sorry.. Enter a choice.. \n");
										break;			
									}		
								
								break;					// break of case 1.2

							case "1.3":

								String choice4;
								
									System.out.println("Select the resource to Write.. \n");
									System.out.println("1. Lifetime(/1/instance/1) \n2. Notification Storing(/1/instance/6) \n3. Binding(/1/instance/7) \n0. Exit");
									BufferedReader bufferobj212 = new BufferedReader(new InputStreamReader(System.in));
									choice4 = bufferobj212.readLine();

									switch(choice4){

									case "1":

										System.out.println("Enter the new Lifetime value to Write to Device: ");
										BufferedReader bufferobj3 = new BufferedReader(new InputStreamReader(System.in));
										String newvalue = bufferobj3.readLine();

										url = "http://" + deviceURI + ":8080/deviceManagement/write/3/1";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);
										request.put("lifetime", newvalue);
										
										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("Write Successful.. Lifetime updated to "+newvalue +" seconds. \n");
										break;

									case "2":

										System.out.println("Enter the new value for Notification Storing:  ");
										BufferedReader bufferobj4 = new BufferedReader(new InputStreamReader(System.in));
										String newvalue1 = bufferobj4.readLine();

										url = "http://" + deviceURI + ":8080/deviceManagement/write/3/6";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);
										request.put("notification", newvalue1);
										
										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("Write Successful.. Notification Storing changed to "+newvalue1 +".\n");
										break;

									case "3":
										System.out.println("Enter the new value for Binding Mode:  ");
										BufferedReader bufferobj41 = new BufferedReader(new InputStreamReader(System.in));
										String newvalue2 = bufferobj41.readLine();

										url = "http://" + deviceURI + ":8080/deviceManagement/write/3/7";

										// create request body
										request = new JSONObject();
										request.put("objectInstanceId", objInstance);
										request.put("bindingMode", newvalue2);
										
										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										restTemplate.postForObject(url, entity, String.class);
										System.out.println("Output from Device .... \n");
										System.out.println("Write Successful.. New binding Mode is: "+newvalue2 +".\n");
										break;	

									case "0":
										break;

									default:
										System.out.println("Sorry.. Enter a choice.. \n");
										break;			
									}		
																
								break;

							case "1.4":
								String maxPeriod = "10";
								String minPeriod = "5";
								String lessThan = "3";
								String cancelObs = "no";
								
								url = "http://" + deviceURI + ":8080/deviceManagement/writeAttributes/0";
								System.out.println("Enter email id");
								BufferedReader bufferobj123 = new BufferedReader(new InputStreamReader(System.in));
								String emailid = bufferobj123.readLine();
								
								// create request body
								request = new JSONObject();
								request.put("maxPeriod", maxPeriod);
								request.put("objectInstanceId", objInstance);
								request.put("minPeriod", minPeriod);
								request.put("lessThan", lessThan);
								request.put("cancelObs", cancelObs);
								request.put("emailId", emailid);
								// set headers
								headers = new HttpHeaders();
								headers.setContentType(MediaType.APPLICATION_JSON);
								entity = new HttpEntity<String>(request.toString(), headers);
								
								restTemplate.postForObject(url, entity, String.class);
								System.out.println("Changed Write Attributes!");
							
								break;					// break of case 1.4

							case "1.5":

								String choice6;
								
									System.out.println("Select the Object ID of the Resource to Execute \n");
									System.out.println("1. Device Object(2) \n0. Exit");
									BufferedReader bufferobj21d = new BufferedReader(new InputStreamReader(System.in));
									choice6 = bufferobj21d.readLine();

									switch(choice6){
									
									case "2":
										//REBOOT
										String execute2;
										System.out.println("Enter the Resource ID(4) to execute...\n");	
										BufferedReader bufferobj4 = new BufferedReader(new InputStreamReader(System.in));
										String resourceid1 = bufferobj4.readLine();

										if(!resourceid1.equalsIgnoreCase("4")){
											execute2 = "There's no resource to execute corresponding to this object id..\n";
											System.out.println("Output from Device .... \n");
											System.out.println(execute2 +"\n");
											break;		
										}

											// 1. get new object instance
										url = "http://" + deviceURI + ":8080/deviceManagement/execute/4";

										// create request body
										request = new JSONObject();
										request.put("resourceId", resourceid1);
										
										
										// set headers
										headers = new HttpHeaders();
										headers.setContentType(MediaType.APPLICATION_JSON);
										entity = new HttpEntity<String>(request.toString(), headers);
										
										execute = restTemplate.postForObject(url, entity, String.class);
									
										if(resourceid1.equalsIgnoreCase("4")){

											System.out.println("Output from Device .... \n");
											System.out.println(execute +"\n");
										}
										
										break;

									case "0":
										break;

									default:
										System.out.println("Sorry.. Enter a choice.. \n");
										break;			
									}		
								
								break;					// break of case 1.5

							case "1.6":

								System.out.println("Enter the Object ID(1) to Create an Object Instance \n");
								BufferedReader bufferobj23 = new BufferedReader(new InputStreamReader(System.in));
								String objectid= bufferobj23.readLine();

								if(!objectid.equalsIgnoreCase("1")){
									System.out.println("There's no object to create with this Object ID.. \n");
									break;					
								}

								objInstance = RegisterHelperService.getobjectinstanceid();		// 1. get new object instance

								// get new values..
								String newvalue = RegisterHelperService.createNewServerObject(name, objInstance);

								url = "http://" + deviceURI + ":8080/deviceManagement/create/" + objInstance;
								
								// create request body
								request = new JSONObject();
								request.put("LWM2MServer", newvalue );
								
								// set headers
								headers = new HttpHeaders();
								headers.setContentType(MediaType.APPLICATION_JSON);
								entity = new HttpEntity<String>(request.toString(), headers);
								
								restTemplate.postForObject(url, entity, String.class);
								String create1 = "The Create Operation is successfully done.. The device is now registered with the new Object Instance i.e.  "+objInstance+ "\nExit first, and than run the discover operation to see that the new object is added to the intended device\n";
								System.out.println("Output from Device .... \n");
								System.out.println(create1 +"\n");

								// IF SUCCESS, changes in RegisteredDevices collection.	
								RegisterHelperService.RegistrationUpdate(name, objInstance);

								break;

							case "1.7":
								// DELETE only for ServerObject
								System.out.println("Enter the endpoint name");
								BufferedReader bufferobj234 = new BufferedReader(new InputStreamReader(System.in));
								name= bufferobj234.readLine();
								
								System.out.println("Loading.... \n");

								url = "http://" + deviceURI + ":8080/deviceManagement/delete/";

								// create request body
								request = new JSONObject();
								request.put("objectInstanceId", objInstance);
								request.put("endpoint", name );
								
								// set headers
								headers = new HttpHeaders();
								headers.setContentType(MediaType.APPLICATION_JSON);
								entity = new HttpEntity<String>(request.toString(), headers);
								
								restTemplate.postForObject(url, entity, String.class);
								System.out.println("Output from Device .... \n");
								System.out.println("Delete Operation successful.. The object instance is deleted from the device.."+"\n");

								// IF SUCCESS, also remove from Serverdb...		// basically deregister device operation
								RegisterHelperService.DeregisterDevice(name , objInstance);

								break;
							
							case "0":
								break;

							default:
								System.out.println("Sorry.. Enter a choice.. \n");
								break;

							}// end of inner switch
						
						break;

					case "2":

							String choice7;
								System.out.println("Select what you want to do.. \n");
								System.out.println("2.1 Observe \n2.2 Cancel Observation \n0. Exit");
								BufferedReader bufferobj12 = new BufferedReader(new InputStreamReader(System.in));
								choice7 = bufferobj12.readLine();
								System.out.println("Enter email id");
								BufferedReader bufferobj123 = new BufferedReader(new InputStreamReader(System.in));
								String emailid = bufferobj123.readLine();
								switch(choice7){

								case "2.1":
									System.out.println("Observe operation started... Check Serverdb to check values.. \n");

									url = "http://" + deviceURI + ":8080/deviceManagement/observe/start";

									// create request body
									request = new JSONObject();
									request.put("objectInstanceId", objInstance);
									request.put("status", "start" );
									request.put("emailId", emailid);
									// set headers
									headers = new HttpHeaders();
									headers.setContentType(MediaType.APPLICATION_JSON);
									entity = new HttpEntity<String>(request.toString(), headers);
									
									restTemplate.postForObject(url, entity, String.class);

									break;

								case "2.2":

									url = "http://" + deviceURI + ":8080/deviceManagement/observe/stop";

									// create request body
									request = new JSONObject();
									request.put("objectInstanceId", objInstance);
									request.put("status", "stop" );
									request.put("emailId", emailid);
									// set headers
									headers = new HttpHeaders();
									headers.setContentType(MediaType.APPLICATION_JSON);
									entity = new HttpEntity<String>(request.toString(), headers);
									
									restTemplate.postForObject(url, entity, String.class);
									break;

								case "0":
									break;

								default:
									System.out.println("Sorry.. Enter a choice.. \n");
									break;

								}	// end of inner switch
					}		
				}while(true); //end of do			
					// end of try
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		} 
	
}