package dygest.text.tagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dygest.datatype.Chunk;
import dygest.datatype.Tag;
import dygest.text.tokenizer.WordTokenizer;

public class ChunkTagger implements ITagger{
	private ITagger posTagger = null;
	private static final String NP = "np";
	
	private class PhraseRules {
		private Map<String, List<String>> rules = new HashMap<String, List<String>>();
		
		public PhraseRules() {
			
			//Rules for NP
			List<String> npRules = new ArrayList<String>();
			npRules.add("nn");
			npRules.add("nn nn");
			npRules.add("jj nn");
			rules.put("np", npRules);			
		}
		public Map<String, List<String>> getRules() {
			return rules;
		}
	}
	
	PhraseRules pr = new PhraseRules();
	
	public ChunkTagger() throws IOException, ClassNotFoundException {
		posTagger = new POSTagger();
	}
	
	@SuppressWarnings("unchecked")
	public List<Chunk> getTags(String sentence) {
		List<Chunk> chunks = new ArrayList<Chunk>();
		List<Tag> tags = (List<Tag>) posTagger.getTags(sentence);
		
		//checking for NP
		chunks.addAll(getChunks(tags, NP));
		
		return chunks;
	}
	
	private List<Chunk> getChunks(List<Tag> tags, String chunkName) {
		List<Chunk> chunks = new ArrayList<Chunk>();
		Map<String, List<String>> chunkRules = pr.getRules();
		WordTokenizer tokenizer = new WordTokenizer();
		List<String> npRules = chunkRules.get(chunkName);
		for(String npRule : npRules) {
			int ruleSize = getCount(npRule, ' ');
			List<String> nonRecursiveRules = tokenizer.tokenize(npRule);
			for(int i = 0; i < tags.size() - ruleSize; ++i) {
				if(isListsEqual(tags.subList(i, i + ruleSize + 1), nonRecursiveRules)) {
					Chunk chunk = new Chunk();
					chunk.setTagName(chunkName);
					chunk.setStartIndex(i);
					chunk.setEndIndex(i+ruleSize);
					chunks.add(chunk);
				}
			}
		}
		return chunks;
	}
	
	private boolean isListsEqual(List<Tag> list1, List<String> list2) {
		if(list1 == null && list2 == null) {
			return true;
		} else if( list1 == null || list2 == null) {
			return false;
		} else if( list1.size() != list2.size()) {
			return false;
		} else {
			for(int i = 0; i < list1.size(); ++i) {
				if(!list1.get(i).getTagName().equals(list2.get(i))) {
					return false;
				}
			}
		}		
		return true;
	}
	
	private int getCount(String str, char ch) {
		int count = 0;
		for(int i = 0; i < str.length(); ++i) {
			if(str.charAt(i) == ch){
				count++;
			}
		}
		return count;
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ChunkTagger ct = new ChunkTagger();
		String str = "the man is good who is of the woman";
		System.out.println(str);
		List<Chunk> chunks = ct.getTags(str);
		System.out.println("i am finisshed");
	}
}
