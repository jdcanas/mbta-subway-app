package mbta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

//takes a graph and finds a path using Breadth first search
public class GraphSearcher {
	private StopGraph graph;
	private HashMap<String,StopConnection> predecessorMap;
	
	public GraphSearcher(StopGraph graph) {
		this.graph = graph;
	}
	
	//a breadth first search on the StopGraph
	//this will return an empty path if the start and end stops are the same, and an exception if there is no path between the start and end
	public ArrayList<StopConnection> findPath(String startStopID, String endStopID) throws NoPathFoundException {		
		predecessorMap = new HashMap<String,StopConnection>();
		
		ArrayList<String> visited = new ArrayList<String>();
		LinkedList<String> openSet = new LinkedList<String>();
		
		openSet.add(startStopID);
		
		String currStopID;
		while (openSet.size() > 0) {
			currStopID = openSet.pollFirst();
			
			//if we have found our destination
			if (currStopID.equals(endStopID)) {
				return traverseToStart(startStopID, endStopID);
			}
			
			//for all child nodes
			for (StopConnection connection: graph.getStop(currStopID).getConnections()) {
				//skip any already visited nodes to avoid loops
				if (visited.contains(connection.end)) {
					continue;
				}
				
				//if we have not already seen this node and queued it for processing, track the path to it and queue it
				if (!openSet.contains(connection.end)) {
					predecessorMap.put(connection.end, connection);
					openSet.add(connection.end);
				}
			}
			
			//we have finished processing this node
			visited.add(currStopID);
		}
		
		//if we reach here, we have explored all possible connections and not found our destination
		throw new NoPathFoundException(Constants.EXCEPTION_NO_ROUTE_TO_DEST + startStopID + " to " + endStopID);
	}
	
	//traverses the preDecessor map to determine the path through the graph taken.
	private ArrayList<StopConnection> traverseToStart(String startStop, String endStop) {
		ArrayList<StopConnection> path = new ArrayList<StopConnection>();
		
		String currStop = endStop;
		
		while (predecessorMap.containsKey(currStop)) {
			path.add(predecessorMap.get(currStop));
			currStop = predecessorMap.get(currStop).start;
		}
		
		Collections.reverse(path);
		return path;
	}
	

}
