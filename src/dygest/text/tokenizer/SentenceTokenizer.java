package dygest.text.tokenizer;

import java.util.ArrayList;
import java.util.List;

public class SentenceTokenizer implements ITokenizer{
	private List<String> sentences = new ArrayList<String>();
	
	public List<String> tokenize(String text) {		
		int startIndex = 0;
		int endIndex = 0;
		for(int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if(ch == '?' || ch == '!') {
				endIndex = i;
				addSentence(text, startIndex, endIndex);
				startIndex = endIndex;
			}
			else if(ch == '.') {
				if(isDelimiter(text, i)) {
					endIndex = i;
					addSentence(text, startIndex, endIndex);
					startIndex = endIndex;					
				}
			}
		}
		addSentence(text, startIndex, text.length());
		return sentences;
	}
	
	private void addSentence(String text, int startIndex, int endIndex) {
		try {
			if(startIndex != endIndex -1) {
				if(startIndex == 0) {
					sentences.add(text.substring(0, endIndex));
				} else if((endIndex - 1) != (startIndex + 2) && text.charAt(startIndex+1) == ' ') {
					sentences.add(text.substring(startIndex + 2, endIndex));
				} else {
					sentences.add(text.substring(startIndex + 1, endIndex));
				}
			}					
		} catch(IndexOutOfBoundsException e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	private boolean isDelimiter(String text, int index) {
		//Backward
		int startIndex = index;
		int	endIndex = index;
		char ch;
		//Backward
		do {
			ch = text.charAt(startIndex);
			startIndex--;	
		} while(ch != ' ');

		//Forward
		do {
			ch = text.charAt(endIndex);
			endIndex++;	
		} while(ch != ' ' && endIndex < text.length());
		
		String textChunk = text.substring(startIndex + 2, endIndex);
		
		System.out.println(textChunk);
				
		//Rule 1 : Numbers 
		if(textChunk.matches("[ ]*(.)[0-9]+\\.[0-9]+(.)[ ]*")) {
			System.out.println("False : " + textChunk);
			return false;
		}
		
		//Rule 2 : abbreviations
		if(textChunk.matches("[ ]*[A-Za-z]\\.([A-Za-z0-9]\\.)*[ ]*") || textChunk.matches("[ ]*[A-Z][^aeiou]+\\.+[ ]*")) {
			System.out.println("False : " + textChunk);
			return false;
		}
		
		//Rule 0 : if there is a space after delimiter then return true
		if((index != text.length() - 1) && text.charAt(index + 1) == ' ') {
			System.out.println("true : "  + textChunk);
			return true;
		}

		return true;
	}
	
	public static void main(String args[]) {
		SentenceTokenizer st = new SentenceTokenizer();
		st.tokenize("hello ijsdgfd amBabuBabu. MR. Sudheer is worst. 60007.3 is a bad number. i see! ok.");
	}
	
}

