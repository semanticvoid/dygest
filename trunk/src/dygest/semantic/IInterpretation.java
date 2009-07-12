/**
 * 
 */
package dygest.semantic;

import java.util.List;

import dygest.graph.Graph;

/**
 * This is the interface for any class that
 * deals with generating semantic graphs from words.
 * @author anand
 *
 */
public interface IInterpretation {

	/**
	 * Function to return the best interpretation at the given
	 * rank.
	 * @param rank the rank
	 * @return	the best interpretation Graph
	 */
	public Graph getInterpretationAtRank(int rank);
	
	/**
	 * Function to return all interpretations
	 * @return	the Set of all interpretations
	 */
	public List<Graph> getAllInterpretations();
	
}
