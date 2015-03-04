package informationextraction;

import model.Sentence;
import classifier.implementations.Classifier;
import classifier.implementations.ClassifierInterface;
import classifier.implementations.KNNClassifierImpl;
import preprocess.PreprocessorManager;
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

	public InformationExtractionEngine() {
		preprocessor = new PreprocessorManager();
		feature = new FeatureExtractor(word, ngram);
		//classifier = new Classifier(new KNNClassifierImpl(modelPath));
	}

	public InformationExtractionEngine(PreprocessorManager pm,
			FeatureExtractor feat, Classifier classifier) {
		this.preprocessor = pm;
		this.feature = feat;
		this.classifier = classifier;
	}
	
	public Sentence runExtractor(Sentence tweet){
		
		//System.out.println("Preprocessor Module");
		Sentence extractedTweet = preprocessor.PreprocessText(tweet.getRawTweet());
		extractedTweet.setTweets(tweet.getTweets());
		
		System.out.println("Feature Extractor Module");
		feature.extract(extractedTweet);
		
		//extractedTweet.setCategory(classifier.executeStrategy(extractedTweet));
		
		return extractedTweet;
	}
}
