package dygest.text;

import java.util.List;

public class Sentence {
	// the original whole sentence
	private String text;
	// the pure tokens
	private List<String> tokens;
	// the processed tokens
	private List<String> pTokens;
	
	public Sentence(String text, List<String> tokens, List<String> pTokens) {
		this.text = text;
		this.tokens = tokens;
		this.pTokens = pTokens;
	}
	
	public String getText() {
		return text;
	}
	
	public List<String> getTokens() {
		return tokens;
	}
	
	public List<String> getpTokens() {
		return pTokens;
	}
}
