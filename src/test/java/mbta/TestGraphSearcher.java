package mbta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestGraphSearcher {

	StopGraph graph;
	
	String stop1ID = "stop1";
	String stop2ID = "stop2";
	String stop3ID = "stop3";
	String stop4ID = "stop4";
	String stop5ID = "stop5";
	String stopName = "name";
	
	String strandedStopID = "strandedStop";
	String strandedRouteID = "strandedRoute";
	
	String route1ID = "route1";
	String route2ID = "route2";
	String route3ID = "route3";
	String route4ID = "route4";
	
	@BeforeEach
	void setup() {
		//this tests a graph with 4 routes and 5 stops, 
		// ____
		// |   |
		// A-B-C-E
		// |/ 
		// D
		// route 1: A-E
		// route 2: A-D
		// route 3: D-B
		// route 4: C-A
			
		//route 1 setup
		ArrayList<Stop> mockedRoute1 = new ArrayList<Stop>();
		
		Stop route1A = new Stop(stop1ID, stopName);
		Stop route1B = new Stop(stop2ID, stopName);
		Stop route1C = new Stop(stop3ID, stopName);
		Stop route1E = new Stop(stop5ID, stopName);
		
		HashSet<StopConnection> stopARoute1Connections = new HashSet<StopConnection>();
		stopARoute1Connections.add(new StopConnection(stop1ID, stop2ID, route1ID));
		route1A.initializeConnections(stopARoute1Connections, route1ID);
		
		HashSet<StopConnection> stopBRoute1Connections = new HashSet<StopConnection>();
		stopBRoute1Connections.add(new StopConnection(stop2ID, stop1ID, route1ID));
		stopBRoute1Connections.add(new StopConnection(stop2ID, stop3ID, route1ID));
		route1B.initializeConnections(stopBRoute1Connections, route1ID);
		
		HashSet<StopConnection> stopCRoute1Connections = new HashSet<StopConnection>();
		stopCRoute1Connections.add(new StopConnection(stop3ID, stop2ID, route1ID));
		stopCRoute1Connections.add(new StopConnection(stop3ID, stop5ID, route1ID));
		route1C.initializeConnections(stopCRoute1Connections, route1ID);
		
		HashSet<StopConnection> stopERoute1Connections = new HashSet<StopConnection>();
		stopERoute1Connections.add(new StopConnection(stop5ID, stop3ID, route1ID));
		route1E.initializeConnections(stopERoute1Connections, route1ID);
		
		mockedRoute1.add(route1A);
		mockedRoute1.add(route1B);
		mockedRoute1.add(route1C);
		mockedRoute1.add(route1E);
		
		//route 2 setup
		ArrayList<Stop> mockedRoute2 = new ArrayList<Stop>();
		
		Stop route2A = new Stop(stop1ID, stopName);
		Stop route2D = new Stop(stop4ID, stopName);
		
		HashSet<StopConnection> stopARoute2Connections = new HashSet<StopConnection>();
		stopARoute2Connections.add(new StopConnection(stop1ID, stop4ID, route2ID));
		route2A.initializeConnections(stopARoute2Connections, route2ID);
		
		HashSet<StopConnection> stopDRoute2Connections = new HashSet<StopConnection>();
		stopDRoute2Connections.add(new StopConnection(stop4ID, stop1ID, route2ID));
		route2D.initializeConnections(stopDRoute2Connections, route2ID);
		
		mockedRoute2.add(route2A);
		mockedRoute2.add(route2D);
		
		//route 3 setup
		ArrayList<Stop> mockedRoute3 = new ArrayList<Stop>();
		
		Stop route3B = new Stop(stop2ID, stopName);
		Stop route3D = new Stop(stop4ID, stopName);
		
		HashSet<StopConnection> stopBRoute3Connections = new HashSet<StopConnection>();
		stopBRoute3Connections.add(new StopConnection(stop2ID, stop4ID, route3ID));
		route3B.initializeConnections(stopBRoute3Connections, route3ID);
		
		HashSet<StopConnection> stopDRoute3Connections = new HashSet<StopConnection>();
		stopDRoute3Connections.add(new StopConnection(stop4ID, stop2ID, route3ID));
		route3D.initializeConnections(stopDRoute3Connections, route3ID);
		
		mockedRoute3.add(route3D);
		mockedRoute3.add(route3B);
		
		//route 4 setup
		ArrayList<Stop> mockedRoute4 = new ArrayList<Stop>();
		
		Stop route4A = new Stop(stop1ID, stopName);
		Stop route4C = new Stop(stop3ID, stopName);
		
		HashSet<StopConnection> stopARoute4Connections = new HashSet<StopConnection>();
		stopARoute4Connections.add(new StopConnection(stop1ID, stop3ID, route4ID));
		route4A.initializeConnections(stopARoute4Connections, route4ID);
		
		HashSet<StopConnection> stopCRoute4Connections = new HashSet<StopConnection>();
		stopCRoute4Connections.add(new StopConnection(stop3ID, stop1ID, route4ID));
		route4C.initializeConnections(stopCRoute4Connections, route4ID);
		
		mockedRoute4.add(route4A);
		mockedRoute4.add(route4C);

		graph = new StopGraph();
		graph.addRoute(mockedRoute1);
		graph.addRoute(mockedRoute2);
		graph.addRoute(mockedRoute3);
		graph.addRoute(mockedRoute4);
		
	}

	@Test
	void testAtoEPath() throws NoPathFoundException {
		GraphSearcher searcher = new GraphSearcher(graph);
		ArrayList<StopConnection> path = searcher.findPath(stop1ID, stop5ID);
		
		assertEquals(2, path.size());
		assertEquals(stop1ID,path.get(0).start);
		assertEquals(stop3ID,path.get(0).end);
		assertEquals(stop3ID,path.get(1).start);
		assertEquals(stop5ID,path.get(1).end);
		assertEquals(route4ID,path.get(0).routeID);
		assertEquals(route1ID,path.get(1).routeID);
	}
	
	@Test
	void testAtoAPath() throws NoPathFoundException {
		GraphSearcher searcher = new GraphSearcher(graph);
		ArrayList<StopConnection> path = searcher.findPath(stop1ID, stop1ID);
		
		assertEquals(0, path.size());
	}
	
	@Test
	void testDtoEPath() throws NoPathFoundException {
		GraphSearcher searcher = new GraphSearcher(graph);
		ArrayList<StopConnection> path = searcher.findPath(stop4ID, stop5ID);
		
		assertEquals(3, path.size());
		//we can only verify the start and end here because there are variable paths of length 3, so the implementation may cause the exact path to change
		assertEquals(stop4ID,path.get(0).start);
		assertEquals(stop5ID,path.get(2).end);
	}
	
	@Test
	void testAtoBPath() throws NoPathFoundException {
		GraphSearcher searcher = new GraphSearcher(graph);
		ArrayList<StopConnection> path = searcher.findPath(stop1ID, stop2ID);
		
		assertEquals(1, path.size());
		assertEquals(stop1ID, path.get(0).start);
		assertEquals(stop2ID, path.get(0).end);
	}
	
	@Test
	void testPathWithNonexistentSourceException() {
		GraphSearcher searcher = new GraphSearcher(graph);
		
		NoPathFoundException error = assertThrows(NoPathFoundException.class, 
					() -> searcher.findPath("nonExistentID", stop1ID));
		
		assertEquals(Constants.EXCEPTIONS_NO_STOP, error.getMessage());
	}
	
	@Test
	void testPathWithNonexistentDestException() {
		GraphSearcher searcher = new GraphSearcher(graph);
		
		NoPathFoundException error = assertThrows(NoPathFoundException.class, 
				() -> searcher.findPath(stop1ID, "nonExistentID"));
	
		assertTrue(error.getMessage().contains(Constants.EXCEPTION_NO_ROUTE_TO_DEST));
	}
	
	@Test
	void testPathWithStrandedDestException() {
		ArrayList<Stop> strandedStops = new ArrayList<Stop>();
		strandedStops.add(new Stop(strandedStopID, stopName));
		graph.addRoute(strandedStops);
		
		GraphSearcher searcher = new GraphSearcher(graph);
		
		NoPathFoundException error = assertThrows(NoPathFoundException.class, 
				() -> searcher.findPath(stop1ID, strandedStopID));
	
		assertTrue(error.getMessage().contains(Constants.EXCEPTION_NO_ROUTE_TO_DEST));
	}


}
