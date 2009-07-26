/**
 * 
 */
package dygest.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dygest.datatype.Word;


/**
 * This class represents a node within the Graph
 * @author anand
 *
 */
public class Node {

	private int id;
	private Word word;
	private int inDegree;
	private int outDegree;
	private double weight;

	private Set<Edge> edges;
	
	public Node(int id, Word word) {
		this.id = id;
		this.inDegree = 0;
		this.outDegree = 0;
		this.word = word;
		this.edges = new HashSet<Edge>();
	}

	public boolean addEdge(Edge e) {
		this.outDegree++;
		return this.edges.add(e);
	}
	
	public int getID() {
		return this.id;
	}
	
	public Word getWord() {
		return this.word;
	}
	
	public int getInDegree() {
		return inDegree;
	}

	public int getOutDegree() {
		return outDegree;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public List<Edge> getEdges() {
		List<Edge> edges = new ArrayList<Edge>();

		for(Edge e : this.edges) {
			edges.add(e);
		}
		
		return edges;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public void incrInDegree() {
		this.inDegree++;
	}
	
	public void incrOutDegree() {
		this.outDegree++;
	}
	
	public void decrInDegree() {
		this.inDegree--;
	}
	
	public void decrOutDegree() {
		this.outDegree--;
	}
}
