package dygest.text.summerizer;

import java.util.List;

import dygest.graph.Graph;
import dygest.graph.score.IScore;
import dygest.graph.score.SynmanticScoring;
import dygest.text.Sentence;

public class SynmanticSummerizer extends Summerizer{
	
	//should be abstract
	protected IScore getScore() {
		return new SynmanticScoring();
	}
	
	//should be abstract
	protected Graph getBestGraph(List<Graph> sortedGraphs) {
		return sortedGraphs.get(0);
	}
	
	//should be abstract
	protected List<ScoredSentence> scoreSentences(Graph bestGraph, List<Sentence> sentences) {
		return null;
	}

	//should be abstract
	//should return the top percentage of the sentences.
	protected int getSummmaryLength() {
		return 0;
	}

}
