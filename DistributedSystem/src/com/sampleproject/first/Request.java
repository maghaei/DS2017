package com.sampleproject.first;

import java.sql.Time;

public class Request {
	public static enum RequestStates{notfinished, finished};
	public static enum RequestTypes{register, showProducts, buy};
	
	//the status of request finished or not
	private String status;
	
	//start and end time of request
	private Time startTime;
	private Time finishTime;
	
	//request type
	private String type;
	
	//the owner of request who is responsible to do it
	private ServerProcess owner;
	
	//request response
	private String response;
	
	public Request()
	{
		
	}
	public Request(String reqType, Time start, ServerProcess reqOwner)
	{
		type = reqType;
		startTime = start;
		owner = reqOwner;
	}
	
	public String getStatus(){return status;}
	public Time getStartTime(){return startTime;}
	public Time getFinishTime(){return finishTime;}
	public String getType(){return type;}
	
	public void setStatus(String state){status = state;}
	public void setFinishTime(Time time){finishTime = time;}
	public void setOwner(ServerProcess process) {owner = process;}
	public void setType(String reqType){type = reqType;}
}
