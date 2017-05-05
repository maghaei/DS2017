package com.sampleproject.first;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServerProcess{
	
	private int MAX_REQUEST_NUMBER = 5;
	
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
	

	public String run()
	{
		String msg = "";
		if (requests.size() <= MAX_REQUEST_NUMBER)
		{
			if (requests.size() > 0)
			{
				status = "serving";
				Request req = requests.get(0);
				switch (req.getType())
				{
					case "register": msg = registerUser(req);
						break;
					case "show": msg = showAvailableItems(req);
						break;
					case "buy": msg = buy(req);
						break;
				}
				//requests.remove(req);
			}
			else
			{
				status = "idle";
			}
		}
		else status = "busy";
			return msg;
	}
	
	private String registerUser(Request r)
	{
		//System.out.println("You are registerd as a new user.");
		r.setFinishTime(System.currentTimeMillis());
		r.setStatus("finished");
		return "<html><body><h3>You are registerd as a new user</h3></body></html>";
	}
	private String showAvailableItems(Request r)
	{
		String[] items = {"Laptop", "smart phone", "notebook", "tablet"};
		String str = "";
		for (String item : items)
		{
			str += item + "<br/>";
			System.out.println(item);
		}
		r.setFinishTime(System.currentTimeMillis());
		r.setStatus("finished");
		String msg = "<html><body><h3>" + str + "</h3></body></html>";
		return msg;
	}
	private String buy(Request r)
	{
		//System.out.println("the item will be deliverd you");
		r.setFinishTime(System.currentTimeMillis());
		r.setStatus("finished");
		return "<html><body><h3>the item will be deliverd you</h3></body></html>";
	}
	
	public void addRequest(Request r)
	{
		requests.add(r);
	}
	public boolean isServerBusy()
	{
		if (status.equals("busy")) return true;
		else return false;
	}
}
