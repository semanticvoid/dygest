/**
 * 
 */
package dygest.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import dygest.datatype.Word;


/**
 * This class represents the Graph data structure.
 * @author anand
 *
 */
public class Graph implements Comparable<Graph> {
	private static final double D = 0.85;
	private long id;
	private int nodeCount = 0;
	private int edgeCount = 0;
	private HashMap<Integer, Node> nodes;
	private HashMap<Integer, Edge> edges;
	private double score = 0;
	
	public Graph() {
		// generate a unique id for this graph
		// currently the number of milliseconds since epoch
		this.id = new Date().getTime();
		this.nodes = new HashMap<Integer, Node>();
		this.edges = new HashMap<Integer, Edge>();
	}
	
	/**
	 * Create a node in the graph representing the Word
	 * @param w	the Word obj
	 * @return	the Node obj on success, null otherwise
	 */
	public Node createNode(Word w) {
		Node node = new Node(++this.nodeCount, w);
		if(addNode(node)) {
			return node;
		}
		
		return null;
	}
	
	/**
	 * Create an edge between two Nodes
	 * @param node1	the first Node
	 * @param node2	the second Node
	 * @param weight	the weight of the edge
	 * @return	true on success, false otherwise
	 */
	public boolean createLink(Node node1, Node node2, double weight) {
		Edge e = new Edge(++this.edgeCount, node1.getID(), node2.getID(), weight);
		node1.addEdge(e);
		node2.addEdge(e);
		return addLink(e);
	}
	
	/**
	 * Add node to set of nodes
	 * @param node	the Node obj
	 * @return	true on success, false otherwise
	 */
	private boolean addNode(Node node) {
		if(!this.nodes.containsKey(node.getID())) {
			this.nodes.put(node.getID(), node);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Add edge to set of edges
	 * @param e	the Edge obj
	 * @return	true on success, false otherwise
	 */
	private boolean addLink(Edge e) {
		if(!this.edges.containsKey(e.getID())) {
			this.edges.put(e.getID(), e);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Compute node weights (based on PageRank)
	 */
	public void computeNodeWeights() {
		HashMap<Integer, Double> nodeWeights = new HashMap<Integer, Double>();
		
		List<Node> nodes = this.getAllNodes();
		
		// init the scores (random as of now)
		for(Node n : nodes) {
			nodeWeights.put(n.getID(), Math.random());
		}
		
		// iterate over all nodes until the difference is negligible
		double diff = 10;
		// TODO choose some threshold here for accuracy desired
		while(diff >= 1) {
			HashMap<Integer, Double> nodeWeightsTemp = new HashMap<Integer, Double>();
			diff  = 0;
			
			for(Node n : nodes) {
				int nodeId = n.getID();
				double n_score = 0;
				
				// get all edges for node
				List<Edge> edges = n.getEdges();
				for(Edge e : edges) {
					// get node at the other end of the edge
					long neighborNodeId = e.getIncidentNodeId(nodeId);
					// neighbor node
					Node neighborNode = this.nodes.get(neighborNodeId);
					// get outdegree of neighbor node
					int outdegree = neighborNode.getOutDegree();
					// update n_score
					// TODO need to take the edge weight into account
					n_score += (nodeWeights.get(neighborNodeId)/outdegree);
				}
				
				double nodeWeight = (1-D) + D*(n_score);
				nodeWeightsTemp.put(nodeId, nodeWeight);
				n.setWeight(nodeWeight);
				diff += Math.abs(nodeWeights.get(nodeId) - nodeWeight);
			}
			
			nodeWeights = nodeWeightsTemp;
			// normalize difference
			diff = (diff/nodes.size());
		}
	}
	
	/**
	 * Set the score of this graph
	 * @param score	the score
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	/**
	 * Get the score
	 * @return	the score of this graph
	 */
	public double getScore() {
		return this.score;
	}

	/**
	 * Get the list of all nodes of this graph
	 * @return	the list of all nodes
	 */
	public List<Node> getAllNodes() {
		List<Node> nodes = new ArrayList<Node>();
		
		for(Node n : this.nodes.values()) {
			nodes.add(n);
		}
		
		return nodes;
	}
	
	/**
	 * Get the list of all edges
	 * @return	the list of edges
	 */
	public List<Edge> getAllEdges() {
		List<Edge> edges = new ArrayList<Edge>();
		
		for(Edge e : this.edges.values()) {
			edges.add(e);
		}
		
		return edges;
	}
	
	/**
	 * Get the node identified by the nodeId
	 * @param nodeId	the ID of the node
	 * @return	the Node object or null
	 */
	public Node getNode(int nodeId) {
		if(this.nodes.containsKey(nodeId)) {
			return this.nodes.get(nodeId);
		}
		
		return null;
	}
	
	/**
	 * Function to clone this graph
	 */
	@SuppressWarnings("unchecked")
	public Graph clone() {
		Graph clone = new Graph();
		clone.edgeCount = this.edgeCount;
		clone.edges = (HashMap<Integer, Edge>) this.edges.clone();
		clone.nodeCount = this.nodeCount;
		clone.nodes = (HashMap<Integer, Node>) this.nodes.clone();
		
		return clone;
	}
	
	/**
	 * The comparison function
	 */
	public int compareTo(Graph o) {
		if(this.score > o.getScore()) {
			return -1;
		} else if(this.score < o.getScore()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * Function to convert this Graph into
	 * a DOT language representation.
	 * @return the DOT langauge representation
	 */
	public String toDOT() {
		StringBuffer dot = new StringBuffer();
		
		dot.append("graph ");
		dot.append(this.id);
		dot.append(" {\n");
		
		dot.append("	node [shape=ellipse, style=filled];\n\n");
		
		// edges
		Iterator<Edge> itr = this.edges.values().iterator();
		while(itr.hasNext()) {
			Edge e = itr.next();
			List<Long> nodes = e.getNodes();
			Node node1 = this.nodes.get(nodes.get(0).intValue());
			Node node2 = this.nodes.get(nodes.get(1).intValue());
			dot.append("	\""  + node1.getWord().getName() + "\" -- \"" 
					+ node2.getWord().getName() + "\" [label=" + e.getWeight() + 
					", style=\"setlinewidth(" + Math.sqrt(e.getWeight()) + ")\"];\n");
		}
		
		dot.append("\n");
		dot.append("}");
		return dot.toString();
	}
}
