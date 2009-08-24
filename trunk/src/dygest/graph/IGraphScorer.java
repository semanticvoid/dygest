/**
 * 
 */
package dygest.graph;

import java.util.List;

/**
 * This is the interface exposed by any graph scorer
 * @author anand
 *
 */
public interface IGraphScorer {

	/**
	 * Function that scores and returns the Graphs
	 * @param graphs	the list of graphs
	 * @return	the scored Graphs sorted by score
	 */
	public List<Graph> scoreGraphs(List<Graph> graphs);

}
