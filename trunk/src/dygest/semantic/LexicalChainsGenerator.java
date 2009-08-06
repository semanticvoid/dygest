/**
 * 
 */
package dygest.semantic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import dygest.datatype.Word;
import dygest.graph.Graph;
import dygest.graph.Node;
import dygest.text.wordnet.WordNet;
import edu.mit.jwi.item.ISynsetID;

/**
 * This class operates on the input words and generates a semantic 
 * concept graphs for those words. 
 * @author anand
 *
 */
public class LexicalChainsGenerator implements IInterpretation {
	
	private List<Graph> interpretations;
	private List<Word> words;
	
	private WordNet wn;
	
	/**
	 * Constructor - works on the candidate words
	 * @param words	the candidate words
	 * @param semanticMeasure	the metric to be used as a semantic measure
	 */
	public LexicalChainsGenerator(List<Word> words) {
		this.words = words;
		this.interpretations = new ArrayList<Graph>();
		this.wn = WordNet.getSingleton();
		interpret();
	}
	
	/**
	 * This function call populates various interpretations for the
	 * given words based on WordNet and the semantic measure. This needs
	 * to be called immediately after the constructor.
	 */
	private void interpret() {
		// just one graph - source of all truth
		Graph g = new Graph();
		
		// Iterate over all the words and retrieve the synsets
		Iterator<Word> wordItr = words.iterator();
		while(wordItr.hasNext()) {
			Word word = wordItr.next();
			List<ISynsetID> synsets = this.wn.getSynsets(word);

			// Add each 
			for(ISynsetID id : synsets) {
				// set the synsetid of the word
				word.setSense(id);
				
				// create links between this node
				// and other nodes in the graph
				// then store this graph into tier 2
				Node wNode = g.createNode(word);
						
				List<Node> nodes = g.getAllNodes();
				for(Node node : nodes) {
					Word wordInGraph = node.getWord();
					double sim = this.wn.getSimilarity(id, wordInGraph.getSense());
					// normalizing the semantic weight
					sim = 1/Math.sqrt(sim);
					// if similarity score > 0 then create a link
					// otherwise don't bother
					if(sim > 0) {
						if(g.createLink(node, wNode, sim)) {
							// TODO print debug msg here
							//System.out.println("sim:\t" + sim);
						}
					}
				}
			}
		}
			
		// complete graph creation
		g.komplett();
		
		// add source of all truth to interpretations list
		interpretations.add(g);
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
	public List<Graph> getAllInterpretations() {
		return this.interpretations;
	}
	
	public static void main(String[] args) {
		ArrayList<Word> words = new ArrayList<Word>();
		
		Word w1 = new Word("person");
		Word w2 = new Word("machine");
		Word w3 = new Word("tv");

		words.add(w1);
		words.add(w2);
		words.add(w3);
		
		LexicalChainsGenerator lcg = new LexicalChainsGenerator(words);
		Date t1 = new Date();
		lcg.interpret();
		Date t2 = new Date();
		System.out.println("time taken:\t" + (t2.getTime() - t1.getTime()) + "ms");
	}
}
