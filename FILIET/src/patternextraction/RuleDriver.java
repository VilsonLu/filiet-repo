package patternextraction;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import preprocess.PreprocessorManager;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import model.ExtractedInformation;
import model.Sentence;
import model.Token;

public class RuleDriver {
	public static void main(String[] args) {
		String path = "./resources/rules/cd-rules";
		PatternMatching pm = new PatternMatching(path);
		try {
			
			Tokenizer tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());
			PreprocessorManager preprocessor = new PreprocessorManager();
			
			//String tweet = "RT @gmanews: ROMBLON — As of 2:30PM, 30689 na ang total evacuees sa buong probinsya, ayon sa PSWD. #RubyPH via @akosiJaysent";
			Sentence sentence = preprocessor.PreprocessText(tweet);

			pm.loadRules();
			List<ExtractedInformation> x = pm.match(sentence);
			for (ExtractedInformation s : x) {
				System.out.println(s.getInformationType() + ": " + s.getValue().getWord()); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
