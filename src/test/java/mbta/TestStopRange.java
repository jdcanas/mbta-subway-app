package mbta;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TestStopRange {
	
	String name1 = "route1";
	String id1 = "id1";
	
	String name2 = "route3";
	String id2 = "id3";
	
	String name3 = "route3";
	String id3 = "id3";
	
	Route route1Mock;
	Route route2Mock;
	Route route3Mock;
	
	@BeforeEach
	void setup() {
		route1Mock = mock(Route.class);
		route2Mock = mock(Route.class);
		route3Mock = mock(Route.class);
	}
	
	@Test
	public void testStopRangeWithNoRoutes() {
		StopRange testRange = new StopRange(new ArrayList<Route>());
		assertNull(testRange.getMaxRoute());
		assertNull(testRange.getMinRoute());
	}
	
	@Test
	public void testStopRangeWithOneRoute() {
		ArrayList<Route> routes = new ArrayList<Route>();
		routes.add(new Route(name1, id1));
		
		StopRange testRange = new StopRange(routes);
		assertEquals(name1, testRange.getMaxRoute().getLongName());
		assertEquals(name1, testRange.getMinRoute().getLongName());
		assertEquals(id1, testRange.getMinRoute().getID());
		assertEquals(id1, testRange.getMaxRoute().getID());
	}
	
	@Test
	public void testStopRangeWithMultipleMockedRoutes() {
		ArrayList<Route> routes = new ArrayList<Route>();
		when(route1Mock.getNumStops()).thenReturn(3);
		when(route2Mock.getNumStops()).thenReturn(2);
		when(route3Mock.getNumStops()).thenReturn(1);
		
		when(route1Mock.getID()).thenReturn(id1);
		when(route3Mock.getID()).thenReturn(id3);
		
		routes.add(route1Mock);
		routes.add(route2Mock);
		routes.add(route3Mock);
		
		StopRange testRange = new StopRange(routes);
		assertEquals(id1, testRange.getMaxRoute().getID());
		assertEquals(id3, testRange.getMinRoute().getID());
	}
}
