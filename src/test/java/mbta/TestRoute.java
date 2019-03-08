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
		
		testRoute = new Route(testRouteID, testRouteName);
		
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
	void testStopEnrichRouteName() throws MBTAAPIException {
	    when(requestsMock.getStops(anyString())).thenReturn(initialStops);
	    
	    ArrayList<Stop> enrichedStops = testRoute.initializeStops();
	    
	    assertEquals(initialStops.size(), enrichedStops.size());
	    
	    assertEquals(1, enrichedStops.get(0).getRoutes().size());
	    assertTrue(enrichedStops.get(0).getRoutes().contains(testRouteName));
	    
	    assertEquals(1, enrichedStops.get(1).getRoutes().size());
	    assertTrue(enrichedStops.get(1).getRoutes().contains(testRouteName));
	    
	    assertEquals(1, enrichedStops.get(2).getRoutes().size());
	    assertTrue(enrichedStops.get(2).getRoutes().contains(testRouteName));
	}
	
	@Test
	//tests the simple route 2-3
	void testStopEnrichInitialConnectionsWith2Stops() throws MBTAAPIException {
		initialStops.remove(0);
		
	    when(requestsMock.getStops(anyString())).thenReturn(initialStops);
	    
	    ArrayList<Stop> enrichedStops = testRoute.initializeStops();
	    
	    assertEquals(1, enrichedStops.get(0).getConnections().size());
	    assertTrue(enrichedStops.get(0).getConnections().contains(stopID3));
	    
	    assertEquals(1, enrichedStops.get(1).getConnections().size());
	    assertTrue(enrichedStops.get(1).getConnections().contains(stopID2));
	}
	
	@Test
	//tests the simple route 1-2-3
	void testStopEnrichInitialConnectionsWith3Stops() throws MBTAAPIException {
	    when(requestsMock.getStops(anyString())).thenReturn(initialStops);
	    
	    ArrayList<Stop> enrichedStops = testRoute.initializeStops();
	    
	    assertEquals(1, enrichedStops.get(0).getConnections().size());
	    assertTrue(enrichedStops.get(0).getConnections().contains(stopID2));
	    
	    assertEquals(2, enrichedStops.get(1).getConnections().size());
	    assertTrue(enrichedStops.get(1).getConnections().contains(stopID1));
	    assertTrue(enrichedStops.get(1).getConnections().contains(stopID3));
	    
	    assertEquals(1, enrichedStops.get(2).getConnections().size());
	    assertTrue(enrichedStops.get(2).getConnections().contains(stopID2));
	}
	
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
