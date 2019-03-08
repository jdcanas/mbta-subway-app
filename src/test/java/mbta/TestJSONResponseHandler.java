package mbta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import services.JSONResponseHandler;
import services.MBTAUnexpectedResponseFormatException;

class TestJSONResponseHandler {
	
	String validKey = "goodKey";
	String invalidKey = "badKey";

	//these tests ensure array parsing properly returns MBTA-specific error messages if an error is encountered in JSON
	@Test
	public void testValidArrayExtract() throws MBTAUnexpectedResponseFormatException {
		JSONObject goodObj = new JSONObject();
		JSONArray goodArr = new JSONArray();
		
		goodArr.add("");
		goodObj.put(validKey, goodArr);
		
		JSONArray parsedArray =  JSONResponseHandler.extractArrayFromJSON(goodObj, validKey);
		assertEquals(1, parsedArray.size());
	}
	
	@Test
	public void testArrayWithKeyNotPresentException() {
		JSONObject badObj = new JSONObject();
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractArrayFromJSON(badObj, invalidKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_ARRAY));
	}
	
	@Test
	public void testObjectWithArrayNotInExpectedPositionException() {
		JSONObject badObj = new JSONObject();
		badObj.put(validKey, "");
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractArrayFromJSON(badObj, validKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_ARRAY));
	}
	
	//these tests ensure object parsing properly returns MBTA-specific error messages if an error is encountered in JSON
	@Test
	public void testValidJSONObjectExtract() throws MBTAUnexpectedResponseFormatException {
		JSONObject goodObj = new JSONObject();
		
		JSONObject nestedObj = new JSONObject();
		String nestedKey = "nestedKey";
		nestedObj.put(nestedKey, "");
		
		goodObj.put(validKey, nestedObj);
		
		JSONObject parsedJSON =  JSONResponseHandler.extractJSONFromJSON(goodObj, validKey);
		assertTrue(parsedJSON.keySet().contains(nestedKey));
	}
		
	@Test
	public void testObjectWithKeyNotPresentException() {
		JSONObject badObj = new JSONObject();
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractJSONFromJSON(badObj, invalidKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_OBJECT));
	}
	
	@Test
	public void testJSONWithNoNestedJSONException() {
		JSONObject badObj = new JSONObject();
		badObj.put(validKey, "");
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractJSONFromJSON(badObj, validKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_OBJECT));
	}
	
	@Test
	public void testValidStringExtract() throws MBTAUnexpectedResponseFormatException {
		JSONObject goodObj = new JSONObject();
		String extractedString = "dataPayload";
				
		goodObj.put(validKey, extractedString);
		
		String payload =  JSONResponseHandler.extractStringFromJSON(goodObj, validKey);
		assertEquals(extractedString, payload);
	}
		
	@Test
	public void testObjectWithStringNotPresentException() {
		JSONObject badObj = new JSONObject();
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractStringFromJSON(badObj, invalidKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_STRING));
	}
	
	@Test
	public void testJSONWithNoStringException() {
		JSONObject badObj = new JSONObject();
		badObj.put(validKey, new JSONArray());
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractStringFromJSON(badObj, validKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_STRING));
	}
	
	@Test
	public void testParseFailWithValidJSONButNotAnObject() {
		String nonJSONObject = "[]";
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractJSONFromString(nonJSONObject));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_JSON));
	}
	
	@Test
	public void testParseFailWithInvalidJSON() {
		String invalidJSON = "";
		
		MBTAUnexpectedResponseFormatException error = assertThrows(MBTAUnexpectedResponseFormatException.class,
				() -> JSONResponseHandler.extractJSONFromString(invalidJSON));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_JSON));
	}
	
	@Test
	public void testParseWithValidJSON() throws MBTAUnexpectedResponseFormatException {
		String validJSON = "{\"key\": \"data\"}";
		
		JSONObject parsedObj = JSONResponseHandler.extractJSONFromString(validJSON);
		
		assertEquals(1, parsedObj.keySet().size());
	}
}
