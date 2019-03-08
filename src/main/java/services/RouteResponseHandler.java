package services;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import mbta.Constants;
import mbta.Route;

public class RouteResponseHandler {
	
	public static ArrayList<Route> processRouteAPIResponse(String response) throws MBTAUnexpectedResponseFormatException {
		ArrayList<Route> routes = new ArrayList<Route>();
		
		JSONObject responseJSON = JSONResponseHandler.extractJSONFromString(response);
		JSONArray routesJSON = JSONResponseHandler.extractArrayFromJSON(responseJSON, Constants.JSON_DATA_KEY);
		
		JSONObject currRouteJSON;
		Route currRoute;	
		for (Object routeObj: routesJSON) {
			currRouteJSON = (JSONObject) routeObj;
			currRoute = extractRouteFromJSON(currRouteJSON);
			routes.add(currRoute);
		}
		
		return routes;
	}
	
	private static Route extractRouteFromJSON(JSONObject routeJSON) throws MBTAUnexpectedResponseFormatException {
		Route extractedRoute;
		
		String id = JSONResponseHandler.extractStringFromJSON(routeJSON, Constants.JSON_ID_KEY);
		
		JSONObject routeWrapper = JSONResponseHandler.extractJSONFromJSON(routeJSON, Constants.JSON_ITEMS_WRAPPER_KEY);
		String longName = JSONResponseHandler.extractStringFromJSON(routeWrapper, Constants.JSON_ROUTE_NAME_KEY);
		
		extractedRoute = new Route(longName, id);
		return extractedRoute;
	}
	

}
