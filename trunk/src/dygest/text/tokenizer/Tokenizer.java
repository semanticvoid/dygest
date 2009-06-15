package dygest.text.tokenizer;

import java.util.List;

/**
 * This class represents the base class for all
 * tokenizers
 * @author anand
 */
public abstract class Tokenizer {

	/**
	 * Tokenize the given input string
	 * @param text	the text to be tokenized
	 * @return the list of tokens generated
	 */
	public abstract String[] tokenize(String text);
	
	/**
	 * Tokenize the given chunks of text
	 * @param chunks	the token chunks that may have
	 *					come from a previous tokenizer
	 * @return the list of tokens generated
	 */
	public abstract String[] tokenize(String[] chunks);
	
}
