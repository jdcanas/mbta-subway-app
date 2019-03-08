package mbta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

import services.MBTAAPIException;
import services.Requests;

public class Route {
	
	private String longName;
	private String routeID;
	private ArrayList<String> stopIDs;
	
	public Route(String longName, String routeID) {
		this.longName = longName;
		this.routeID = routeID;
		this.stopIDs = new ArrayList<String>();
	}
	
	public ArrayList<Stop> initializeStops() throws MBTAAPIException {
		ArrayList<Stop> apiStops = Requests.getInstance().getStops(routeID);
		setIDsFromStops(apiStops);
		
		ArrayList<Stop> enrichedStops = getEnrichedStopsWithConnections(apiStops);
		
		return enrichedStops;
	}
	
	private void setIDsFromStops(ArrayList<Stop> apiStops) {
		this.stopIDs = new ArrayList<String>(apiStops.stream()
						.map(stop -> stop.getID())
						.collect(Collectors.toList()));
	}
	
	private ArrayList<Stop> getEnrichedStopsWithConnections(ArrayList<Stop> stops) {
		ArrayList<Stop> stopsWithConnections = new ArrayList<Stop>();
		
		Stop currStop;
		HashSet<String> currConnectionIDs;
		for (int i = 0; i < stops.size(); i++) {
			currStop = stops.get(i);
			currConnectionIDs = getConnectionIDs(i, stops);
			
			currStop.initializeConnections(currConnectionIDs, routeID);
			
			stopsWithConnections.add(currStop);
		}
		
		return stopsWithConnections;
	}
	
	private static HashSet<String> getConnectionIDs(int index, ArrayList<Stop> stops) {
		HashSet<String> connectionIDs = new HashSet<String>();
		
		if (index != 0) { //add the previous stop if this is not the first stop
			connectionIDs.add(stops.get(index - 1).getID());
		}
		
		if (index != stops.size() - 1) { //add the next stop if this is not the last stop
			connectionIDs.add(stops.get(index + 1).getID());
		}
		
		return connectionIDs;
	}

	public String getLongName() {
		return longName;
	}
	
	public int getNumStops() {
		return stopIDs.size();
	}
	
	public String getID() {
		return this.routeID;
	}

}
