package informationextraction;

import java.io.IOException;

import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import model.Sentence;

public class RuleDriver {
	public static void main(String[] args) {
		String path = "./resources/rules/simple-rules";
		PatternMatching pm = new PatternMatching(path);
		try {
			
			Tokenizer tokenizer = new Tokenizer(new OpenNLPTokenizerImpl());
			Sentence sentence = tokenizer.executeStrategy("hello world");
			pm.loadRules();
			pm.match(sentence);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
