/**
 * 
 */
package dygest.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dygest.datatype.Word;


/**
 * This class represents the Graph data structure.
 * @author anand
 *
 */
public class Graph implements Comparable<Graph> {
	
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
	 * Set the score of this graph
	 * @param score	the score
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	/**
	 * Get the score
	 * @return	the score of this score
	 */
	public double getScore() {
		return this.score;
	}

	/**
	 * Get the list of all nodes of this graph
	 * @return	the list of all nodes
	 */
	public List<Node> getAllNodes() {
		return (List<Node>) this.nodes.values();
	}
	
	/**
	 * Get the list of all edges
	 * @return	the list of edges
	 */
	public List<Edge> getAllEdges() {
		return (List<Edge>) this.edges.values();
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
		if(this.score < o.getScore()) {
			return -1;
		} else if(this.score > o.getScore()) {
			return 1;
		} else {
			return 0;
		}
	}
}
