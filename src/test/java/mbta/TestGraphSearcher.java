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

		graph = new StopGraph();
		graph.addRoute(mockedRoute1);
		graph.addRoute(mockedRoute2);
		graph.addRoute(mockedRoute3);
		graph.addRoute(mockedRoute4);
	}

	@Test
	void testAtoEPath() throws NoPathFoundException {
		GraphSearcher searcher = new GraphSearcher(graph);
		ArrayList<Stop> path = searcher.findPath(stop1ID, stop5ID);
		
		assertEquals(4, path.size());
		assertEquals(stop1ID,path.get(0).getID());
		assertEquals(stop3ID,path.get(1).getID());
		assertEquals(stop5ID,path.get(2).getID());
	}
	
	@Test
	void testAtoAPath() throws NoPathFoundException {
		GraphSearcher searcher = new GraphSearcher(graph);
		ArrayList<Stop> path = searcher.findPath(stop1ID, stop1ID);
		
		assertEquals(1, path.size());
	}

}
