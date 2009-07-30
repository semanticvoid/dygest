/**
 * 
 */
package dygest.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dygest.text.analyzer.StopWordAnalyzer;
import dygest.text.tokenizer.SentenceTokenizer;
import dygest.text.tokenizer.WordTokenizer;

/**
 * This class represents the Document object
 * representing a chunk of text.
 * @author anand
 *
 */
public class Document {

	/**
	 * This class represents a sentence object.
	 * @author anand
	 *
	 */	
	
	// the list of Sentences in the Document
	private List<Sentence> sentences;
	// the term vector
	private HashMap<String, Integer> termVector = null;
	// the inverted index
	private HashMap<String, List<Position>> invertedIndex = null;
	
	//TODO:compute bigram and trigram frequencies
	private HashMap<String, Integer> bigramFrequency = null;
	
	private HashMap<String, Integer> trigramFrequency = null;
	
	// the total number of word occurences in term vector
	// will be used for calculating unigram probability
	private double numOfTerms = 0;
	
	/**
	 * Constructor
	 * @param text	the text this Document represents
	 * @param formTermVector	flag to turn term vector formation on/off
	 * @param formInvertedIndex	flag to turn inverted index formation on/off
	 */
	public Document(String text, boolean formTermVector, boolean formInvertedIndex) {
		this.sentences = new ArrayList<Sentence>();
		
		if(formTermVector) {
			this.termVector = new HashMap<String, Integer>();
		}
		
		if(formInvertedIndex) {
			this.invertedIndex = new HashMap<String, List<Position>>();
		}
		
		generateRepresentation(text, formTermVector, formInvertedIndex);
	}
	
	private void generateRepresentation(String text, boolean formTermVector, boolean formInvertedIndex) {
		SentenceTokenizer stok = new SentenceTokenizer();
		WordTokenizer wtok = new WordTokenizer();
		
		// tokenize into sentences
		List<String> sentences = stok.tokenize(text);
		int sIndex = 0;
		for(String sentence : sentences) {
			// tokenize every sentence into words
			List<String> tokens = wtok.tokenize(sentence);
			// store sentence + words
			// TODO need to process tokens through analyzer and pass them as pTokens
			List<String> ptokens = new ArrayList<String>();
			for(String token : tokens) {
				// LOWERCASE - to be replaced by Lowercase Tokenizer
				String ptoken = token.toLowerCase();
				// STOPWORD check - to be replaced by Stopword Tokenizer
				if(StopWordAnalyzer.STOP_WORD_MAP.contains(ptoken)) {
					ptoken = null;
				}
				
				ptokens.add(ptoken);
			}
			
			this.sentences.add(new Sentence(sentence, tokens, ptokens));
			
			if(formTermVector || formInvertedIndex) {
				// iterate through the tokens to form term vector
				int wIndex = 0;
				for(String token : ptokens) {
					if(token != null) {
						if(formTermVector) {
							int count = 0;
							if(this.termVector.containsKey(token)) {
								count = this.termVector.get(token);
							}
							
							this.termVector.put(token, ++count);
						}
						
						if(formInvertedIndex) {
							List<Position> positions;
							if(this.invertedIndex.containsKey(token)) {
								positions = this.invertedIndex.get(token);
							} else {
								positions = new ArrayList<Position>();
							}
							
							positions.add(new Position(sIndex, wIndex));
							this.invertedIndex.put(token, positions);
						}
						
						wIndex++;
						this.numOfTerms++;
					}
				}
			}
			
			sIndex++;
		}
	}
	
	public double getUnigramProbability(String term) {
		double prob = 0.0;
		if(this.termVector != null && this.termVector.containsKey(term)) {
			int freq = this.termVector.get(term);
			prob = ((double)freq/(double)this.numOfTerms);
		}
		
		return prob;
	}
	
	public double getFrequency(String term) {
		double freq = 0.0;
		if(this.termVector != null && this.termVector.containsKey(term)) {
			freq = this.termVector.get(term);
		}
		return freq;
	}
	
	public List<Sentence> getSentences() {
		return this.sentences;
	}
}
