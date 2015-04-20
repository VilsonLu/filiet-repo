package informationextraction;

import classifier.ClassifierBuilder;
import classifier.implementations.Classifier;
import classifier.implementations.ClassifierImpl;
import classifier.implementations.ClassifierInterface;
import classifier.implementations.MultiClassifierImpl;
import preprocess.PreprocessorManager;
import ruleinduction.RuleInductor;
import support.model.Sentence;
import featureextraction.FeatureExtractor;

public class InformationExtractionEngine {

	// paths to resources
	private String word = "./resources/model/TFIDF-Scores/mario-ruby/marioruby-word-combined-30.txt";
	private String rulePath = "./resources/rules/simple-rules-no-loc";

	// modules
	private PreprocessorManager preprocessor;
	private FeatureExtractor feature;
	private Classifier classifier;
	private RuleInductor ruleInductor;

	public InformationExtractionEngine() throws Exception {
		preprocessor = new PreprocessorManager();
		feature = new FeatureExtractor(word, null);
		classifier = new Classifier(new ClassifierImpl());
		ruleInductor = new RuleInductor(rulePath);
	}

	/**
	 * Integration of the modules
	 * 
	 * @param pm
	 *            - Preprocessor Manager
	 * @param feat
	 *            - Feature Extractor
	 * @param classifier
	 *            - Classifier
	 */
	public InformationExtractionEngine(PreprocessorManager pm,
			FeatureExtractor feat, Classifier classifier) {
		this.preprocessor = pm;
		this.feature = feat;
		this.classifier = classifier;
	}

	/**
	 * @param preprocessor
	 *            the preprocessor to set
	 */
	public void setPreprocessor(PreprocessorManager preprocessor) {
		this.preprocessor = preprocessor;
	}

	/**
	 * @param feature
	 *            the feature to set
	 */
	public void setFeature(FeatureExtractor feature) {
		this.feature = feature;
	}

	/**
	 * @param classifier
	 *            the classifier to set
	 */
	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	/**
	 * @param ruleInductor
	 *            the ruleInductor to set
	 */
	public void setRuleInductor(RuleInductor ruleInductor) {
		this.ruleInductor = ruleInductor;
	}

	public Sentence runExtractor(Sentence tweet) {

		Sentence extractedTweet = null;
		if (preprocessor != null) {
			System.out.println("Preprocessor Module");
			extractedTweet = preprocessor.PreprocessText(tweet.getRawTweet());
			extractedTweet.setTweets(tweet.getTweets());
		}
		
		if (feature != null) {
			System.out.println("Feature Extractor Module");
			feature.extract(extractedTweet);
		}

		if (classifier != null) {

			System.out.println("Classifier Module");
			classifier.executeStrategy(extractedTweet);
			System.out.println("Predicted: " + extractedTweet.getCategory());
			System.out.println("Actual: "
					+ extractedTweet.getTweets().getCategory());
		}
		if (ruleInductor != null) {
			if (!extractedTweet.getTweets().getCategory().equalsIgnoreCase("O")) {
				System.out.println("Rule Induction Module");
				extractedTweet.setExtractedInformation(ruleInductor
						.match(extractedTweet));
			}
		}

		return extractedTweet;
	}
}
