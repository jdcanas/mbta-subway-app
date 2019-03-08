package mbta;

import java.util.ArrayList;

public class StopRange {
	
	private Route minRoute;
	private Route maxRoute;
	
	//in the case of two routes with the same number of stops, the first one found will be considered the correct answer
	public StopRange(ArrayList<Route> routes) {
		Route currMaxRoute;
		Route currMinRoute;
		
		//initialize
		if (routes.size() == 0) {
			return;
		} else {
			currMaxRoute = routes.get(0);
			currMinRoute = routes.get(0);
		}
	
		for (Route route: routes) {
			if (route.getNumStops() < currMinRoute.getNumStops()) {
				currMinRoute = route;
			}
			
			if (route.getNumStops() > currMaxRoute.getNumStops()) {
				currMaxRoute = route;
			}
		}
		
		setMinMax(currMinRoute, currMaxRoute);
	}
	
	private void setMinMax(Route min, Route max) {
		minRoute = min;
		maxRoute = max;
	}
	
	public void printStopRange() {
		if (maxRoute == null || minRoute == null) {
			System.out.println("No routes found");
		} else {
			System.out.println("The route with the most stops is " + maxRoute.getLongName() + " and it has " + maxRoute.getNumStops() + " stops.");
			System.out.println("The route with the least stops is " + minRoute.getLongName() + " and it has " + minRoute.getNumStops() + " stops.");
		}
	}
	
	public Route getMaxRoute() {
		return maxRoute;
	}
	
	public Route getMinRoute() {
		return minRoute;
	}

}
