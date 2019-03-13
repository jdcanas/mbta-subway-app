package mbta;

import java.util.HashSet;

public class Stop {
	//the MBTA id for this stop
	private String stopID;
	//the IDs of stops that you can get to from this stop
	private HashSet<StopConnection> connections;
	//whether the stop connects to other routes
	
	private String name;
	
	public Stop(String stopID, String name) {
		this.stopID = stopID;
		this.name = name;
		this.connections = new HashSet<StopConnection>();
	}
	
	public void initializeConnections(HashSet<StopConnection> connections, String routeID) {
		HashSet<String> routeSet = new HashSet<String>();
		routeSet.add(routeID);
		
		addConnections(connections);
	}
	
	public void updateConnections(Stop stopWithSameIDFromAnotherRoute) {
		addConnections(stopWithSameIDFromAnotherRoute.getConnections());
	}
	
	private void addConnections(HashSet<StopConnection> connectionIDs) {
		this.connections.addAll(connectionIDs);
	}
	
	public boolean isConnector() {
		return StopConnection.getRoutesFromConnections(connections).size() > 1;
	}
	
	public String getID() {
		return stopID;
	}
	
	public HashSet<StopConnection> getConnections() {
		return connections;
	}
	
	public String getName() {
		return name;
	}
	
}
