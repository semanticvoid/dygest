/**
 * 
 */
package dygest.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import dygest.graph.score.IScore;

/**
 * This class implements the scorer for scoring a set of graphs.
 * @author anand
 *
 */
public class GraphScorer implements IGraphScorer {
	
	private IScore scoringFunction;
	private List<Graph> graphs;
	
	/**
	 * The constructor
	 * @param scoringFunction	the scoring mechanism implementation
	 */
	public GraphScorer(IScore scoringFunction, List<Graph> graphs) {
		this.scoringFunction = scoringFunction;
		this.graphs = graphs;
	}
	
	/**
	 * This function iterates through all the Graphs and scores them
	 * as per the supplied scoring mechanism.
	 */
	public void scoreGraphs() {
		for(Graph g : this.graphs) {
			double score = this.scoringFunction.score(g);
			g.setScore(score);
		}
		
		java.util.Collections.sort(this.graphs);
	}
	
	/**
	 * @see dygest.graph.IGraphScorer#getGraphAtRank
	 */
	public Graph getGraphAtRank(int rank) {
		if(rank <= 0 || rank > this.graphs.size()) {
			return null;
		}
		
		return this.graphs.get(rank-1);
	}

}
