package carPark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class entrance2 {
    public static void main(String[] args) throws IOException {
    	// Initiating the needed variables
        Socket CarParkClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int  CarParkSocketNumber = 4547;
        String  CarParkServerName = "localhost";
        String  CarParkClientID = " CarPark_Entrace_Client2";
        
        // Connecting to the specified host (localhost) and port (4547)
        try {
        	CarParkClientSocket = new Socket(CarParkServerName, CarParkSocketNumber);
            out = new PrintWriter(CarParkClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(CarParkClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ CarParkSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + CarParkClientID + " client and IO connections");
        
        // While we are connected, wait for user input. 
        // Send a message automatically on "You can come in" replies
        fromServer = "first";
        while (true) {
        	if (fromServer.equals("You can come in")) {
        		fromUser = "Coming in";
                System.out.println("From: " + CarParkClientID + " To: CarParkServer sending: " + fromUser);
                out.println(fromUser);
                fromServer = in.readLine();
                System.out.println("From: CarParkServer received: " + fromServer);
                continue;
        	}
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println("From: " + CarParkClientID + " To: CarParkServer sending: " + fromUser);
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println("From: CarParkServer received: " + fromServer);
        }
    }
}
