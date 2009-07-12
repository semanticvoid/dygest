/**
 * 
 */
package dygest.graph;

import java.util.List;

import dygest.graph.score.IScore;

/**
 * This class implements the scorer for scoring a set of graphs.
 * @author anand
 *
 */
public class GraphScorer implements IGraphScorer {
	
	private IScore scoringFunction;
	
	/**
	 * The constructor
	 * @param scoringFunction	the scoring mechanism implementation
	 */
	public GraphScorer(IScore scoringFunction) {
		this.scoringFunction = scoringFunction;
	}
	
	/**
	 * This function iterates through all the Graphs and scores them
	 * as per the supplied scoring mechanism.
	 */
	public List<Graph> scoreGraphs(List<Graph> graphs) {
		for(Graph g : graphs) {
			double score = this.scoringFunction.score(g);
			g.setScore(score);
		}
		
		java.util.Collections.sort(graphs);
		
		return graphs;
	}
}
