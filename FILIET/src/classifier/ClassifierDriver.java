package classifier;

import java.io.IOException;
import java.util.List;

import model.Sentence;
import testing.Testing;
import classifier.implementations.ClassifierInterface;
import classifier.implementations.KNNClassifierImpl;
import featureextraction.FeatureExtractor;

public class ClassifierDriver {


	public static void main(String[] args) throws IOException {
		
		String testData = "./resources/tweets/test-extracted/mario-tfidf/mario-combined.csv";
		String ngram = "./resources/model/ngram/ruby-ngram";
		String word = "./resources/model/word/ruby-word";
		String modelPath = "./resources/model/classifier/combined-knn5.model";

		
		FeatureExtractor fe = new FeatureExtractor(word,ngram);
	
		ClassifierInterface classifier = new KNNClassifierImpl(modelPath);
		
		
		
		List<Sentence> testSentences = Testing.readTestData(testData);
		int i = 1;
		for(Sentence sentence: testSentences){
			System.out.println("Test " + i);
			System.out.println(sentence.toString());
			fe.extract(sentence);
			System.out.println("Classified: "+classifier.classify(sentence));
			System.out.println("Actual: " + sentence.getTweets().getCategory());
			System.out.println();
			i++;
		}
		
	
		
		
	}

}
