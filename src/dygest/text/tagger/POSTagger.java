package dygest.text.tagger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliasi.hmm.HiddenMarkovModel;
import com.aliasi.hmm.HmmDecoder;
import com.aliasi.util.Streams;

import dygest.datatype.Tag;
import dygest.text.tokenizer.WordTokenizer;

public class POSTagger implements ITagger{
	
	private HmmDecoder decoder;
	
	public POSTagger() throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream("model/pos-en-general-brown.HiddenMarkovModel");
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
        HiddenMarkovModel hmm = (HiddenMarkovModel) objIn.readObject();
        Streams.closeInputStream(objIn);
        decoder = new HmmDecoder(hmm);
	}
	
	public List<Tag> getTags(String sentence) {
	    WordTokenizer wordTok = new WordTokenizer();
        List<String> tokens = wordTok.tokenize(sentence);
        String[] words = (String[])tokens.toArray();
        String[] tags = decoder.firstBest(words);
        List<Tag> taggedSentence = new ArrayList<Tag>();
        for(int i = 0; i < words.length; ++i) {
        	Tag tag = new Tag();
        	tag.setTagName(tags[i]);
        	tag.setTextElement(words[i]);
        	taggedSentence.add(tag);
        }
		return taggedSentence;
	}
	
	public static void main(String[] args) {
		try {
			POSTagger posTagger = new POSTagger();
			List<Tag> taggedSentence = posTagger.getTags("I saw a man under the tree with a telescope who is watching me from a distant place");
			for(Tag tag : taggedSentence) {
				System.out.println(tag.getTextElement() + "_" + tag.getTagName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
