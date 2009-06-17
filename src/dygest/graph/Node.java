/**
 * 
 */
package dygest.graph;

/**
 * This class represents a node within the Graph
 * @author anand
 *
 */
public class Node {

	private long id;
	private int inDegree;
	private int outDegree;
	
	// @TODO need to pass the Word object in the constructor
	public Node(int id) {
		this.id = id;
		this.inDegree = 0;
		this.outDegree = 0;
	}

	public int getInDegree() {
		return inDegree;
	}

	public int getOutDegree() {
		return outDegree;
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
