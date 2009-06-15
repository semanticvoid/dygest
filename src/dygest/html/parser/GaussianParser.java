package dygest.html.parser;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Parser;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;

import com.sun.javadoc.Tag;

/**
 * This class represents the HTMLParser which extracts
 * content from the html source using the Gaussian content
 * extraction algorithm
 * @author anand
 *
 */
public class GaussianParser extends NodeVisitor {

	private float K = 0;
	private boolean appendData = false;
	private List<Chunk> chunks;
	private String content = null;
	private long sumOfCounts = 0;
	
	public GaussianParser(float K) {
		this.K = K;
		this.chunks = new ArrayList<Chunk>();
	}
	
	public void visitTag(Tag tag) {
		if(tag.name().equalsIgnoreCase("script") || tag.name().equalsIgnoreCase("style")) {
			this.appendData = false;
		}
		this.chunks.add(new Chunk(null, 0));
	}
	
	public void visitEndTag(Tag tag) {
		if(tag.name().equalsIgnoreCase("script") || tag.name().equalsIgnoreCase("style")) {
			this.appendData = true;
		}
		this.chunks.add(new Chunk(null, 0));
	}

	public void visitStringNode(Text txt) {
		if(!txt.getText().isEmpty() && this.appendData) {
			String[] tokens = txt.getText().split("[ ]+");
			int count = tokens.length;
			Chunk c = new Chunk(txt.getText(), count);
			this.chunks.add(c);
			this.sumOfCounts += count;
		} else {
			this.chunks.add(new Chunk(null, 0));
		}
	}
	
	private String gaussianThreshold() {
		StringBuffer data = new StringBuffer();
		double variance = 0;
		int length = this.chunks.size();
		double mean = this.sumOfCounts/length;
		
		for(Chunk c : chunks) {
			variance += Math.pow((c.getCount() - mean), 2);
		}
		variance = variance/length;
		double sigma = Math.sqrt(variance);
		
		for(Chunk c : chunks) {
			if(Math.abs(c.getCount() - mean) >= (this.K*sigma)) {
				data.append(c.getText());
			}
		}
		
		return data.toString();
	}
	
	public String parse(String url) {
		if(this.content == null) {
			try {
				Parser parser = new Parser(url);
				parser.visitAllNodesWith(this);
				this.content = this.gaussianThreshold();
			} catch(Exception e) {
				return null;
			}
		}
		
		return this.content;
	}
	
	private class Chunk {
		private int count = 0;
		private String text = null;
		
		public Chunk(String text, int count) {
			this.count = count;
			this.text = text;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
	
}
