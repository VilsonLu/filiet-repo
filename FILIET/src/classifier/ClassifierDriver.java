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
		
		String testData = "./resources/tweets/Testing/Combined-tagged.csv";
		String ngram = "./resources/model/ngram/test-charngram";
		String word = "./resources/model/word/test-wordcounts";
		String modelPath = "./resources/model/classifier/Combined/Combined-kNN-3.model";
		// TODO Auto-generated method stub
		int classIndex = 0;
		
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
