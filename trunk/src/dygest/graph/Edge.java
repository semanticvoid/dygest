/**
 * 
 */
package dygest.graph;

import java.util.HashMap;

/**
 * This class represents a edge in the Graph
 * @author anand
 *
 */
public class Edge {

	private HashMap<Long, Long> nodeIdMap;
	private double weight = 0;
	
	public Edge(long nodeIdStart, long nodeIdEnd) {
		this.nodeIdMap = new HashMap<Long, Long>();
		this.nodeIdMap.put(nodeIdStart, nodeIdEnd);
		this.nodeIdMap.put(nodeIdEnd, nodeIdStart);
	}

	public Edge(long nodeIdStart, long nodeIdEnd, double weight) {
		this.nodeIdMap = new HashMap<Long, Long>();
		this.nodeIdMap.put(nodeIdStart, nodeIdEnd);
		this.nodeIdMap.put(nodeIdEnd, nodeIdStart);
		this.weight = weight;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
