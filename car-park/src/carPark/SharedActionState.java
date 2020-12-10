package carPark;

public class SharedActionState{
	
	// Initialise the needed variables
	private SharedActionState mySharedObj;
	private String myThreadName;
	private double mySharedParkingSpaces;
	private double mySharedQueueSize;
	private boolean accessing=false; // true if a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers

	
	SharedActionState(double freeSpaces, double queueSize) {
		mySharedParkingSpaces = freeSpaces;
		mySharedQueueSize = queueSize;
	}

      //Attempt to acquire a lock
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting;
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true;
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

	public synchronized String processInput(String myThreadName, String theInput) {
    		System.out.println(myThreadName + " received "+ theInput);
    		String theOutput = null;
    			// Handling logic for entrance clients
    			if (myThreadName.equals("CarParkThread1")) {
    				if(theInput.equals("Car arriving")) {
    					if(mySharedParkingSpaces > 0) {
    						theOutput = "You can come in";
    					} else {
    						mySharedQueueSize += 1;
    						theOutput = "Parking spaces full, you will have to wait in a queue. Queue size: " + mySharedQueueSize;
    					}
    				} else if(theInput.equals("Coming in")) {
    					mySharedParkingSpaces -= 1;
        				theOutput = "Car parked, available spaces left: " + mySharedParkingSpaces;
    				} else {
    					theOutput = "Command not recognized. Try 'Car arriving'";
    				}
    			}
    			
    			else if (myThreadName.equals("CarParkThread2")) {
    				if(theInput.equals("Car arriving")) {
    					if(mySharedParkingSpaces > 0) {
    						theOutput = "You can come in";
    					} else {
    						mySharedQueueSize += 1;
    						theOutput = "Parking spaces full, you will have to wait in a queue. Queue size: " + mySharedQueueSize;
    					}
    				} else if(theInput.equals("Coming in")) {
    					mySharedParkingSpaces -= 1;
        				theOutput = "Car parked, available spaces left: " + mySharedParkingSpaces;
    				} else {
    					theOutput = "Command not recognized. Try 'Car arriving'";
    				}
    			}
    			
    			// Handling logic for exit clients
       			else if (myThreadName.equals("CarParkThread3")) {
       				if(theInput.equals("Leave")) {
    					if(mySharedParkingSpaces < 5) {
    						if(mySharedQueueSize <= 0) {
        					mySharedParkingSpaces += 1;
    						theOutput = "Car left, available spaces now: " + mySharedParkingSpaces;
    						} else if (mySharedQueueSize > 0) {
        						mySharedQueueSize -= 1;
        						theOutput = "Car left, letting a car from the queue to come in. Queue size now: " + mySharedQueueSize;
    						}
    					} else {
    						theOutput = "There is no car in the car park";
    					}
    				} else {
    					theOutput = "Command not recognized. Try 'Leave'";
    				}
       			}
    			
       			else if (myThreadName.equals("CarParkThread4")) {
       				if(theInput.equals("Leave")) {
    					if(mySharedParkingSpaces < 5) {
    						if(mySharedQueueSize <= 0) {
        					mySharedParkingSpaces += 1;
    						theOutput = "Car left, available spaces now: " + mySharedParkingSpaces;
    						} else if (mySharedQueueSize > 0) {
        						mySharedQueueSize -= 1;
        						theOutput = "Car left, letting a car from the queue to come in. Queue size now: " + mySharedQueueSize;
    						}
    					} else {
    						theOutput = "There is no car in the car park";
    					}
    				} else {
    					theOutput = "Command not recognized. Try 'Leave'";
    				}
       			}
    			
    		System.out.println(theOutput);
    		return theOutput;
    	}	
}
