package com.sampleproject.first;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServerProcess{
	
	private int MAX_REQUEST_NUMBER = 50;
	private int MAX_ITERATION_TIMES = 1000;
	
	//each server has its own id
	private int serverID;
	
	//a queue of requests that each server has to do 
	private ArrayList<Request> requests;
	
	//server status
	private String status;
	
	public ServerProcess()
	{
	}
	public ServerProcess(int id)
	{
		serverID = id;
		status = "idle";
		requests = new ArrayList<>();
	}
	
	public int getID(){return serverID;}
	public ArrayList<Request> getRequests(){return requests;}
	public int getRequestsCount(){return requests.size();}
	
	public void addRequest(Request r)
	{
		requests.add(r);
	}
	public boolean isServerBusy()
	{
		if (status.equals("busy")) return true;
		else return false;
	}
	
	public String run(Request req)
	{
		String msg = "";
		/*if (requests.size() <= MAX_REQUEST_NUMBER)
		{
			if (requests.size() > 0)
			{
				status = "serving";
				Request req = requests.get(getTheFirstUnfinishedRequestPosition());
				*/
				switch (req.getType())
				{
					case "register": msg = registerUser(req);
						break;
					case "show": msg = showAvailableItems(req);
						break;
					case "buy": msg = buy(req);
						break;
				}
			/*
			}
			else
			{
				status = "idle";
			}
		}
		else status = "busy";
		*/
			return msg;
	}
	
	private String registerUser(Request r)
	{
		iterate();
		r.setFinishTime(System.currentTimeMillis());
		r.setStatus("finished");
		String elapsedTime = "Request start time: " + r.getStartTime() + " --- " + " Request finish time: " + r.getFinishTime();
		String response = "Response time for this request is: " + String.valueOf((r.getFinishTime()-r.getStartTime()));
		return "<html><body><h1>server " + serverID + 
				": </h1><br/>" + "<h2>You are registerd as a new user</h2><br/><br/><br/><h3>" + elapsedTime + "<br/><br/>" + response + "</h3></body></html>";
	}
	private String showAvailableItems(Request r)
	{
		iterate();
		String[] items = {"Laptop", "smart phone", "notebook", "tablet"};
		String str = "Current available Products:<br/>";
		for (String item : items)
		{
			str += item + "<br/>";
		}
		r.setFinishTime(System.currentTimeMillis());
		r.setStatus("finished");
		String elapsedTime = "Request start time: " + r.getStartTime() + " --- " + " Request finish time: " + r.getFinishTime();
		String response = "Response time for this request is: " + String.valueOf((r.getFinishTime()-r.getStartTime()));
		String msg = "<html><body><h1>server " + serverID + ":<h1/> <br/><h2>" + str + "</h2><br/><br/><br/><h3>" + 
						elapsedTime + "<br/><br/>" + response + "</h3></body></html>";
		return msg;
	}
	private String buy(Request r)
	{
		iterate();
		r.setFinishTime(System.currentTimeMillis());
		r.setStatus("finished");
		String elapsedTime = "Request start time: " + r.getStartTime() + " --- " + " Request finish time: " + r.getFinishTime();
		String response = "Response time for this request is: " + String.valueOf((r.getFinishTime()-r.getStartTime()));
		return "<html><body><h1>server " + serverID + ": <h1/><br/>" + "<h2>the item will be deliverd you</h2><br/><h3>" + elapsedTime + 
				"<br/><br/><br/>" + response + "</h3></body></html>";
	}
	/*private int getTheFirstUnfinishedRequestPosition()
	{
		int j = 0;
		for (int i=0; i<requests.size(); i++)
		{
			if (requests.get(i).getStatus().equals("unfinished")) j = i;
			break;
		}
		return j;
	}
	*/
	private void iterate()
	{
		Random rand = new Random();
		MAX_ITERATION_TIMES = rand.nextInt(15000)+5000;
		long j = 0;
		for (int i=0; i<MAX_ITERATION_TIMES; i++)
		{
			j++;
			for (int k=0; k<MAX_ITERATION_TIMES; k++)
			j += k;
		}
		System.out.println(j);
	}
}
