package dygest.text.tagger.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhraseRules {
	private Map<String, List<String>> rules = new HashMap<String, List<String>>();
	
	public PhraseRules() {
	}
	
	public void addRules(String key, List<String> rules) {
		this.rules.put(key, rules);
	}
	
	public List<String> getRules(String key) {
		return rules.get(key);
	}
	
	public Map<String, List<String>> getAllRules() {
		return rules;
	}
}
