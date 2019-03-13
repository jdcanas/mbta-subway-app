package mbta;

import java.util.HashSet;

//Represents a node in the stop graph
//This is an internal representation of the stop object from the MBTA /stops API
public class Stop {
	//the MBTA id for this stop
	private String stopID;
	//the IDs of stops that you can get to from this stop
	private HashSet<StopConnection> connections;
	
	private String name;
	
	public Stop(String stopID, String name) {
		this.stopID = stopID;
		this.name = name;
		this.connections = new HashSet<StopConnection>();
	}
	
	public void initializeConnections(HashSet<StopConnection> connections) {
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
