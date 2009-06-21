/**
 * 
 */
package dygest.semantic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dygest.graph.Graph;
import dygest.text.Word;
import dygest.text.wordnet.WordNet;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.Synset;

/**
 * This class operates on the input words and generates a semantic 
 * concept graphs for those words. 
 * @author anand
 *
 */
public class LexicalChainsGenerator implements IInterpretation {
	
	private Set<Graph> interpretations;
	private List<Word> words;
	
	private WordNet wn;
	
	/**
	 * Constructor - works on the candidate words
	 * @param words	the candidate words
	 * @param semanticMeasure	the metric to be used as a semantic measure
	 */
	public LexicalChainsGenerator(List<Word> words) {
		this.words = words;
		this.interpretations = new HashSet<Graph>();
		try {
			this.wn = WordNet.getSingleton();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This function call populates various interpretations for the
	 * given words based on WordNet and the semantic measure. This needs
	 * to be called immediately after the constructor.
	 */
	public void interpret() {
		// Iterate over all the words and retrieve the synsets
		Iterator<Word> wordItr = words.iterator();
		while(wordItr.hasNext()) {
			Word word = wordItr.next();
			List<ISynsetID> synsets = this.wn.getSynsets(word);

			System.out.println("\nWord:\t" + word.word);
			for(ISynsetID id : synsets) {
				System.out.println(id.toString());
			}
		}
	}

	/**
	 * @see dygest.semantic.IInterpretation#getInterpretationAtRank(int)
	 */
	public Graph getInterpretationAtRank(int rank) {
		return null;
	}

	/**
	 * @see dygest.semantic.IInterpretation#getAllInterpretations()
	 */
	public Set<Graph> getAllInterpretations() {
		return this.interpretations;
	}
	
	public static void main(String[] args) {
		ArrayList<Word> words = new ArrayList<Word>();
		
		Word w1 = new Word();
		w1.word = "person";
		Word w2 = new Word();
		w2.word = "machine";
		Word w3 = new Word();
		w3.word = "tv";
		
		words.add(w1);
		words.add(w2);
		words.add(w3);
		
		LexicalChainsGenerator lcg = new LexicalChainsGenerator(words);
		lcg.interpret();
	}
}
