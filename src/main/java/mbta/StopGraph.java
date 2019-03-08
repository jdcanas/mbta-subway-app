package mbta;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	public Stop getStop(String stopID) {
		return stopSet.get(stopID);
	}
	

}
