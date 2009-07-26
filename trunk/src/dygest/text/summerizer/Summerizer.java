package dygest.text.summerizer;

import java.io.IOException;
import java.util.ArrayList;
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
	private Document document;
	
	protected class ScoredSentence {
		private String sentence;
		private double score;
		
		public String getSentence() {
			return sentence;
		}
		
		public double getScore() {
			return score;
		}
		
		public void setSentence(String sentence) {
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
	
	public List<String> summerize(String url) throws IOException, ClassNotFoundException {
		
		Set<Word> cKeys = new HashSet<Word>();
		String content = getTextContent(url);
		document = new Document(content, true, true);
		List<Sentence> sentences = document.getSentences();
		for(Sentence sentence :sentences) {
			cKeys.addAll(getCandidateKeys(sentence.getText()));
		}
		
		List<Graph> graphs = getInterpretations(new ArrayList<Word>(cKeys));
		
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
			CandidateKeyAggregator ckAggregator = new CandidateKeyAggregator(document);
			return ckAggregator.getCandidateKeys();
	}
	
	protected List<Graph> getInterpretations(List<Word> cKeys) {
		IInterpretation lexicalChainGen = new LexicalChainsGenerator(cKeys);
		return lexicalChainGen.getAllInterpretations();
	}
	
	private List<String> getSummary(List<ScoredSentence> scoredSentences) {
		return null;
	}
	
}
