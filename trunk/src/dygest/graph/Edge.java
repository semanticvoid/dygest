/**
 * 
 */
package dygest.graph;

import java.util.HashMap;
import java.util.List;

/**
 * This class represents a edge in the Graph
 * @author anand
 *
 */
public class Edge {

	private int id;
	private HashMap<Long, Long> nodeIdMap;
	private double weight = 0;
	
	public Edge(int id, long nodeIdStart, long nodeIdEnd) {
		this.id = id;
		this.nodeIdMap = new HashMap<Long, Long>();
		this.nodeIdMap.put(nodeIdStart, nodeIdEnd);
		this.nodeIdMap.put(nodeIdEnd, nodeIdStart);
	}

	public Edge(int id, long nodeIdStart, long nodeIdEnd, double weight) {
		this.id = id;
		this.nodeIdMap = new HashMap<Long, Long>();
		this.nodeIdMap.put(nodeIdStart, nodeIdEnd);
		this.nodeIdMap.put(nodeIdEnd, nodeIdStart);
		this.weight = weight;
	}

	public int getID() {
		return this.id;
	}
	
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public List<Long> getNodes() {
		return (List<Long>) this.nodeIdMap.values();
	}
	
	/**
	 * Function to return the NodeId of the node on which
	 * this edge terminates (assuming that it originates at the 
	 * given node). This is under the assumption that the edge has no
	 * direction.
	 * @param currentNodeId the node at which this edge originates
	 * @return	the terminating nodeid or -1 otherwise
	 */
	public long getIncidentNodeId(long currentNodeId) {
		if(this.nodeIdMap.containsKey(currentNodeId)) {
				return this.nodeIdMap.get(currentNodeId);
		}
		
		return -1;
	}
}
