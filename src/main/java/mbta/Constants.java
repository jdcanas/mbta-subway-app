package mbta;

public class Constants {
	
	//API Constants
	public static final String MBTA_URL = "https://api-v3.mbta.com/";
	
	//Route API constants
	public static final String ROUTES_API = "routes";
	public static final String ROUTE_FILTER_PARAMETER = "?type=0,1";
	public static final String GET_ROUTES_URL = MBTA_URL + ROUTES_API + ROUTE_FILTER_PARAMETER;
	
	//Stop API Constants
	public static final String STOPS_API = "stops";
	public static final String STOPS_ROUTE_FILTER_PARAMETER  = "?route=";
	public static final String GET_STOPS_URL = MBTA_URL + STOPS_API + STOPS_ROUTE_FILTER_PARAMETER;

	//JSON field constants
	public static final String JSON_DATA_KEY = "data";
	public static final String JSON_ID_KEY = "id";
	public static final String JSON_ITEMS_WRAPPER_KEY = "attributes";
	public static final String JSON_ROUTE_NAME_KEY = "long_name";
	public static final String JSON_STOP_NAME_KEY = "name";
	
	
	
	//JSON parsing exception messages
	public static final String EXCEPTIONS_UNEXPECTED_TYPE = "Encountered an unexpected type when parsing the route response. ";
	public static final String EXCEPTIONS_INVALID_JSON = "The response being parsed is not valid JSON. ";
	
	public static final String EXCEPTIONS_INVALID_ARRAY = "Failed to extract a JSON array from JSON. ";

	public static final String EXCEPTIONS_INVALID_OBJECT = "Failed to extract a JSON object from JSON. ";

	public static final String EXCEPTIONS_INVALID_STRING = "Failed to extract a String from JSON. ";
}
