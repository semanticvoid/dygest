/**
 * 
 */
package dygest.graph;

/**
 * This is the interface exposed by any graph scorer
 * @author anand
 *
 */
public interface IGraphScorer {

	/**
	 * Function that returns the Graph object at provided rank
	 * after scoring against other graphs.
	 * @param rank	the rank for which the Graph is required
	 * @return	Graph at rank or null otherwise
	 */
	public Graph getGraphAtRank(int rank);
	
}
