package services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import mbta.Constants;

public class JSONResponseMapper {

	public static JSONObject extractJSONFromString(String data) throws MBTADataMappingException {
		JSONObject responseJSON;
		JSONParser parser = new JSONParser();
		
		try {
			responseJSON = (JSONObject) parser.parse(data);
		} catch (ParseException | ClassCastException e) {
			throw new MBTADataMappingException(Constants.EXCEPTIONS_INVALID_JSON + data);
		}
		
		return responseJSON;
	}

	public static String extractStringFromJSON(JSONObject json, String key) throws MBTADataMappingException {
		String string;
		
		if (!json.containsKey(key)) {
			throw new MBTADataMappingException(Constants.EXCEPTIONS_INVALID_STRING + "Object: " +  json.toJSONString() + "Key: " + key);
		}
		
		try {
			string = (String) json.get(key);
		} catch(ClassCastException e) {
			throw new MBTADataMappingException(Constants.EXCEPTIONS_INVALID_STRING + json.toJSONString());
		}
		
		return string;
	}

	public static JSONObject extractJSONFromJSON(JSONObject json, String key) throws MBTADataMappingException {
		JSONObject obj;
		
		if (!json.containsKey(key)) {
			throw new MBTADataMappingException(Constants.EXCEPTIONS_INVALID_OBJECT + "Object: " +  json.toJSONString() + "Key: " + key);
		}
		
		try {
			obj = (JSONObject) json.get(key);
		} catch(ClassCastException e) {
			throw new MBTADataMappingException(Constants.EXCEPTIONS_INVALID_OBJECT + json.toJSONString());
		}
		
		return obj;
	}

	public static JSONArray extractArrayFromJSON(JSONObject json, String key) throws MBTADataMappingException {
		JSONArray items;
		
		if (!json.containsKey(key)) {
			throw new MBTADataMappingException(Constants.EXCEPTIONS_INVALID_ARRAY + "Object: " +  json.toJSONString() + "Key: " + key);
		}
		
		try {
			items = (JSONArray) json.get(key);
		} catch(ClassCastException e) {
			throw new MBTADataMappingException(Constants.EXCEPTIONS_INVALID_ARRAY + json.toJSONString());
		}
		
		return items;
	}

}
