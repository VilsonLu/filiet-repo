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
			System.out.print(token);
			System.out.print(" ");
		}
		System.out.println();
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
	public String[] PreprocessText(String text){
		
		String tokens[] = null;
		long start = 0;
		long end = 0;
	
		try {
			
			// Tokenizer
			System.out.println("Tokenizer");
			tokens = tokenizer.executeStrategy(text);
			PrintArray(tokens);
			
			// POS Tagger
			start = System.nanoTime();
			tokens = post.executeStrategy(tokens);
			end = System.nanoTime();
			
			System.out.println("POS Tagger:");
			PrintArray(tokens);
			
			System.out.println("Execution Time: " + ((end-start)/1000000));
			
			// Disaster Tagger
			tokens = disasterTagger.executeStrategy(tokens);
			System.out.println("Disaster Tagger:");
			PrintArray(tokens);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tokens;
	}
}
