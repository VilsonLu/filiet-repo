package informationextraction;

import classifier.implementations.Classifier;
import classifier.implementations.ClassifierImpl;
import classifier.implementations.ClassifierInterface;
import preprocess.PreprocessorManager;
import ruleinduction.RuleInductor;
import support.model.Sentence;
import featureextraction.FeatureExtractor;

public class InformationExtractionEngine {

	// paths to resources
	private String word = "./resources/model/word/ruby-word";
	private String ngram = "./resources/model/ngram/ruby-ngram";
	private String modelPath = "./resources/model/classifier/combined-randomforest.model";
	private String rulePath = "./resources/rules/simple-rules";

	// modules
	private PreprocessorManager preprocessor;
	private FeatureExtractor feature;
	private Classifier classifier;
	private RuleInductor ruleInductor;

	public InformationExtractionEngine() throws Exception {
		preprocessor = new PreprocessorManager();
		feature = new FeatureExtractor(word, ngram);
		classifier = new Classifier(new ClassifierImpl());
		ruleInductor = new RuleInductor(rulePath);
	}

	/**
	 * Integration of the modules
	 * @param pm - Preprocessor Manager
	 * @param feat - Feature Extractor
	 * @param classifier - Classifier
	 */
	public InformationExtractionEngine(PreprocessorManager pm,
			FeatureExtractor feat, Classifier classifier) {
		this.preprocessor = pm;
		this.feature = feat;
		this.classifier = classifier;
	}
	
	public Sentence runExtractor(Sentence tweet){
		
		System.out.println("Preprocessor Module");
		Sentence extractedTweet = preprocessor.PreprocessText(tweet.getRawTweet());
		extractedTweet.setTweets(tweet.getTweets());
		
		System.out.println("Feature Extractor Module");
		feature.extract(extractedTweet);
		
		System.out.println("Classifier Module");
		classifier.executeStrategy(extractedTweet);
		System.out.println("Predicted: " + extractedTweet.getCategory());
		System.out.println("Actual: " + extractedTweet.getTweets().getCategory());
		
		if(extractedTweet.getCategory() != "O"){
			System.out.println("Rule Induction Module");
			extractedTweet.setExtractedInformation(ruleInductor.match(extractedTweet));
		}
		
		return extractedTweet;
	}
}
