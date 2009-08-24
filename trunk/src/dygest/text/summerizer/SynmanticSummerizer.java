package dygest.text.summerizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dygest.graph.Graph;
import dygest.graph.Node;
import dygest.graph.score.IScore;
import dygest.graph.score.SynmanticScoring;
import dygest.text.ScoredSentence;
import dygest.text.Sentence;

public class SynmanticSummerizer extends Summerizer{
	
	public SynmanticSummerizer() throws IOException, ClassNotFoundException {
		initialize();
	}
	
	protected IScore getScore() {
		return new SynmanticScoring();
	}
	
	protected Graph getBestGraph(List<Graph> sortedGraphs) {
		return sortedGraphs.get(0);
	}
	
	protected List<ScoredSentence> scoreSentences(Graph bestGraph, List<Sentence> sentences) {
		List<ScoredSentence> scoredSentences = new ArrayList<ScoredSentence>();
		List<Node> nodes = bestGraph.getAllNodes();
		Map<String, Node> nodesMap = new HashMap<String, Node>();
		for(Node node : nodes) {
			nodesMap.put(node.getWord().getName(), node);
		}
		for(Sentence sentence : sentences) {
			double score = 0.0;
			for(Node node : nodes) {
				if(sentence.getText().trim().contains(node.getWord().getName())) {
					// TODO move sentence scoring mechanism into a separate class
					score = score + (node.getWeight() * node.getWord().getWordFrequency());
				}				
			}
			scoredSentences.add(new ScoredSentence(sentence, score));
		}
		return scoredSentences;
	}

	//should return the top percentage of the sentences.
	protected int getSummmaryLength() {
		return 30;
	}

}
