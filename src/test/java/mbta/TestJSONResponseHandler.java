package mbta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import services.JSONResponseMapper;
import services.MBTADataMappingException;

class TestJSONResponseHandler {
	
	String validKey = "goodKey";
	String invalidKey = "badKey";

	//these tests ensure array parsing properly returns MBTA-specific error messages if an error is encountered in JSON
	@Test
	public void testValidArrayExtract() throws MBTADataMappingException {
		JSONObject goodObj = new JSONObject();
		JSONArray goodArr = new JSONArray();
		
		goodArr.add("");
		goodObj.put(validKey, goodArr);
		
		JSONArray parsedArray =  JSONResponseMapper.extractArrayFromJSON(goodObj, validKey);
		assertEquals(1, parsedArray.size());
	}
	
	@Test
	public void testArrayWithKeyNotPresentException() {
		JSONObject badObj = new JSONObject();
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractArrayFromJSON(badObj, invalidKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_ARRAY));
	}
	
	@Test
	public void testObjectWithArrayNotInExpectedPositionException() {
		JSONObject badObj = new JSONObject();
		badObj.put(validKey, "");
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractArrayFromJSON(badObj, validKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_ARRAY));
	}
	
	//these tests ensure object parsing properly returns MBTA-specific error messages if an error is encountered in JSON
	@Test
	public void testValidJSONObjectExtract() throws MBTADataMappingException {
		JSONObject goodObj = new JSONObject();
		
		JSONObject nestedObj = new JSONObject();
		String nestedKey = "nestedKey";
		nestedObj.put(nestedKey, "");
		
		goodObj.put(validKey, nestedObj);
		
		JSONObject parsedJSON =  JSONResponseMapper.extractJSONFromJSON(goodObj, validKey);
		assertTrue(parsedJSON.keySet().contains(nestedKey));
	}
		
	@Test
	public void testObjectWithKeyNotPresentException() {
		JSONObject badObj = new JSONObject();
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractJSONFromJSON(badObj, invalidKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_OBJECT));
	}
	
	@Test
	public void testJSONWithNoNestedJSONException() {
		JSONObject badObj = new JSONObject();
		badObj.put(validKey, "");
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractJSONFromJSON(badObj, validKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_OBJECT));
	}
	
	@Test
	public void testValidStringExtract() throws MBTADataMappingException {
		JSONObject goodObj = new JSONObject();
		String extractedString = "dataPayload";
				
		goodObj.put(validKey, extractedString);
		
		String payload =  JSONResponseMapper.extractStringFromJSON(goodObj, validKey);
		assertEquals(extractedString, payload);
	}
		
	@Test
	public void testObjectWithStringNotPresentException() {
		JSONObject badObj = new JSONObject();
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractStringFromJSON(badObj, invalidKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_STRING));
	}
	
	@Test
	public void testJSONWithNoStringException() {
		JSONObject badObj = new JSONObject();
		badObj.put(validKey, new JSONArray());
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractStringFromJSON(badObj, validKey));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_STRING));
	}
	
	@Test
	public void testParseFailWithValidJSONButNotAnObject() {
		String nonJSONObject = "[]";
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractJSONFromString(nonJSONObject));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_JSON));
	}
	
	@Test
	public void testParseFailWithInvalidJSON() {
		String invalidJSON = "";
		
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class,
				() -> JSONResponseMapper.extractJSONFromString(invalidJSON));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_JSON));
	}
	
	@Test
	public void testParseWithValidJSON() throws MBTADataMappingException {
		String validJSON = "{\"key\": \"data\"}";
		
		JSONObject parsedObj = JSONResponseMapper.extractJSONFromString(validJSON);
		
		assertEquals(1, parsedObj.keySet().size());
	}
}
