package mbta;

import java.util.ArrayList;

import services.MBTAAPIException;
import services.Requests;

public class Route {
	
	private String longName;
	private String id;
	private ArrayList<Stop> stops;
	
	public Route(String longName, String id) {
		this.longName = longName;
		this.id = id;
	}
	
	public ArrayList<Stop> getStops() throws MBTAAPIException {
		return Requests.getInstance().getStops(id);
	}	
	
	

}
