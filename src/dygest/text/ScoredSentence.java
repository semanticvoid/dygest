/**
 * 
 */
package dygest.text;

/**
 * @author anand
 *
 */
public class ScoredSentence extends Sentence {

	private double score;

	public ScoredSentence(Sentence s, double score) {
		super(s.getText(), s.getTokens(), s.getpTokens());
		this.setScore(score);
	}
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
