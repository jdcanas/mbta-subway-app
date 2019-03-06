package mbta;

import java.util.ArrayList;

public class Stop {
	//the MBTA id for this stop
	String stopID;
	//the routes this stop is a part of
	ArrayList<String> routeIDs;
	//the IDs of stops that you can get to from this stop
	ArrayList<String> connectionIDs;
	//whether the stop connects to other routes
	boolean isConnector = false;
	
	
	

}
