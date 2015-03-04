package informationextraction;

import classifier.implementations.Classifier;
import classifier.implementations.ClassifierImpl;
import classifier.implementations.ClassifierInterface;
import preprocess.PreprocessorManager;
import support.model.Sentence;
import featureextraction.FeatureExtractor;

public class InformationExtractionEngine {

	// paths to resources
	private String word = "./resources/model/word/ruby-word";
	private String ngram = "./resources/model/ngram/ruby-ngram";
	private String modelPath = "./resources/model/classifier/combined-knn5.model";

	// modules
	private PreprocessorManager preprocessor;
	private FeatureExtractor feature;
	private Classifier classifier;

	public InformationExtractionEngine() throws Exception {
		preprocessor = new PreprocessorManager();
		feature = new FeatureExtractor(word, ngram);
		classifier = new Classifier(new ClassifierImpl());
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
		extractedTweet.setCategory(classifier.executeStrategy(extractedTweet));
		System.out.println("Predicted: " + extractedTweet.getCategory());
		System.out.println("Actual: " + extractedTweet.getTweets().getCategory());
		
		return extractedTweet;
	}
}
