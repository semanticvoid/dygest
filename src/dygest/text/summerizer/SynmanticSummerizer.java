package dygest.text.summerizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dygest.graph.Graph;
import dygest.graph.Node;
import dygest.graph.score.IScore;
import dygest.graph.score.SynmanticScoring;
import dygest.text.Sentence;

public class SynmanticSummerizer extends Summerizer{
	
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
			ScoredSentence scoredSentence = new ScoredSentence();
			double score = 0.0;
			for(Node node : nodes) {
				if(sentence.getText().trim().contains(node.getWord().getName())) {
					score = score + node.getWeight();
				}				
			}
			scoredSentence.setScore(score);
			scoredSentence.setSentence(sentence);
			scoredSentences.add(scoredSentence);
		}
		return scoredSentences;
	}

	//should return the top percentage of the sentences.
	protected int getSummmaryLength() {
		return 30;
	}

}
