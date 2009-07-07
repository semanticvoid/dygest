package dygest.datatype;

public class Chunk extends Tag{
	private int start;
	private int end;
	
	public int getStartIndex() {
		return start;
	}
	
	public int getEndIndex() {
		return end;
	}
	
	public void setStartIndex(int start) {
		this.start = start;
	}
	
	public void setEndIndex(int end) {
		this.end = end;
	}

}
