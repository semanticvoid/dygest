package dygest.text.tagger.wrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dygest.datatype.Chunk;
import dygest.datatype.Word;
import dygest.text.Document;
import dygest.text.Sentence;
import dygest.text.tagger.ChunkTagger;
import dygest.text.tagger.POSTagger;

public class CandidateKeyAggregator {
	PhraseRules pr = new PhraseRules();
	Document doc = null;
	
	public CandidateKeyAggregator(Document doc) {
		this.doc = doc;
	}
	
	public List<Word> getCandidateKeys() throws IOException, ClassNotFoundException {
		List<Word> words = new ArrayList<Word>();
		List<String> rules = new ArrayList<String>();
		rules.add("nn");
		rules.add("nn nn");
		rules.add("jj nn");
		pr.addRules("np", rules);
		
		ChunkTagger chunker = new ChunkTagger(new POSTagger(), pr);
		for(Sentence sentence : doc.getSentences()) {
			List<Chunk> chunks = chunker.getTags(sentence.getText());
			for(Chunk chunk : chunks) {
				Word word = new Word(chunk.getTextElement().trim());
				word.setProbability(0.0);
				word.setWordFrequency(0);
				word.setCandidateWord(true);
				word.setWordPOS(chunk.getTagName());
				words.add(word);
			}

		}
		return words;	
	}
	
	private double computeCkFrequency(String candidateKey) {
		double count = 0.0;
		int ckLength = candidateKey.length();
		for(Sentence sentence : doc.getSentences()) {
			String largeStr = sentence.getText().trim();
			for(int i = 0; i < largeStr.length() - ckLength; ++i) {
				String subStr = largeStr.substring(i, i+ckLength);
				if(isEqual(subStr, candidateKey)) {
					count++;
				}
			}
		}
		return count;
	}
	
	private boolean isEqual(String str1, String str2) {
		return str1.equals(str2);
	}
}
