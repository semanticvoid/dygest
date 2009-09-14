/**
 * 
 */
package dygest.graph.score;

import java.util.List;

import dygest.graph.Edge;
import dygest.graph.Graph;
import dygest.graph.Node;

/**
 * This class represents the graph (interpretation) scoring mechanism.
 * As the name suggests, this is a combination of Syntactic as well as Semantic features.
 * @author anand
 *
 */
public class SynmanticScoring implements IScore {

	/* (non-Javadoc)
	 * @see dygest.graph.score.IScore#score(dygest.graph.Graph)
	 */
	public double score(Graph g) {
		double graphScore = 0;
		
		// Get all edges of the graph
		List<Edge> edges = g.getAllEdges();
		
		// Iterate through all the edges
		// compute the semantic score
		// compute the syntactic score of the nodes that this edge connects
		// combine the syntactic and semantic scores and store it back as the link weight
		for(Edge e : edges) {
			double linkScore = e.getWeight();
			double semanticScore = 1/Math.sqrt(linkScore);
			
			List<Long> nodeIds = e.getNodes();
			double tempScore = Math.log(semanticScore);
			for(Long id : nodeIds) {
				Node node = g.getNode(id.intValue());
				double prob = node.getWord().getProbability();
				tempScore += Math.log(prob);
			}
			
			linkScore = Math.exp(tempScore);
			e.setWeight(linkScore);
			
			graphScore += linkScore;
		}
		
		return graphScore;
	}

}
