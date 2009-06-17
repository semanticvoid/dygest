/**
 * 
 */
package dygest.graph.score;

import dygest.graph.Graph;

/**
 * This is the interface for all Graph score calculators
 * @author anand
 *
 */
public interface IScore {

	/**
	 * This function scores the given graph
	 * @param g	the Graph obj
	 * @return	a score between 0 and 1
	 */
	public double score(Graph g);
	
}
