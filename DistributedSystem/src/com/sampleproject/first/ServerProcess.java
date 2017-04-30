package com.sampleproject.first;

import java.util.ArrayList;

public class ServerProcess extends Thread{
	
	public enum States{idle,busy};
	
	//each server has its own id
	private String serverID;
	
	//a queue of requests that each server has to do 
	private ArrayList<Request> requests;
	
	//server status
	private String status;
	
	public ServerProcess()
	{
		requests = new ArrayList<Request>();
	}
	
	public ServerProcess(String id)
	{
		serverID = id;
		requests = new ArrayList<Request>();
	}
	
	public String getID(){return serverID;}
	public void setID(String id){serverID = id;}
	
	@Override
	public void run()
	{
		while (true)
		{
			if (requests.size() > 0)
			{
				status = "busy";
				Request req = requests.get(0);
				switch (req.getType())
				{
					case "register": register();
						break;
					case "show": showAvailableItems();
						break;
					case "buy": buy();
						break;
				}
				if (req.getStatus().equals("finished")) requests.remove(0);
			}
			else
			{
				status = "idle";
			}
		}
	}
	
	private void register()
	{}
	private void showAvailableItems()
	{}
	private void buy()
	{}
}
