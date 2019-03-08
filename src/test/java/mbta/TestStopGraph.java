package mbta;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestStopGraph {
	
	Stop stop1Mock;
	Stop stop2Mock;
	Stop stop2DifferentRouteMock;
	Stop stop3Mock;
	Stop stop4Mock;
	
	String stop1ID = "stop1";
	String stop2ID = "stop2";
	String stop3ID = "stop3";
	String stop4ID = "stop4";
	String stop5ID = "stop5";
	String stopName = "name";
	
	String route1ID = "route1";
	String route2ID = "route2";
	String route3ID = "route3";
	String route4ID = "route4";
	
	@BeforeEach
	void setup() {
		stop1Mock = new Stop(stop1ID, stopName);
	
		stop2Mock = new Stop(stop2ID, stopName);
		
		stop2DifferentRouteMock = new Stop(stop2ID, stopName);
		
		stop3Mock = new Stop(stop3ID, stopName);
		
		stop4Mock = new Stop(stop4ID, stopName);
	}

	@Test
	//this test the graph with 1 route and 3 stops, 1-2-3
	void testSimpleGraphWithOneRoute() {
		HashSet<String> routes = new HashSet<String>();
		routes.add(route1ID);
		
		HashSet<String> stop1Connections = new HashSet<String>();
		stop1Connections.add(stop2ID);
		stop1Mock.initializeConnections(stop1Connections, route1ID);
		
		HashSet<String> stop2Connections = new HashSet<String>();
		stop2Connections.add(stop1ID);
		stop2Connections.add(stop3ID);
		stop2Mock.initializeConnections(stop2Connections, route1ID);
		
		HashSet<String> stop3Connections = new HashSet<String>();
		stop3Connections.add(stop2ID);
		stop3Mock.initializeConnections(stop3Connections, route1ID);
		
		ArrayList<Stop> mockedStops = new ArrayList<Stop>();
		mockedStops.add(stop1Mock);
		mockedStops.add(stop2Mock);
		mockedStops.add(stop3Mock);
		
		StopGraph graph = new StopGraph();
		graph.addRoute(mockedStops);
		
		assertEquals(0, graph.getConnectorStops().size());
		
		assertEquals(stop1Connections, graph.getStop(stop1ID).getConnections());
		assertEquals(stop2Connections, graph.getStop(stop2ID).getConnections());
		assertEquals(stop3Connections, graph.getStop(stop3ID).getConnections());
		
		assertEquals(routes, graph.getStop(stop1ID).getRoutes());
		assertEquals(routes, graph.getStop(stop2ID).getRoutes());
		assertEquals(routes, graph.getStop(stop3ID).getRoutes());
	}
	
	@Test
	//this test the graph with 2 routes and 3 stops, 1-2-3
	//1-2 are in route 1, and 2-3 are in route 2
	void testSimpleGraphWith2Routes() {
		//route 1 setup
		HashSet<String> stop1Connections = new HashSet<String>();
		stop1Connections.add(stop2ID);
		stop1Mock.initializeConnections(stop1Connections, route1ID);
		
		HashSet<String> stop2Connections = new HashSet<String>();
		stop2Connections.add(stop1ID);
		stop2Mock.initializeConnections(stop2Connections, route1ID);
		
		//route 2 setup
		HashSet<String> stop2DifferentRouteConnections = new HashSet<String>();
		stop2DifferentRouteConnections.add(stop3ID);
		stop2DifferentRouteMock.initializeConnections(stop2DifferentRouteConnections, route2ID);
		
		HashSet<String> stop3Connections = new HashSet<String>();
		stop3Connections.add(stop2ID);
		stop3Mock.initializeConnections(stop3Connections, route2ID);
		
		ArrayList<Stop> mockedRoute1 = new ArrayList<Stop>();
		mockedRoute1.add(stop1Mock);
		mockedRoute1.add(stop2Mock);
		
		ArrayList<Stop> mockedRoute2 = new ArrayList<Stop>();
		mockedRoute2.add(stop2DifferentRouteMock);
		mockedRoute2.add(stop3Mock);
		
		StopGraph graph = new StopGraph();
		graph.addRoute(mockedRoute1);
		graph.addRoute(mockedRoute2);
		
		//test connections
		assertEquals(1, graph.getConnectorStops().size());
		assertTrue(graph.getConnectorStops().contains(stop2Mock));
		
		assertEquals(stop1Connections, graph.getStop(stop1ID).getConnections());
		
		assertEquals(2, graph.getStop(stop2ID).getConnections().size());
		assertTrue(graph.getStop(stop2ID).getConnections().contains(stop1ID));
		assertTrue(graph.getStop(stop2ID).getConnections().contains(stop3ID));
		
		assertEquals(stop3Connections, graph.getStop(stop3ID).getConnections());
		
		//test routes
		assertEquals(1, graph.getStop(stop1ID).getRoutes().size());
		assertTrue(graph.getStop(stop1ID).getRoutes().contains(route1ID));

		assertEquals(2, graph.getStop(stop2ID).getRoutes().size());
		assertTrue(graph.getStop(stop2ID).getRoutes().contains(route1ID));
		assertTrue(graph.getStop(stop2ID).getRoutes().contains(route2ID));
		
		assertEquals(1, graph.getStop(stop3ID).getRoutes().size());
		assertTrue(graph.getStop(stop3ID).getRoutes().contains(route2ID));
	}
	
	@Test
	//this test the graph with 4 routes and 5 stops, 
	// ____
	// |   |
	// A-B-C-E
	// |/ 
	// D
	// route 1: A-E
	// route 2: A-D
	// route 3: D-B
	// route 4: C-A
	void testComplexGraphWith4Routes() {
		
		//route 1 setup
		ArrayList<Stop> mockedRoute1 = new ArrayList<Stop>();
		
		Stop route1A = new Stop(stop1ID, stopName);
		Stop route1B = new Stop(stop2ID, stopName);
		Stop route1C = new Stop(stop3ID, stopName);
		Stop route1E = new Stop(stop5ID, stopName);
		
		HashSet<String> stopARoute1Connections = new HashSet<String>();
		stopARoute1Connections.add(stop2ID);
		route1A.initializeConnections(stopARoute1Connections, route1ID);
		
		HashSet<String> stopBRoute1Connections = new HashSet<String>();
		stopBRoute1Connections.add(stop1ID);
		stopBRoute1Connections.add(stop3ID);
		route1B.initializeConnections(stopBRoute1Connections, route1ID);
		
		HashSet<String> stopCRoute1Connections = new HashSet<String>();
		stopCRoute1Connections.add(stop2ID);
		stopCRoute1Connections.add(stop5ID);
		route1C.initializeConnections(stopCRoute1Connections, route1ID);
		
		HashSet<String> stopERoute1Connections = new HashSet<String>();
		stopERoute1Connections.add(stop3ID);
		route1E.initializeConnections(stopERoute1Connections, route1ID);
		
		mockedRoute1.add(route1A);
		mockedRoute1.add(route1B);
		mockedRoute1.add(route1C);
		mockedRoute1.add(route1E);
		
		//route 2 setup
		ArrayList<Stop> mockedRoute2 = new ArrayList<Stop>();
		
		Stop route2A = new Stop(stop1ID, stopName);
		Stop route2D = new Stop(stop4ID, stopName);
		
		HashSet<String> stopARoute2Connections = new HashSet<String>();
		stopARoute2Connections.add(stop4ID);
		route2A.initializeConnections(stopARoute2Connections, route2ID);
		
		HashSet<String> stopDRoute2Connections = new HashSet<String>();
		stopDRoute2Connections.add(stop1ID);
		route2D.initializeConnections(stopDRoute2Connections, route2ID);
		
		mockedRoute2.add(route2A);
		mockedRoute2.add(route2D);
		
		//route 3 setup
		ArrayList<Stop> mockedRoute3 = new ArrayList<Stop>();
		
		Stop route3B = new Stop(stop2ID, stopName);
		Stop route3D = new Stop(stop4ID, stopName);
		
		HashSet<String> stopBRoute3Connections = new HashSet<String>();
		stopBRoute3Connections.add(stop4ID);
		route3B.initializeConnections(stopBRoute3Connections, route3ID);
		
		HashSet<String> stopDRoute3Connections = new HashSet<String>();
		stopDRoute3Connections.add(stop2ID);
		route3D.initializeConnections(stopDRoute3Connections, route3ID);
		
		mockedRoute3.add(route3D);
		mockedRoute3.add(route3B);
		
		//route 4 setup
		ArrayList<Stop> mockedRoute4 = new ArrayList<Stop>();
		
		Stop route4A = new Stop(stop1ID, stopName);
		Stop route4C = new Stop(stop3ID, stopName);
		
		HashSet<String> stopARoute4Connections = new HashSet<String>();
		stopARoute4Connections.add(stop3ID);
		route4A.initializeConnections(stopARoute4Connections, route4ID);
		
		HashSet<String> stopCRoute4Connections = new HashSet<String>();
		stopCRoute4Connections.add(stop1ID);
		route4C.initializeConnections(stopCRoute4Connections, route4ID);
		
		mockedRoute4.add(route4A);
		mockedRoute4.add(route4C);

		StopGraph graph = new StopGraph();
		graph.addRoute(mockedRoute1);
		graph.addRoute(mockedRoute2);
		graph.addRoute(mockedRoute3);
		graph.addRoute(mockedRoute4);
		
		//test connections for stop A
		assertEquals(4, graph.getConnectorStops().size());
		graph.getConnectorStops().forEach(stop -> assertFalse(stop.getID().equals(stop5ID)));
		
		//stop 1 tests
		
			//connections
		assertEquals(3, graph.getStop(stop1ID).getConnections().size());
		assertTrue(graph.getStop(stop1ID).getConnections().contains(stop4ID));
		assertTrue(graph.getStop(stop1ID).getConnections().contains(stop2ID));
		assertTrue(graph.getStop(stop1ID).getConnections().contains(stop3ID));
		
			//routes
		assertEquals(3, graph.getStop(stop1ID).getRoutes().size());
		assertTrue(graph.getStop(stop1ID).getRoutes().contains(route1ID));
		assertTrue(graph.getStop(stop1ID).getRoutes().contains(route2ID));
		assertTrue(graph.getStop(stop1ID).getRoutes().contains(route4ID));
		
		//stop 2 tests
		
			//connections
		assertEquals(3, graph.getStop(stop2ID).getConnections().size());
		assertTrue(graph.getStop(stop2ID).getConnections().contains(stop4ID));
		assertTrue(graph.getStop(stop2ID).getConnections().contains(stop3ID));
		assertTrue(graph.getStop(stop2ID).getConnections().contains(stop1ID));
	
			//routes
		assertEquals(2, graph.getStop(stop2ID).getRoutes().size());
		assertTrue(graph.getStop(stop2ID).getRoutes().contains(route1ID));
		assertTrue(graph.getStop(stop2ID).getRoutes().contains(route3ID));
		

		//stop 3 tests
		
			//connections
		assertEquals(3, graph.getStop(stop3ID).getConnections().size());
		assertTrue(graph.getStop(stop3ID).getConnections().contains(stop1ID));
		assertTrue(graph.getStop(stop3ID).getConnections().contains(stop2ID));
		assertTrue(graph.getStop(stop3ID).getConnections().contains(stop5ID));
	
			//routes
		assertEquals(2, graph.getStop(stop3ID).getRoutes().size());
		assertTrue(graph.getStop(stop3ID).getRoutes().contains(route1ID));
		assertTrue(graph.getStop(stop3ID).getRoutes().contains(route4ID));
		
		//stop 4 tests
		
			//connections
		assertEquals(2, graph.getStop(stop4ID).getConnections().size());
		assertTrue(graph.getStop(stop4ID).getConnections().contains(stop1ID));
		assertTrue(graph.getStop(stop4ID).getConnections().contains(stop2ID));

		//routes
		assertEquals(2, graph.getStop(stop4ID).getRoutes().size());
		assertTrue(graph.getStop(stop4ID).getRoutes().contains(route2ID));
		assertTrue(graph.getStop(stop4ID).getRoutes().contains(route3ID));
		
		//stop 5 tests
		
			//connections
		assertEquals(1, graph.getStop(stop5ID).getConnections().size());
		assertTrue(graph.getStop(stop5ID).getConnections().contains(stop3ID));

			//routes
		assertEquals(1, graph.getStop(stop5ID).getRoutes().size());
		assertTrue(graph.getStop(stop5ID).getRoutes().contains(route1ID));
	}

}
