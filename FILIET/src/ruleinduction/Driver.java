package ruleinduction;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import preprocess.PreprocessorManager;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import support.model.ExtractedInformation;
import support.model.PostExtractedInformation;
import support.model.Rule;
import support.model.Sentence;
import support.model.Token;

public class Driver {
	public static void main(String[] args) throws IOException {
		String path = "./resources/rules/cd-rules";
		RuleInductor pm = new RuleInductor(path);
		try {
			
			Tokenizer tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());
			PreprocessorManager preprocessor = new PreprocessorManager();
			
			String tweet ="#AksyonSaHagupit | 2 bahay nawasak dahil sa naglalakihang alon at lakas na hangin sa Brgy. Tabunan, Matnog! #RubyPH";
			Sentence sentence = preprocessor.PreprocessText(tweet);

			pm.loadRules();
	
			List<PostExtractedInformation> x = pm.match(sentence);
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
