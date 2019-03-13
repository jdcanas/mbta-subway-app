package mbta;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import services.MBTAAPIException;
import services.Requests;

import static org.mockito.Mockito.*;

class TestRoute {
	
	Requests requestsMock;
	ArrayList<Stop> initialStops;
	String stopID1 = "stop1";
	String stopID2 = "stop2";
	String stopID3 = "stop3";
	String stopName = "stopName";
	
	Route testRoute;
	String testRouteID = "routeID";
	String testRouteName = "routeName";
	
	@BeforeEach
	void eachSetup() {
		requestsMock = mock(Requests.class);
		setMock(requestsMock);
		
		testRoute = new Route(testRouteName, testRouteID);
		
		initialStops = new ArrayList<Stop>();
		initialStops.add(new Stop(stopID1, stopName));
		initialStops.add(new Stop(stopID2, stopName));
		initialStops.add(new Stop(stopID3, stopName));
	}

	@AfterEach
	void tearDown() {
		resetSingleton();
	}
	
	@Test
	//tests the simple route 2-3
	void testStopEnrichInitialConnectionsWith2Stops() throws MBTAAPIException {
		initialStops.remove(0);
		
	    when(requestsMock.getStops(anyString())).thenReturn(initialStops);
	    
	    ArrayList<Stop> enrichedStops = testRoute.initializeStops();
	    
	    assertEquals(1, enrichedStops.get(0).getConnections().size());
	    assertTrue(enrichedStops.get(0).getConnections().contains(new StopConnection(stopID2,stopID3,testRouteID)));
	    
	    assertEquals(1, enrichedStops.get(1).getConnections().size());
	    assertTrue(enrichedStops.get(1).getConnections().contains(new StopConnection(stopID3,stopID2,testRouteID)));
	}
	
	@Test
	//tests the simple route 1-2-3
	void testStopEnrichInitialConnectionsWith3Stops() throws MBTAAPIException {
	    when(requestsMock.getStops(anyString())).thenReturn(initialStops);
	    
	    ArrayList<Stop> enrichedStops = testRoute.initializeStops();
	    
	    assertEquals(1, enrichedStops.get(0).getConnections().size());
	    assertTrue(enrichedStops.get(0).getConnections().contains(new StopConnection(stopID1,stopID2,testRouteID)));
	    
	    assertEquals(2, enrichedStops.get(1).getConnections().size());
	    assertTrue(enrichedStops.get(1).getConnections().contains(new StopConnection(stopID2,stopID1,testRouteID)));
	    assertTrue(enrichedStops.get(1).getConnections().contains(new StopConnection(stopID2,stopID3,testRouteID)));
	    
	    assertEquals(1, enrichedStops.get(2).getConnections().size());
	    assertTrue(enrichedStops.get(2).getConnections().contains(new StopConnection(stopID3,stopID2,testRouteID)));
	}
	
	//the two methods below are necessary for mocking the requests. Reflection is used as the mockito framework has issues mocking static methods
    private void setMock(Requests mock){
		try{
			Field instance = Requests.class.getDeclaredField("services");
			instance.setAccessible(true);
			instance.set(instance,mock);
		}catch(Exception e){
			e.printStackTrace();
		}  
	}
	private void resetSingleton() {
		try{
			Field instance = Requests.class.getDeclaredField("services");
			instance.setAccessible(true);
			instance.set(null,null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
