package com.sampleproject.first;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
	protected void doPost(HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final PrintWriter p = response.getWriter();
		
		final String func = request.getParameter("radio").toLowerCase();
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		Future<?> future = executor.submit(new Runnable(){
			@Override 
			public void run()
			{
				String msg = "";
				ServerProcess sp = selectProperServer();
				Request r = bindRequest(func, sp);
				msg = sp.run(r);
				p.println(msg);
			}});
		executor.shutdown();
		try {
	        future.get(100, TimeUnit.MILLISECONDS);  //     <-- wait 8 seconds to finish
	    } catch (InterruptedException e) {    //     <-- possible error cases
	        System.out.println("job was interrupted");
	    } catch (ExecutionException e) {
	        System.out.println("caught exception: " + e.getCause());
	    } catch (TimeoutException e) {
	        future.cancel(true);              //     <-- interrupt the job
	        System.out.println("server maybe crashed due to request timeout! the request will be send to another server");
	        ExecutorService executor1 = Executors.newFixedThreadPool(1);
			Future<?> future1 = executor1.submit(new Runnable(){
				@Override 
				public void run()
				{
					String msg = "";
					ServerProcess sp = selectProperServer();
					Request r = bindRequest(func, sp);
					msg = sp.run(r);
					p.println(msg);
				}});
			executor.shutdown();
			try {
		        future1.get(100, TimeUnit.MILLISECONDS);  //     <-- wait 8 seconds to finish
		    } catch (InterruptedException e1) {    //     <-- possible error cases
		        System.out.println("job was interrupted");
		    } catch (ExecutionException e1) {
		        System.out.println("caught exception: " + e1.getCause());
		    } catch (TimeoutException e1) {
		        future.cancel(true);              //     <-- interrupt the job
		        System.out.println("All servers are busy right now. Please try another time.");
		    }
	    }
	}
	/*private String dispatchRequests(final String requestType)
	{
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future = executor.submit(new Runnable(){
			@Override 
			public void run()
			{
				String msg = "";
				ServerProcess sp = selectProperServer();
				Request r = bindRequest(requestType, sp);
				msg = sp.run(r);
				p.println(msg);
			}	
			});
		executor.shutdown();
		try {
	        future.get(100, TimeUnit.MILLISECONDS);  //     <-- wait 8 seconds to finish
	    } catch (InterruptedException e) {    //     <-- possible error cases
	        System.out.println("job was interrupted");
	    } catch (ExecutionException e) {
	        System.out.println("caught exception: " + e.getCause());
	    } catch (TimeoutException e) {
	        future.cancel(true);              //     <-- interrupt the job
	        System.out.println("server maybe crashed due to request timeout!");
	    }
		return "";
	}
	*/
	
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
