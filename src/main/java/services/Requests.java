package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import mbta.Constants;
import mbta.Route;
import mbta.Stop;

public class Requests {
	
	private static Requests services = null;
	private Requests() {}
	
	public static Requests getInstance() {
		if (services == null) {
			services = new Requests();
		}
		return services;
	}
	
	public ArrayList<Route> getRoutes() throws MBTAAPIException {
		String routesResponse = performGetRequest(Constants.GET_ROUTES_URL);
		ArrayList<Route> routes = RouteResponseMapper.processRouteAPIResponse(routesResponse);
		
		return routes;
	}
	
	public ArrayList<Stop> getStops(String routeID) throws MBTAAPIException {
		String stopsUrl = Constants.GET_STOPS_URL + routeID;
		String stopsResponse = performGetRequest(stopsUrl);
		
		ArrayList<Stop> stops = StopResponseMapper.processStopAPIResponse(stopsResponse);
		
		return stops;
	}
	
	private String performGetRequest(String requestUrl) throws MBTAResourceRequestIncompleteException {
		URL url;
		String response = "";
		
			try {
				url = new URL(requestUrl);
				
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				
				response = getResponseFromConnection(con);
			} catch (IOException e) {
				throw new MBTAResourceRequestIncompleteException("HttpConnection to mbta api has failed. Requested resource: " + requestUrl);
			}
		return response;
	}
	
	private static String getResponseFromConnection(HttpURLConnection connection) throws IOException {
		String inputLine = "";
		StringBuffer content = new StringBuffer();
		
		try(InputStreamReader isStream = new InputStreamReader(connection.getInputStream());
				BufferedReader in = new BufferedReader(isStream)) {
			
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
		} catch(IOException e) {
			//here we are only try/catching to close the resource. 
			//We want to bubble the exception up to be handled by the caller
			throw e; 
		}
		
		return content.toString();
	}
	

}
