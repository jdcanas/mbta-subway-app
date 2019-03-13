package services;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import mbta.Constants;
import mbta.Stop;

//The adaptor to produce the internal stop representation from the MBTA API response
public class StopResponseMapper {
	
	public static ArrayList<Stop> processStopAPIResponse(String response) throws MBTADataMappingException {
		ArrayList<Stop> stops = new ArrayList<Stop>();
		
		JSONObject responseJSON = JSONResponseMapper.extractJSONFromString(response);
		JSONArray stopsJSON = JSONResponseMapper.extractArrayFromJSON(responseJSON, Constants.JSON_DATA_KEY);
		
		JSONObject currStopJSON;
		Stop currStop;
		for (Object stopObj: stopsJSON) {
			currStopJSON = (JSONObject) stopObj;
			currStop = extractStopFromJSON(currStopJSON);
			stops.add(currStop);
		}
		
		return stops;
	}

	private static Stop extractStopFromJSON(JSONObject stopJSON) throws MBTADataMappingException {
		Stop extractedStop;
		
		String id = JSONResponseMapper.extractStringFromJSON(stopJSON, Constants.JSON_ID_KEY);

		
		JSONObject stopWrapper = JSONResponseMapper.extractJSONFromJSON(stopJSON, Constants.JSON_ITEMS_WRAPPER_KEY);
		String name = JSONResponseMapper.extractStringFromJSON(stopWrapper, Constants.JSON_STOP_NAME_KEY);
		
		extractedStop = new Stop(id, name);
		
		return extractedStop;
	}
}
