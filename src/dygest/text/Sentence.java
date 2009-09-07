package dygest.text;

import dygest.datatype.Word;
import java.util.ArrayList;
import java.util.List;

public class Sentence {
	// the original whole sentence
	private String text;
	// the pure tokens
	private List<String> tokens;
	// the processed tokens
	private List<String> pTokens;
        // the candidate keys occurring in sentence
        private List<Word> candidateKeys;
	
	public Sentence(String text, List<String> tokens, List<String> pTokens) {
		this.text = text;
		this.tokens = tokens;
		this.pTokens = pTokens;
                this.candidateKeys = new ArrayList<Word>();
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

        public List<Word> getCandidateKeys() {
            return candidateKeys;
        }

        public void setCandidateKeys(List<Word> keys) {
            this.candidateKeys = keys;
        }

        public void addCandidateKey(Word key) {
            this.candidateKeys.add(key);
        }
}
