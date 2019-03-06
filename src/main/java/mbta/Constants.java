package mbta;

public class Constants {
	
	public static final String MBTA_URL = "https://api-v3.mbta.com/";
	
	public static final String ROUTES_API = "routes";
	public static final String ROUTE_FILTER_PARAMETER = "?type=0,1";
	public static final String GET_ROUTES_URL = MBTA_URL + ROUTES_API + ROUTE_FILTER_PARAMETER;
	
	
}
