package mbta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import services.MBTADataMappingException;
import services.StopResponseMapper;

class TestStopResponseHandler {

	String validStopsResponse = "{ \"data\": [ { \"attributes\": { \"address\": \"Dorchester Ave and Ashmont St, Boston, MA 02124\", \"description\": null, \"latitude\": 42.284652, \"location_type\": 1, \"longitude\": -71.064489, \"name\": \"Ashmont\", \"platform_code\": null, \"platform_name\": null, \"wheelchair_boarding\": 1 }, \"id\": \"place-asmnl\", \"links\": { \"self\": \"/stops/place-asmnl\" }, \"relationships\": { \"child_stops\": {}, \"facilities\": { \"links\": { \"related\": \"/facilities/?filter[stop]=place-asmnl\" } }, \"parent_station\": { \"data\": null } }, \"type\": \"stop\" }, { \"attributes\": { \"address\": \"Fellsway St and Milton St, Dorchester, MA 02124\", \"description\": null, \"latitude\": 42.279682, \"location_type\": 1, \"longitude\": -71.060432, \"name\": \"Cedar Grove\", \"platform_code\": null, \"platform_name\": null, \"wheelchair_boarding\": 1 }, \"id\": \"place-cedgr\", \"links\": { \"self\": \"/stops/place-cedgr\" }, \"relationships\": { \"child_stops\": {}, \"facilities\": { \"links\": { \"related\": \"/facilities/?filter[stop]=place-cedgr\" } }, \"parent_station\": { \"data\": null } }, \"type\": \"stop\" }, { \"attributes\": { \"address\": \"Butler St and Branchfield St, Dorchester, MA 02124\", \"description\": null, \"latitude\": 42.272343, \"location_type\": 1, \"longitude\": -71.062584, \"name\": \"Butler\", \"platform_code\": null, \"platform_name\": null, \"wheelchair_boarding\": 1 }, \"id\": \"place-butlr\", \"links\": { \"self\": \"/stops/place-butlr\" }, \"relationships\": { \"child_stops\": {}, \"facilities\": { \"links\": { \"related\": \"/facilities/?filter[stop]=place-butlr\" } }, \"parent_station\": { \"data\": null } }, \"type\": \"stop\" } ], \"jsonapi\": { \"version\": \"1.0\" } }";
	ArrayList<Stop> parsedStops;
	
	@Test
	public void testValidStopParse() throws MBTADataMappingException {
		parsedStops = StopResponseMapper.processStopAPIResponse(validStopsResponse);
		assertEquals(3, parsedStops.size());
		
		assertEquals("place-asmnl", parsedStops.get(0).getID());
		assertEquals("Ashmont", parsedStops.get(0).getName());
	}
	
	@Test
	public void testInvalidRouteParse() throws MBTADataMappingException {
		MBTADataMappingException error = assertThrows(MBTADataMappingException.class, 
				() -> StopResponseMapper.processStopAPIResponse(""));
		
		assertTrue(error.getMessage().contains(Constants.EXCEPTIONS_INVALID_JSON));
	}

}
