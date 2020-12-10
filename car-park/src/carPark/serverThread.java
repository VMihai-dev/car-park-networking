package carPark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class serverThread extends Thread {

	  private Socket serverSocket = null;
	  private SharedActionState sharedParkSpacesObject;
	  private String myServerThreadName;
	  
	  // Thread constructor
	  public serverThread(Socket serverSocket, String ServerThreadName, SharedActionState SharedObject) {
		 this.serverSocket = serverSocket;
		 sharedParkSpacesObject = SharedObject;
		 myServerThreadName = ServerThreadName;
	  }

	  public void run() {
	    try {
	      System.out.println(myServerThreadName + "initialising.");
	      PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
	      BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
	      String inputLine, outputLine;

	      while ((inputLine = in.readLine()) != null) {
	    	  // Get a lock first
	    	  try { 
	    		  sharedParkSpacesObject.acquireLock();  
	    		  outputLine = sharedParkSpacesObject.processInput(myServerThreadName, inputLine);
	    		  out.println(outputLine);
	    		  sharedParkSpacesObject.releaseLock();  
	    	  } 
	    	  catch(InterruptedException e) {
	    		  System.err.println("Failed to get lock when reading:"+e);
	    	  }
	      }

	       out.close();
	       in.close();
	       serverSocket.close();

	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
	}