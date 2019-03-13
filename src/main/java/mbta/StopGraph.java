package mbta;

import java.util.ArrayList;
import java.util.HashMap;


//A custom graph implementation is used here for better understanding of the underlying implementation
//The 'Stop' class is a node, and the 'StopConnection' is an edge 
public class StopGraph {
	
	HashMap<String, Stop> stopSet;
	
	public StopGraph() {
		stopSet = new HashMap<String, Stop>();
	}
	
	public void addRoute(ArrayList<Stop> stops) {
		Stop existingStop;
		for (Stop newStop: stops) {
			String id = newStop.getID();
			if (!stopSet.containsKey(id)) {
				stopSet.put(id, newStop);
			} else { //in this case, we have encountered a stop which is already in the graph. We must update the existing entry
				existingStop = stopSet.get(id);
				existingStop.updateConnections(newStop);
				stopSet.put(id, existingStop);
			}
		}
	}
	
	public ArrayList<Stop> getConnectorStops() {
		ArrayList<Stop> connectors = new ArrayList<Stop>();
		
		for (Stop stop: stopSet.values()) {
			if (stop.isConnector()) {
				connectors.add(stop);
			}
		}
		
		return connectors;
	}
	
	public Stop getStop(String stopID) throws NoPathFoundException {
		Stop stop = stopSet.get(stopID);
		if (stop == null) {
			throw new NoPathFoundException(Constants.EXCEPTIONS_NO_STOP);
		}
		
		return stop;
	}
	

}
