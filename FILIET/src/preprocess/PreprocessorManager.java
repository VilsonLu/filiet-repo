package preprocess;

import java.io.IOException;

import preprocess.disastertagger.DisasterTagger;
import preprocess.ner.NamedEntityRecognizer;
import preprocess.ner.SomidiaNERImpl;
import preprocess.postagger.POSLookupImpl;
import preprocess.postagger.POSTagger;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import model.Sentence;

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
		tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());
		post = new POSTagger(new POSLookupImpl());
		//disasterTagger = new DisasterTagger(new DefaultDisasterTag());
		ner = new NamedEntityRecognizer(new SomidiaNERImpl());
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
			
			/*
			// Disaster Keyword Tagger
			tokens =  disasterTagger.executeStrategy(tokens);
			System.out.println("Disaster Tagger:");
			tokens.PrintSentence();
			*/
			

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tokens;
	}
}
