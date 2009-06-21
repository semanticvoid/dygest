/**
 * 
 */
package dygest.text.wordnet;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jsl.measure.jwi.LinSimilarity;

import dygest.text.Word;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.SynsetID;


/**
 * This class is the local wrapper over the WordNet library (so that
 * other classes are free from specific implementation details of the
 * library).
 * This is a singleton class.
 * @author anand
 *
 */
public class WordNet {

	private static WordNet wn = null;
	private IDictionary dict = null;
	private Dictionary dictionary = null;
	
	/**
	 * Private constructor (for singleton)
	 */
	private WordNet() throws Exception {
		// @TODO move this path to the Environment variable WNHOME
		URL url = new URL("file", null, "/usr/local/WordNet-3.0/dict/");
		this.dictionary = new Dictionary(url);
		this.dict = dictionary;
		this.dict.open();
	}
	
	/**
	 * Method that returns the singleton WordNet obj
	 * @return the singleton object
	 */
	public static WordNet getSingleton() throws Exception {
		if(wn == null) {
			wn = new WordNet();
		}
		
		return wn;
	}
	
	/**
	 * Function to return the related synsets for the
	 * given word.
	 * @param word	the word obj
	 * @return	the List of synset id's
	 */
	public List<ISynsetID> getSynsets(Word word) {
		ArrayList<ISynsetID> synsetids = new ArrayList<ISynsetID>();
		
		// @TODO to work on this after sudheer is done with Word obj
		// right now assume NOUN
		IIndexWord idxWord = this.dict.getIndexWord(word.word, POS.NOUN);
		List<IWordID> wordIDs = idxWord.getWordIDs();
		for(IWordID wid : wordIDs) {
			IWord w = dict.getWord(wid);
			ISynset sysnet = w.getSynset();
			synsetids.add(sysnet.getID());
		}
		
		return synsetids;
	}
	
	public double getLinSimilarity(SynsetID syid1, SynsetID syid2) {
		LinSimilarity sim = new LinSimilarity(this.dictionary);
		double score = sim.calculateSimilarity(syid1, syid2);
		return score;
	}
}
