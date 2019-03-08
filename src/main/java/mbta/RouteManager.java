package mbta;

import java.util.ArrayList;

import services.MBTAAPIException;
import services.Requests;

public class RouteManager {
	
	ArrayList<Route> routes;
	StopGraph stopGraph;
	
	public RouteManager() {
		this.stopGraph = new StopGraph();
	}
	
	public void performMBTARequests() throws MBTAAPIException {
		routes = Requests.getInstance().getRoutes();
		
		ArrayList<Stop> stops;
		for (Route route: routes) {
			stops = route.initializeStops();
			stopGraph.addRoute(stops);
		}
	}
	
	public void printRouteNames() {
		for (Route route: routes) {
			System.out.println(route.getLongName());
		}
	}
	
	public void printStopRange() {
		StopRange range = new StopRange(routes);
		range.printStopRange();
	}
	
	public void printConnectorStops() {
		ArrayList<Stop> connectors = stopGraph.getConnectorStops();
		
		for (Stop stop: connectors) {
			System.out.println("Stop: " + stop.getName() + " is on lines: " + stop.getRoutes().toString());
		}
	}
	
	

}
