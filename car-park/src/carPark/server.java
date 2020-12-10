package carPark;

import java.net.*;
import java.io.*;


public class server {
  public static void main(String[] args) throws IOException {
  	// Initiating the needed variables
	ServerSocket serverSocket = null;
    boolean listening = true;
    String serverName = "CarPark";
    int serverNumber = 4547;
    
    // Variables and values to be used for the sharedObject
    double carParkSpaces = 5;
    double queueSize = 0;
    
    // Creating the SharedActionState object that will be shared between the threads
    SharedActionState sharedParkSpacesObject = new SharedActionState(carParkSpaces, queueSize);
    
    // Starting the server
    try {
    	serverSocket = new ServerSocket(serverNumber);
    } catch (IOException e) {
    	System.err.println("Could not start " + serverName + " specified port.");
    	System.exit(-1);
    }
    System.out.println(serverNumber + " started");
    
    // Listen for connection and assign a thread name + the sharedObject to clients.
    while (listening){
      new serverThread(serverSocket.accept(), "CarParkThread1", sharedParkSpacesObject).start();
      new serverThread(serverSocket.accept(), "CarParkThread2", sharedParkSpacesObject).start();
      new serverThread(serverSocket.accept(), "CarParkThread3", sharedParkSpacesObject).start();
      new serverThread(serverSocket.accept(), "CarParkThread4", sharedParkSpacesObject).start();
      System.out.println("CarPark is ready.");
    }
    serverSocket.close();
  }
}