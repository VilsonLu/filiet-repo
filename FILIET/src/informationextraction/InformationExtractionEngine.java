package informationextraction;

import model.Sentence;
import classifier.implementations.Classifier;
import classifier.implementations.ClassifierInterface;
import classifier.implementations.KNNClassifierImpl;
import preprocess.PreprocessorManager;
import featureextraction.FeatureExtractor;

public class InformationExtractionEngine {

	// paths to resources
	String word = "./resources/model/word/ruby-word";
	String ngram = "./resources/model/ngram/ruby-ngram";
	String modelPath = "./resources/model/classifier/combined-knn5.model";

	// modules
	private PreprocessorManager preprocessor;
	private FeatureExtractor feature;
	private Classifier classifier;

	public InformationExtractionEngine() {
		preprocessor = new PreprocessorManager();
		feature = new FeatureExtractor(word, ngram);
		classifier = new Classifier(new KNNClassifierImpl(modelPath));
	}

	public InformationExtractionEngine(PreprocessorManager pm,
			FeatureExtractor feat, Classifier classifier) {
		this.preprocessor = pm;
		this.feature = feat;
		this.classifier = classifier;
	}
	
	public Sentence runExtractor(Sentence sentence){
		
		Sentence tweet = preprocessor.PreprocessText(sentence.getRawTweet());
		feature.extract(sentence);
		classifier.executeStrategy(sentence)
		
		return sentence;
	}
}
