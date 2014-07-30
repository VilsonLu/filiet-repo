package preprocess;

import java.io.IOException;

public class PreprocessorManager {

	public void PrintArray(String[] text){
		for(String token: text){
			System.out.println(token);
		}
	}
	public void PreprocessText(String text){
		String tokens[] = null;
		Tokenizer tokenizer = new Tokenizer();
		POSTagger post = new POSTagger();
		DisasterTagger disasterTagger = new DisasterTagger();
		try {
			tokens = tokenizer.OpenNLPTokenizer(text);
			System.out.println("Tokenizer:");
			PrintArray(tokens);
			tokens = post.POSLookUp(tokens);
			System.out.println("POS Tagger:");
			PrintArray(tokens);
			tokens = disasterTagger.DisasterTag(tokens);
			System.out.println("Disaster Tagger:");
			PrintArray(tokens);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
