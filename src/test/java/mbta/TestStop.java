package mbta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestStop {
	
	String testStopID1 = "stopID1";
	String stopName = "stopName";
	
	Stop testStop;
	
	HashSet<String> testConnectionIDs1;
	String connection1 = "c1";
	String connection2 = "c2";
	String connection3 = "c3";
	
	HashSet<String> testConnectionIDs2;
	
	String routeID1 = "routeID1";
	String routeID2 = "routeID2";
	
	@BeforeEach
	void setup() {
		testConnectionIDs1 = new HashSet<String>();
		
		testConnectionIDs2 = new HashSet<String>();
		
		testStop = new Stop(testStopID1, stopName);
	}
	
	@Test
	void testInitializeConnections() {
		testConnectionIDs1.add(connection1);
		testConnectionIDs1.add(connection2);
		
		testStop.initializeConnections(testConnectionIDs1, routeID1);
		
		assertEquals(1, testStop.getRoutes().size());
		assertEquals(2, testStop.getConnections().size());
		assertTrue(testStop.getConnections().contains(connection1));
		assertTrue(testStop.getConnections().contains(connection2));
	} 
	
	@Test
	void testInitialStopState() {
		
		assertEquals(testStopID1, testStop.getID());
		assertEquals(0, testStop.getRoutes().size());
		assertEquals(0, testStop.getConnections().size());
		assertFalse(testStop.isConnector());
	}
	
	@Test
	void testInitialIsConnectorState() {
		testStop.initializeConnections(new HashSet<String>(), routeID1);
		
		assertFalse(testStop.isConnector());
	}
	
	@Test
	void testUpdateConnections() {
		Stop sameStopDifferentRoute = new Stop(testStopID1, stopName);
		
		testConnectionIDs1.add(connection1);
		testConnectionIDs1.add(connection2);
		testStop.initializeConnections(testConnectionIDs1, routeID1);
	
		testConnectionIDs2.add(connection3);
		sameStopDifferentRoute.initializeConnections(testConnectionIDs2, routeID2);
		
		testStop.updateConnections(sameStopDifferentRoute);
		
		assertEquals(3, testStop.getConnections().size());
		assertEquals(2, testStop.getRoutes().size());
		assertTrue(testStop.isConnector());
	}

}
