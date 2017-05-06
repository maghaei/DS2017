package com.sampleproject.first;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
		PrintWriter p = response.getWriter();
		
		String func = request.getParameter("radio").toLowerCase();
		String result = dispatchRequests(func);
		p.println(result);
	}
	private String dispatchRequests(String requestType)
	{
		String msg = "";
		ServerProcess sp = selectProperServer();
		Request r = bindRequest(requestType, sp);
		msg = sp.run(r);
		
		/*switch (requestType)
		{
		case "register": 
			bindRequest(requestType, sp);
			if (!sp.isServerBusy()) msg = sp.run();
			else msg = "<html> server " + sp.getID() + " is busy with so many registers" + "</html>";
			break;
		case "show": 
			
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
		*/
		return msg;
	}
	
	private ServerProcess selectProperServer()
	{
		Random r = new Random();
		int i = r.nextInt(3);
		return servers.get(i);
	}
	
	private Request bindRequest(String type, ServerProcess sp)
	{
		Request r = new Request(type, System.currentTimeMillis(), sp);
		sp.addRequest(r);
		return r;
	}

}
