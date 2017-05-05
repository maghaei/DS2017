package com.sampleproject.first;


public class Request {
	
	//the status of request finished or not
	private String status;
	
	//start and end time of request
	private long startTime;
	private long finishTime;
	
	//request type
	private String type;
	
	//the owner of request who is responsible to do it
	private ServerProcess owner;
	
	//request response
	private String response;
	
	public Request()
	{
		
	}
	public Request(String reqType, long start, ServerProcess reqOwner)
	{
		type = reqType;
		startTime = start;
		owner = reqOwner;
	}
	
	public String getStatus(){return status;}
	public long getStartTime(){return startTime;}
	public long getFinishTime(){return finishTime;}
	public String getType(){return type;}
	
	public void setStatus(String state){status = state;}
	public void setFinishTime(long time){finishTime = time;}
	public void setOwner(ServerProcess process) {owner = process;}
	public void setType(String reqType){type = reqType;}
}
