package services;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import mbta.Constants;
import mbta.Route;

public class RouteResponseMapper {
	
	public static ArrayList<Route> processRouteAPIResponse(String response) throws MBTADataMappingException {
		ArrayList<Route> routes = new ArrayList<Route>();
		
		JSONObject responseJSON = JSONResponseMapper.extractJSONFromString(response);
		JSONArray routesJSON = JSONResponseMapper.extractArrayFromJSON(responseJSON, Constants.JSON_DATA_KEY);
		
		JSONObject currRouteJSON;
		Route currRoute;	
		for (Object routeObj: routesJSON) {
			currRouteJSON = (JSONObject) routeObj;
			currRoute = extractRouteFromJSON(currRouteJSON);
			routes.add(currRoute);
		}
		
		return routes;
	}
	
	private static Route extractRouteFromJSON(JSONObject routeJSON) throws MBTADataMappingException {
		Route extractedRoute;
		
		String id = JSONResponseMapper.extractStringFromJSON(routeJSON, Constants.JSON_ID_KEY);
		
		JSONObject routeWrapper = JSONResponseMapper.extractJSONFromJSON(routeJSON, Constants.JSON_ITEMS_WRAPPER_KEY);
		String longName = JSONResponseMapper.extractStringFromJSON(routeWrapper, Constants.JSON_ROUTE_NAME_KEY);
		
		extractedRoute = new Route(longName, id);
		return extractedRoute;
	}
	

}
