package mbta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class GraphSearcher {
	StopGraph graph;
	
	HashMap<String,String> predecessorMap;
	
	public GraphSearcher(StopGraph graph) {
		this.graph = graph;
	}
	
	//a breadth first search
	public ArrayList<Stop> findPath(String startStopID, String endStopID) throws NoPathFoundException {		
		predecessorMap = new HashMap<String,String>();
		
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
			for (String stopID: graph.getStop(currStopID).getConnections()) {
				//skip any already visited nodes to avoid loops
				if (visited.contains(stopID)) {
					continue;
				}
				
				//if we have not already seen this node and queued it for processing, track the path to it and queue it
				if (!openSet.contains(stopID)) {
					predecessorMap.put(stopID, currStopID);
					openSet.add(stopID);
				}
			}
			
			//we have finished processing this node
			visited.add(currStopID);
		}
		
		throw new NoPathFoundException("Unable to route from " + startStopID + " to " + endStopID);
	}
	
	private ArrayList<Stop> traverseToStart(String startStop, String endStop) {
		ArrayList<Stop> path = new ArrayList<Stop>();
		
		String currStop = endStop;
		
		while (currStop != startStop) {
			path.add(graph.getStop(currStop));
			currStop = predecessorMap.get(currStop);
		}
		
		Collections.reverse(path);
		return path;
	}
	

}
