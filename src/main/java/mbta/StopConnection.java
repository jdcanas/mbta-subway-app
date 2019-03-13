package mbta;

import java.util.Collection;
import java.util.LinkedHashSet;

public class StopConnection {
	
	public String start;
	public String end;
	public String routeID;
	
	public StopConnection(String start, String end, String routeID) {
		this.start = start;
		this.end = end;
		this.routeID = routeID;
	}
	
	//A linkedHashSet is used to preserve order and ensure uniqueness
	public static LinkedHashSet<String> getRoutesFromConnections(Collection<StopConnection> path) {
		LinkedHashSet<String> routesTaken = new LinkedHashSet<String>();
		
		for (StopConnection connection: path) {
			routesTaken.add(connection.routeID);
		}
		
		return routesTaken;
	}
	
	@Override
	public String toString() {
		return "StopConnection [start=" + start + ", end=" + end + ", routeID=" + routeID + "]";
	}

	//necessary for HashSet contains method to function properly
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((routeID == null) ? 0 : routeID.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	//necessary for equality check
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StopConnection other = (StopConnection) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (routeID == null) {
			if (other.routeID != null)
				return false;
		} else if (!routeID.equals(other.routeID))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}
