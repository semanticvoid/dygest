package dygest.text.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the tokenizer that breaks the input into
 * Word tokens.
 * @author anand
 *
 */
public class WordTokenizer extends Tokenizer {

	/* (non-Javadoc)
	 * @see dygest.text.tokenizer.Tokenizer#tokenize(java.lang.String)
	 */
	@Override
	public String[] tokenize(String text) {
		return text.split("[ ,\\t\\n\\r\\f\\.;:\"\'-]+");
	}

	/* (non-Javadoc)
	 * @see dygest.text.tokenizer.Tokenizer#tokenize(java.util.List)
	 */
	@Override
	public String[] tokenize(String[] chunks) {
		String[] tokens = null;
		
		for (String chunk : chunks) {
			
		}
		
		return null;
	}

}
