package mbta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Services {
	
	private static Services services = null;
	private Services() {}
	
	public static Services getInstance() {
		if (services == null) {
			services = new Services();
		}
		return services;
	}
	
	public ArrayList<Route> getRoutes() throws MBTAResourceRequestIncompleteException {
		String routesResponse = performGetRequest(Constants.GET_ROUTES_URL);
		ArrayList<Route> routes = ResponseHandler.processRouteAPIResponse(routesResponse);
		
		return routes;
	}
	
	public ArrayList<Stop> getStops(String routeID) {
		return null;
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
