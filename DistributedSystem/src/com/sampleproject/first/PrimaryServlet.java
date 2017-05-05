package com.sampleproject.first;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class sizeComparator implements Comparator<ServerProcess> 
{
	@Override
	public int compare(ServerProcess s1, ServerProcess s2)
	{
		return s1.getRequestsCount() < s2.getRequestsCount() ? 1 : s1.getRequestsCount() == s2.getRequestsCount() ? 0 : -1;
	}
}

/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("/PrimaryServlet")
public class PrimaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<ServerProcess> servers;
       
	public void init()
	{
        servers = new ArrayList<>();
        for (int i=1; i <= 3; i++)
        {
        	ServerProcess s = new ServerProcess(i);
        	servers.add(s);
        }
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrimaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String func = request.getParameter("radio").toLowerCase();
		PrintWriter p = response.getWriter();
		String result = dispatchRequests(func);
		p.println(result);
		p.println(showRequestsCount());
	}
	private String dispatchRequests(String requestType)
	{
		String msg = "";
		ServerProcess sp = selectProperServer();
		switch (requestType)
		{
		case "register": 
			Request r1 = new Request("register", System.currentTimeMillis(), sp);
			sp.addRequest(r1);
			if (!sp.isServerBusy()) msg = sp.run();
			else msg = "<html> server " + sp.getID() + " is busy with so many registers" + "</html>";
			break;
		case "show": 
			Request r2 = new Request("show", System.currentTimeMillis(), sp);
			sp.addRequest(r2);
			if (!sp.isServerBusy()) msg = sp.run();
			else msg = "<html> server " + sp.getID() + " is busy with so many show requests" + "</html>";
			break;
		case "buy": 
			Request r3 = new Request("buy", System.currentTimeMillis(), sp);
			sp.addRequest(r3);
			if (!sp.isServerBusy()) msg = sp.run();
			else msg = "<html> server " + sp.getID() + " is busy with so many buy requests" + "</html>";
			break;
		}
		return msg;
	}
	
	private ServerProcess selectProperServer()
	{
		Collections.sort(servers, new Comparator<ServerProcess>(){@Override public int compare(ServerProcess s1, ServerProcess s2)
		{
			return s1.getRequestsCount() < s2.getRequestsCount() ? -1 : s1.getRequestsCount() == s2.getRequestsCount() ? 0 : 1;
		}});
		return servers.get(0);
	}
	private String showRequestsCount()
	{
		String msg = "<html><body>";
		for (ServerProcess sp : servers)
		{
			msg += "s" + sp.getID() + " has " + sp.getRequestsCount() + " request." + "<br/>";
			for (Request r : sp.getRequests())
			{
				msg += r.getStatus() + "-" + r.getStartTime() + "-" + r.getFinishTime() + "<br/>";
			}
		}
		msg += "</body></html>";
		return msg;
	}
}
