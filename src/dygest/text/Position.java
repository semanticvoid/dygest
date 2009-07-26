package dygest.text;

public class Position {
	private int sentenceIndex;
	private int wordIndex;
	
	public Position(int sIndex, int wIndex) {
		this.sentenceIndex = sIndex;
		this.wordIndex = wIndex;
	}
	
	public int getSentenceIndex() {
		return sentenceIndex;
	}
	
	public int getWordIndex() {
		return wordIndex;
	}
}
