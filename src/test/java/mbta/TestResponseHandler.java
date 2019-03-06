package mbta;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestResponseHandler {

	String validRoutesResponse = "{\"data\": [{ \"attributes\": {\"color\": \"DA291C\",\"description\": \"Rapid Transit\",\"direction_destinations\": [\"Ashmont/Braintree\",\"Alewife\" ], \"direction_names\": [\"South\", \"North\" ], \"fare_class\": \"Rapid Transit\",\"long_name\": \"Red Line\",\"short_name\": \"\",\"sort_order\": 10010,\"text_color\": \"FFFFFF\",\"type\": 1 }, \"id\": \"Red\",\"links\": { \"self\": \"/routes/Red\" }, \"relationships\": {\"line\": { \"data\": { \"id\": \"line-Red\",\"type\": \"line\" }}}, \"type\": \"route\" }, {\"attributes\": {\"color\": \"DA291C\",\"description\": \"Rapid Transit\",\"direction_destinations\": [\"Mattapan\",\"Ashmont\" ], \"direction_names\": [\"Outbound\",\"Inbound\" ], \"fare_class\": \"Rapid Transit\",\"long_name\": \"Mattapan Trolley\",\"short_name\": \"\",\"sort_order\": 10011,\"text_color\": \"FFFFFF\",\"type\": 0 }, \"id\": \"Mattapan\",\"links\": { \"self\": \"/routes/Mattapan\" }, \"relationships\": {\"line\": { \"data\": { \"id\": \"line-Mattapan\",\"type\": \"line\" }}}, \"type\": \"route\" }, {\"attributes\": {\"color\": \"ED8B00\",\"description\": \"Rapid Transit\",\"direction_destinations\": [\"Forest Hills\",\"Oak Grove\" ], \"direction_names\": [\"South\", \"North\" ], \"fare_class\": \"Rapid Transit\",\"long_name\": \"Orange Line\",\"short_name\": \"\",\"sort_order\": 10020,\"text_color\": \"FFFFFF\",\"type\": 1 }, \"id\": \"Orange\",\"links\": { \"self\": \"/routes/Orange\" }, \"relationships\": {\"line\": { \"data\": { \"id\": \"line-Orange\",\"type\": \"line\" }}}, \"type\": \"route\" }], \"jsonapi\": {\"version\": \"1.0\" }}";
	
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}