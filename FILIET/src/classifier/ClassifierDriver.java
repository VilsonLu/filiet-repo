package classifier;

import classifier.implementations.ClassifierInterface;
import classifier.implementations.KNNClassifierImpl;
import featureextraction.FeatureExtractor;

public class ClassifierDriver {


	public static void main(String[] args) {
		
		String testData = "./resources/tweets/testdata/test-data.csv";
		String ngram = "./resources/model/ngram/char-ngram";
		String word = "./resources/model/wordcount/wordcounts";
		String modelPath = "./resources/model/classifier/kNN-7.model";
		// TODO Auto-generated method stub
		int classIndex = 0;
		
		FeatureExtractor fe = new FeatureExtractor(word,ngram);
	
		classIndex = 110;
		System.out.println("Class Index: "  + classIndex);
		ClassifierInterface classifier = new KNNClassifierImpl(modelPath);
		
		
		
		
	/*	List<Sentence> testSentences = Testing.readTestData(testData);
		int i = 1;
		for(Sentence sentence: testSentences){
			System.out.println("Test " + i);
			System.out.println(sentence.toString());
			fe.extract(sentence);
			System.out.println("Expected: "+classifier.classify(sentence));
			System.out.println("Actual: " + sentence.getTweets().getCategory());
			System.out.println();
			i++;
		}*/
		
	
		
		
	}

}
