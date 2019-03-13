package mbta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestStop {
	
	String testStopID1 = "stopID1";
	String stopName = "stopName";
	
	Stop testStop;
	
	HashSet<StopConnection> testConnectionIDs1;
	HashSet<StopConnection> testConnectionIDs2;
	
	String routeID1 = "routeID1";
	String routeID2 = "routeID2";
	
	StopConnection connection1 = new StopConnection("1", "2", routeID1);
	StopConnection connection2 = new StopConnection("2", "3", routeID1);
	StopConnection connection3 = new StopConnection("2", "3", routeID2);
	
	@BeforeEach
	void setup() {
		testConnectionIDs1 = new HashSet<StopConnection>();
		
		testConnectionIDs2 = new HashSet<StopConnection>();
		
		testStop = new Stop(testStopID1, stopName);
	}
	
	@Test
	void testInitializeConnections() {
		testConnectionIDs1.add(connection1);
		testConnectionIDs1.add(connection2);
		
		testStop.initializeConnections(testConnectionIDs1);
		
		assertEquals(1, StopConnection.getRoutesFromConnections(testStop.getConnections()).size());
		assertEquals(2, testStop.getConnections().size());
		assertTrue(testStop.getConnections().contains(connection1));
		assertTrue(testStop.getConnections().contains(connection2));
	} 
	
	@Test
	void testInitialStopState() {
		assertEquals(testStopID1, testStop.getID());
		assertEquals(0, testStop.getConnections().size());
		assertFalse(testStop.isConnector());
	}
	
	@Test
	void testInitialIsConnectorState() {
		testStop.initializeConnections(new HashSet<StopConnection>());
		
		assertFalse(testStop.isConnector());
	}
	
	@Test
	void testUpdateConnections() {
		Stop sameStopDifferentRoute = new Stop(testStopID1, stopName);
		
		testConnectionIDs1.add(connection1);
		testConnectionIDs1.add(connection2);
		testStop.initializeConnections(testConnectionIDs1);
	
		testConnectionIDs2.add(connection3);
		sameStopDifferentRoute.initializeConnections(testConnectionIDs2);
		
		testStop.updateConnections(sameStopDifferentRoute);
		
		assertEquals(3, testStop.getConnections().size());
		assertEquals(2, StopConnection.getRoutesFromConnections(testStop.getConnections()).size());
		assertTrue(testStop.isConnector());
	}
	
	@Test
	void testUpdateConnectionsWithSameConnection() {
		
		HashSet<StopConnection> testConnections = new HashSet<StopConnection>();
		testConnections.add(connection1);
		testConnections.add(connection1);
		
		assertEquals(1, testConnections.size());
	}
	
	

}
