package dygest.text.tagger;

import java.util.List;

import dygest.datatype.Tag;

public interface ITagger {
	public List<? extends Tag> getTags(String sentence);
}
