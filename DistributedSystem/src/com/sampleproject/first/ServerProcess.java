package com.sampleproject.first;

import java.util.ArrayList;

public class ServerProcess extends Thread{
	//each server has its own id
	private String serverID;
	
	//a queue of requests that each server has to do 
	private ArrayList<Request> requests;
	

	public ServerProcess()
	{}
	
	public ServerProcess(String id, ArrayList<Request> reqList)
	{
		serverID = id;
		requests = reqList;
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			if (requests.size() > 0)
			{
				Request req = requests.get(0);
				switch (req.getType())
				{
					case "register": 
						break;
					case "show":
						break;
					case "buy":
						break;
				}
				if (req.getStatus().equals("finished")) requests.remove(0);
			}
		}
		
	}
}
