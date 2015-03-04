package patternextraction;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import preprocess.PreprocessorManager;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import support.model.ExtractedInformation;
import support.model.Sentence;
import support.model.Token;

public class Driver {
	public static void main(String[] args) {
		String path = "./resources/rules/cd-rules";
		PatternMatching pm = new PatternMatching(path);
		try {
			
			Tokenizer tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());
			PreprocessorManager preprocessor = new PreprocessorManager();
			
			String tweet = "ROMBLON: RT @gmanews: VIDEO: Mahigit 200,000 residente, lumikas sa Sorsogon dahil sa Bagyong #RubyPH http://t.co/1SPOOw4WVF http://t.co/XJerjp7yWk";
			Sentence sentence = preprocessor.PreprocessText(tweet);

			pm.loadRules();
	
			List<ExtractedInformation> x = pm.match(sentence);
			for (ExtractedInformation s : x) {
				System.out.println(s.getInformationType() + ": " + s.getValue().getWord()); 
			}
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
