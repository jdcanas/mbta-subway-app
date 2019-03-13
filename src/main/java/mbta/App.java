package mbta;

import services.MBTAAPIException;

public class App {

    public static void main(String[] args) {    	
    	RouteManager routeManager = new RouteManager();
    	
		try {
			routeManager.performMBTARequests();
		} catch (MBTAAPIException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Printing routes: ");
		routeManager.printRoutes();
		System.out.println("==================================================\n");
		
		System.out.println("Printing the routes with the min and max number of stops");
		routeManager.printStopRange();
		System.out.println("==================================================\n");
		
		System.out.println("Printing connecting stops: ");
		routeManager.printConnectorStops();
		System.out.println("==================================================\n");
		
		if (args.length == 2) {
			try {
				routeManager.printRoutesBetweenStops(args[0], args[1]);
			} catch (NoPathFoundException e) {
				System.out.println("No path was found for the given arguments. Error message: ");
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("2 Stops were not passed for routing. See the readme if you would like to run the program with 2 stops.");
		}
    }
    
    
}
