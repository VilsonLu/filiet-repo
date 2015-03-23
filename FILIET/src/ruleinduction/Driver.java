package ruleinduction;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import ontology.model.CautionAndAdviceTweet;
import binder.Binder;
import preprocess.PreprocessorManager;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import support.model.ExtractedInformation;
import support.model.PostExtractedInformation;
import support.model.Rule;
import support.model.Sentence;
import support.model.Token;
import support.model.Tweet;

public class Driver {
	public static void main(String[] args) throws IOException {
		String path = "./resources/rules/ca-rules";
		RuleInductor pm = new RuleInductor(path);
		try {
			
			PreprocessorManager preprocessor = new PreprocessorManager();
			
			String tweet = "Malanday, Marikina, Muntinlupa, Metro Manila";
			Sentence sentence = preprocessor.PreprocessText(tweet);

			pm.loadRules();
			System.out.println();
			List<PostExtractedInformation> x = pm.match(sentence);
			System.out.println();
			System.out.println(x.size());
			for (PostExtractedInformation s : x) {
				
				s.printText();
				System.out.println();
			}
		
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
