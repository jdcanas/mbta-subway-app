package mbta;

import java.util.ArrayList;
import java.util.LinkedHashSet;

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
	
	public void printRoutes() {
		for (Route route: routes) {
			System.out.println(route.getLongName());
			System.out.println("    Stops on this route:");
			System.out.println("    " + route.getStopIDs().toString());
			System.out.println();
		}
	}
	
	public void printStopRange() {
		StopRange range = new StopRange(routes);
		range.printStopRange();
	}
	
	public void printConnectorStops() {
		ArrayList<Stop> connectors = stopGraph.getConnectorStops();
		
		for (Stop stop: connectors) {
			LinkedHashSet<String> routes = StopConnection.getRoutesFromConnections(stop.getConnections());
			System.out.println("Stop: " + stop.getName() + " (" + stop.getID() + ") " + "is on routes: " + routes.toString());
		}
	}

	public void printRoutesBetweenStops(String startStopID, String endStopID) throws NoPathFoundException {
		GraphSearcher searcher = new GraphSearcher(stopGraph);
		
		ArrayList<StopConnection> path = searcher.findPath(startStopID, endStopID);
		LinkedHashSet<String> routes = StopConnection.getRoutesFromConnections(path);
		
		System.out.println("The route found between the stops " + startStopID + " and " + endStopID + " is as follows: ");
		System.out.println("    " + routes.toString());
	}
	
	

}
