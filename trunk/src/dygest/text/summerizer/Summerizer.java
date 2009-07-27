package dygest.text.summerizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dygest.datatype.Word;
import dygest.graph.Graph;
import dygest.graph.GraphScorer;
import dygest.graph.score.IScore;
import dygest.html.parser.GaussianParser;
import dygest.semantic.IInterpretation;
import dygest.semantic.LexicalChainsGenerator;
import dygest.text.Document;
import dygest.text.Sentence;
import dygest.text.tagger.wrapper.CandidateKeyAggregator;
import dygest.text.tokenizer.ITokenizer;
import dygest.text.tokenizer.SentenceTokenizer;

public abstract class Summerizer {
	protected Document document = null;
	protected List<Word> candidateKeys = null;
	CandidateKeyAggregator ckAggregator = null;
	
	protected class ScoredSentence {
		private Sentence sentence;
		private double score;
		
		public Sentence getSentence() {
			return sentence;
		}
		
		public double getScore() {
			return score;
		}
		
		public void setSentence(Sentence sentence) {
			this.sentence = sentence;
		}
		
		public void setScore(double score) {
			this.score = score;
		}
	}
	
	protected abstract IScore getScore();
	protected abstract Graph getBestGraph(List<Graph> sortedGraphs);
	protected abstract List<ScoredSentence> scoreSentences(Graph bestGraph, List<Sentence> sentences);
	protected abstract int getSummmaryLength();
	
	protected void initialize() throws IOException, ClassNotFoundException {
		ckAggregator = new CandidateKeyAggregator();
	}
	public List<ScoredSentence> summerize(String url) throws IOException, ClassNotFoundException {
		
		Set<Word> cKeys = new HashSet<Word>();
		String content = getTextContent(url);
		document = new Document(content, true, true);
		List<Sentence> sentences = document.getSentences();
		for(Sentence sentence :sentences) {
			cKeys.addAll(getCandidateKeys(sentence.getText()));
		}
		candidateKeys = new ArrayList<Word>(cKeys);
		List<Graph> graphs = getInterpretations(candidateKeys);
		
		GraphScorer scorer = new GraphScorer(getScore());
		List<Graph> sortedGraphs = scorer.scoreGraphs(graphs);
		Graph bestGraph = getBestGraph(sortedGraphs);
		
		List<ScoredSentence> scoredSentences = scoreSentences(bestGraph, sentences);
		
		return getSummary(scoredSentences);
	}
	
	protected String getTextContent(String url) {
		GaussianParser gparser = new GaussianParser(1.5);
		//TODO : validate the url
		return gparser.parse(url);
	}
	
	protected List<String> getSentences(String content) {
		ITokenizer sentenceTokenizer = new SentenceTokenizer();
		return sentenceTokenizer.tokenize(content);
	}
	
	protected List<Word> getCandidateKeys(String sentence) throws IOException, ClassNotFoundException {
			return ckAggregator.getCandidateKeys(document);
	}
	
	protected List<Graph> getInterpretations(List<Word> cKeys) {
		IInterpretation lexicalChainGen = new LexicalChainsGenerator(cKeys);
		return lexicalChainGen.getAllInterpretations();
	}
	
	@SuppressWarnings("unchecked")
	private List<ScoredSentence> getSummary(List<ScoredSentence> scoredSentences) {
		Comparator comparator = new Comparator<ScoredSentence>() {
			public int compare(ScoredSentence obj1, ScoredSentence obj2) {
				if(obj1.getScore() > obj2.getScore()) {
					return 1;
				} else if(obj1.getScore() < obj2.getScore()) {
					return -1;
				} else {
					return 0;
				}
			}
		};
		Collections.sort(scoredSentences, comparator);
		double summaryLength = (scoredSentences.size() * getSummmaryLength() ) / 100; 
		return scoredSentences.subList(0, (int)summaryLength);
	}
	
}
