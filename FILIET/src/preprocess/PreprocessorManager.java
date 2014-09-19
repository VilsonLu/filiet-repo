package preprocess;

import java.io.IOException;

import model.Sentence;
import model.Token;

public class PreprocessorManager {

	private Tokenizer tokenizer;
	private POSTagger post;
	private DisasterTagger disasterTagger;
	private NamedEntityRecognizer ner;
	
	/*
	 * Constructor: Initializes the modules
	 */
	public PreprocessorManager (){
		InitializeModules();
	}
	
	/*
	 * Default Implementation
	 */
	public void InitializeModules(){
		tokenizer = new Tokenizer(new ArkNLPTokenizer());
		post = new POSTagger(new POSLookup());
		disasterTagger = new DisasterTagger(new DefaultDisasterTag());
		ner = new NamedEntityRecognizer(new SomidiaNER());
	}
	
	/*
	 * Preprocess the text
	 * Tokenizer, POS Tagger and Disaster Tagger
	 */
	public Sentence PreprocessText(String text){
		
		Sentence tokens = null;
		long start = 0;
		long end = 0;
	
		try {
			
			System.out.println("Tweet:");
			System.out.println(text);
			
			// Tokenizer
			System.out.println("Tokenizer:");
			tokens = tokenizer.executeStrategy(text);
			tokens.PrintSentence();
			
			// POS Tagger
			tokens = post.executeStrategy(tokens);			
			System.out.println("POS Tagger:");
			tokens.PrintSentence();
			
			// Named Entity Recognizer
			tokens = ner.executeStrategy(tokens);
			System.out.println("Named Entity Recognizer:");
			tokens.PrintSentence();
			
			// Disaster Keyword Tagger
			tokens =  disasterTagger.executeStrategy(tokens);
			System.out.println("Disaster Tagger:");
			tokens.PrintSentence();
			
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tokens;
	}
}
