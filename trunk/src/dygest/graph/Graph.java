/**
 * 
 */
package dygest.graph;

/**
 * This class represents the Graph data structure.
 * @author anand
 *
 */
public class Graph implements Comparable<Graph> {
	
	private double score = 0;
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public double getScore() {
		return this.score;
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
