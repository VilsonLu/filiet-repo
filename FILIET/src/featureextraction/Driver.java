package featureextraction;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import support.model.Sentence;
import testing.Testing;




public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String category = "combined";
		String tweets = "./resources/tweets/mario-datasets/original/mario-"+category+".csv";
		String ngram = "./resources/model/ngram/ruby-ngram";
		String word = "./resources/model/word/ruby-word";
		//String saveModel = "./resources/tweets/test-extracted/mario-tfidf/mario-rubytrained-combined.csv";
		String saveModel = "./resources/tweets/test-extracted/mario-tfidf/mario-"+category+".csv";
		String testTweets = "./resources/tweets/ruby-datasets/original/combined-bin-d.csv";

		
		FeatureExtractor fe = new FeatureExtractor(word,ngram);
		//fe.extractFeatures(tweets,saveModel);
		
		List<Sentence> sentences = null;
		try {
			sentences = Testing.readTestData(testTweets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		int count = 1;
		for(Sentence sentence:sentences){
			fe.extract(sentence);
			System.out.println("Test " + count +":");
			Set<Map.Entry<String,Integer>> itSet = sentence.getExtractedWordFeatures().entrySet();
			
			for(Map.Entry<String, Integer> entry: itSet){
				System.out.println(entry.getKey()+": "+entry.getValue());
			}
			
			itSet = sentence.getExtractedNgramFeatures().entrySet();
			
			for(Map.Entry<String, Integer> entry: itSet){
				System.out.println(entry.getKey()+": "+entry.getValue());
			}
			count++;
		}
		
		
		
		
	
		
		
	}
	
	

}
