package dygest.datatype;

public class Word {
	private String wordName;
	private int wordFrequency = 0;
	private int semanticFrequency = 0;
	private boolean candidateWord = false;
	private int coverageFrequency = 0;
	private String wordPOS;
	private String stemmedVersion;
	
	public String getName() {
		return wordName;
	}
	
	public int getWordFrequency() {
		return wordFrequency;
	}
	
	public int getSemanticFrequency() {
		return semanticFrequency;
	}

	public boolean isCandidateWord() {
		return candidateWord;
	}

	public int getCoverageFrequency() {
		return coverageFrequency;
	}

	public String getWordPos() {
		return wordPOS;
	}

	public String getStemmedVersion() {
		return stemmedVersion;
	}

	public void setName(String wordName) {
		this.wordName = wordName;
	}
	
	public void setWordFrequency(int wordFrequency) {
		this.wordFrequency = wordFrequency;
	}
	
	public void setSemanticFrequency(int semanticFrequency) {
		this.semanticFrequency = semanticFrequency;
	}

	public void setCandidateWord(boolean candidateWord) {
		this.candidateWord = candidateWord;
	}

	public void setCoverageFrequency(int coverageFrequency) {
		this.coverageFrequency = coverageFrequency;
	}

	public void setWordPOS(String wordPOS) {
		this.wordPOS = wordPOS;
	}

	public void setStemmedVersion(String stemmedVersion) {
		this.stemmedVersion = stemmedVersion;
	}
	
}
