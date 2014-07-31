package preprocess;

import java.io.IOException;

public class PreprocessorManager {

	private Tokenizer tokenizer;
	private POSTagger post;
	private DisasterTagger disasterTagger;
	
	/*
	 * Constructor: Initializes the modules
	 */
	public PreprocessorManager (){
		InitializeModules();
	}
	
	/*
	 * Prints the array
	 */
	public void PrintArray(String[] text){
		for(String token: text){
			System.out.println(token);
		}
	}
	
	/*
	 * Default Implementation
	 */
	public void InitializeModules(){
		tokenizer = new Tokenizer(new OpenNLPTokenizer());
		post = new POSTagger(new POSLookup());
		disasterTagger = new DisasterTagger(new DefaultDisasterTag());
	}
	
	/*
	 * Preprocess the text
	 * Tokenizer, POS Tagger and Disaster Tagger
	 */
	public void PreprocessText(String text){
		
		String tokens[] = null;
	
		try {
			
			// Tokenizer
			tokens = tokenizer.executeStrategy(text);
			PrintArray(tokens);
			// POS Tagger
			tokens = post.executeStrategy(tokens);
			System.out.println("POS Tagger:");
			PrintArray(tokens);
			// Disaster Tagger
			tokens = disasterTagger.executeStrategy(tokens);
			System.out.println("Disaster Tagger:");
			PrintArray(tokens);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
