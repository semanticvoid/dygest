/**
 * 
 */
package dygest.semantic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jsl.measure.jwi.JWIAbstractSemanticMeasure;
import dygest.graph.Graph;
import dygest.text.Word;

/**
 * This class operates on the input words and generates a semantic 
 * concept graphs for those words. 
 * @author anand
 *
 */
public class LexicalChainsGenerator implements IInterpretation {
	
	private Set<Graph> interpretations;
	private List<Word> words;
	
	/**
	 * Constructor - works on the candidate words
	 * @param words	the candidate words
	 * @param semanticMeasure	the metric to be used as a semantic measure
	 */
	public LexicalChainsGenerator(List<Word> words) {
		this.words = words;
		this.interpretations = new HashSet<Graph>();
	}
	
	/**
	 * This function call populates various interpretations for the
	 * given words based on WordNet and the semantic measure. This needs
	 * to be called immediately after the constructor.
	 */
	public void interpret() {
		
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
}
