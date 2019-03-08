package services;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import mbta.Constants;
import mbta.Stop;

public class StopResponseHandler {
	
	public static ArrayList<Stop> processStopAPIResponse(String response) throws MBTAUnexpectedResponseFormatException {
		ArrayList<Stop> stops = new ArrayList<Stop>();
		
		JSONObject responseJSON = JSONResponseHandler.extractJSONFromString(response);
		JSONArray stopsJSON = JSONResponseHandler.extractArrayFromJSON(responseJSON, Constants.JSON_DATA_KEY);
		
		JSONObject currStopJSON;
		Stop currStop;
		for (Object stopObj: stopsJSON) {
			currStopJSON = (JSONObject) stopObj;
			currStop = extractStopFromJSON(currStopJSON);
			stops.add(currStop);
		}
		
		return stops;
	}

	private static Stop extractStopFromJSON(JSONObject stopJSON) throws MBTAUnexpectedResponseFormatException {
		Stop extractedStop;
		
		String id = JSONResponseHandler.extractStringFromJSON(stopJSON, Constants.JSON_ID_KEY);

		
		JSONObject stopWrapper = JSONResponseHandler.extractJSONFromJSON(stopJSON, Constants.JSON_ITEMS_WRAPPER_KEY);
		String name = JSONResponseHandler.extractStringFromJSON(stopWrapper, Constants.JSON_STOP_NAME_KEY);
		
		extractedStop = new Stop(id, name);
		
		return extractedStop;
	}
}
