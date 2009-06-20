/**
 * 
 */
package dygest.text.wordnet;

import java.net.URL;
import java.util.List;

import dygest.text.Word;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.ISynsetID;


/**
 * This class is the local wrapper over the WordNet library (so that
 * other classes are free from specific implementation details of the
 * library).
 * This is a singleton class.
 * @author anand
 *
 */
public class WordNet {

	private WordNet wn = null;
	private IDictionary dict = null;
	
	/**
	 * Private constructor (for singleton)
	 */
	private WordNet() throws Exception {
		// @TODO move this path to the Environment variable WNHOME
		URL url = new URL("file", null, "/usr/local/WordNet-3.0/dict/");
		this.dict = new Dictionary(url);
		this.dict.open();
	}
	
	/**
	 * Method that returns the singleton WordNet obj
	 * @return the singleton object
	 */
	public WordNet getSingleton() throws Exception {
		if(this.wn == null) {
			this.wn = new WordNet();
		}
		
		return this.wn;
	}
	
	/**
	 * 
	 * @param word
	 * @return
	 */
	public List<ISynsetID> getHypernyms(Word word) {
		return null;
	}
}
