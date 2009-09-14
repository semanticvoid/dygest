/**
 * 
 */
package dygest.html.parser;

import java.util.HashMap;

import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.Text;
import org.htmlparser.visitors.NodeVisitor;

/**
 * This class represents the HTMLParser which extracts
 * anchor texts ansd associated links from page
 * @author anand
 *
 */
public class AnchorTextExtractor extends NodeVisitor {

	/**
	 * {
	 * 	'URL' => [text, text...]
	 * 	...
	 * }
	 */
	private HashMap<String, HashMap<String, Integer>> anchors;
	
	private String url;
	private StringBuffer text;;
	
	public AnchorTextExtractor() {
		this.anchors = new HashMap<String, HashMap<String, Integer>>();
		this.url = null;
		this.text = new StringBuffer();
	}
	
	public void visitTag(Tag tag) {
		if(tag.getTagName().equalsIgnoreCase("a")) {
			this.url = tag.getAttribute("href");
		}
	}
	
	public void visitEndTag(Tag tag) {
		if(tag.getTagName().equalsIgnoreCase("a")) {
			if(!this.anchors.containsKey(this.url)) {
				this.anchors.put(this.url, new HashMap<String, Integer>());
			}
			
			String text = this.text.toString().trim();
			
			if(text.length() > 0) {
				HashMap<String, Integer> anchorTexts = this.anchors.get(this.url);
				if(anchorTexts.containsKey(text) && anchorTexts.get(this.text) != null) {
					anchorTexts.put(text, anchorTexts.get(this.text)+1);
				} else {
					anchorTexts.put(text, 1);
				}
			}
			
			// reset
			this.url = null;
			this.text = new StringBuffer();
		}
	}

	public void visitStringNode(Text txt) {
		if(this.url != null) {
			this.text.append(txt.getText() + " ");
		}
	}
	
	public HashMap<String, HashMap<String, Integer>> parse(String url) {
		try {
			Parser parser = new Parser(url);
			parser.visitAllNodesWith(this);
			return this.anchors;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static void main(String args[]) {
		AnchorTextExtractor atx = new AnchorTextExtractor();
		HashMap<String, HashMap<String, Integer>> anchors = atx.parse("http://www.wired.com/epicenter/2009/06/1-million-netflix-prize-so-close-they-can-taste-it/");
		System.out.println("");
	}
}
