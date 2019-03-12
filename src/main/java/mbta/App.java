package mbta;

import services.MBTAAPIException;

public class App {

    public static void main(String[] args) {
    	RouteManager routeManager = new RouteManager();
    	
		try {
			routeManager.performMBTARequests();
		} catch (MBTAAPIException e) {
			e.printStackTrace();
		}
		
		System.out.println("Printing route names: ");
		routeManager.printRouteNames();
		System.out.println("==================================================");
		
		System.out.println("Printing the routes with the min and max number of stops");
		routeManager.printStopRange();
		System.out.println("==================================================");
		
		System.out.println("Printing connecting stops: ");
		routeManager.printConnectorStops();
		System.out.println("==================================================");
    }
}
