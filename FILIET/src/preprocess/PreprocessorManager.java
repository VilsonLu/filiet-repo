package preprocess;

import java.io.IOException;

import org.apache.ivy.plugins.matcher.NoMatcher;

import preprocess.disastertagger.DisasterTagger;
import preprocess.ner.NamedEntityRecognizer;
import preprocess.ner.SomidiaHashNERImpl;
import preprocess.ner.SomidiaNERImpl;
import preprocess.normalizer.NormApiImpl;
import preprocess.normalizer.Normalizer;
import preprocess.postagger.POSHashLookupImpl;
import preprocess.postagger.POSLookupImpl;
import preprocess.postagger.POSTagger;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import support.model.Sentence;

public class PreprocessorManager {
	
	private Tokenizer tokenizer;
	private POSTagger post;
	private DisasterTagger disasterTagger;
	private NamedEntityRecognizer ner;
	private Normalizer normalizer;


	/*
	 * Constructor: Initializes the modules
	 */
	public PreprocessorManager() {
		InitializeModules();
	}

	/*
	 * Default Implementation
	 */
	public void InitializeModules(){
		normalizer = new Normalizer(new NormApiImpl());
		tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());
		post = new POSTagger(new POSHashLookupImpl());
		// disasterTagger = new DisasterTagger(new DefaultDisasterTag());
		ner = new NamedEntityRecognizer(new SomidiaHashNERImpl());
	}

	/*
	 * Preprocess the text Tokenizer, POS Tagger and Disaster Tagger
	 */
	public Sentence PreprocessText(String text){

		Sentence tokens = null;
		long start = 0;
		long end = 0;

		System.out.println("Tweet: " + text);
		System.out.println();

		// Normalizer
		System.out.println("Normalizer:");
		String normalizedTweet = normalizer.executeStrategy(text);
		System.out.println(normalizedTweet);

		// Tokenizer
		tokens = tokenizer.executeStrategy(normalizedTweet);
		System.out.println("Tokenizer: ");
		tokens.toString();
		System.out.println();
		
		// POS Tagger
		tokens = post.executeStrategy(tokens);		
		System.out.println("POS Tagger:");
		tokens.toString();
		System.out.println();

		// Named Entity Recognizer
		tokens = ner.executeStrategy(tokens);
		System.out.println("Named Entity Recognizer:");
		tokens.toString();		
		System.out.println();
	
		return tokens;
	}
}
