package mbta;

import java.util.HashSet;

public class Stop {
	//the MBTA id for this stop
	private String stopID;
	//the routes this stop is a part of
	private HashSet<String> routeIDs;
	//the IDs of stops that you can get to from this stop
	private HashSet<String> connectionIDs;
	//whether the stop connects to other routes
	
	private String name;
	
	public Stop(String stopID, String name) {
		this.stopID = stopID;
		this.name = name;
		this.routeIDs = new HashSet<String>();
		this.connectionIDs = new HashSet<String>();
	}
	
	public void initializeConnections(HashSet<String> connectionIDs, String routeID) {
		HashSet<String> routeSet = new HashSet<String>();
		routeSet.add(routeID);
		
		addConnections(connectionIDs, routeSet);
	}
	
	public void updateConnections(Stop stopWithSameIDFromAnotherRoute) {
		addConnections(stopWithSameIDFromAnotherRoute.getConnections(), stopWithSameIDFromAnotherRoute.getRoutes());
	}
	
	private void addConnections(HashSet<String> connectionIDs, HashSet<String> routeIDs) {
		this.routeIDs.addAll(routeIDs);
		this.connectionIDs.addAll(connectionIDs);
	}
	
	public boolean isConnector() {
		return routeIDs.size() > 1;
	}
	
	public String getID() {
		return stopID;
	}
	
	public HashSet<String> getRoutes() {
		return routeIDs;
	}
	
	public HashSet<String> getConnections() {
		return connectionIDs;
	}
	
	public String getName() {
		return name;
	}
	
}
