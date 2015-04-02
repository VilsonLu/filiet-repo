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
		String category = "Combined";
		String percent = "10";
		String tweets = "./resources/tweets/Mario-datasets/original/Mario-"+category+".csv";
		String ngram = "./resources/model/ngram/mario/2-gram/mario-2gram-ca"+"-"+percent;
		String word = "./resources/model/TFIDF-Scores/mario-ruby/marioruby-word-"+category+"-"+percent+".txt";
		
		
		String saveModel = "./resources/tweets/test-extracted/ruby-tfidf/ruby-"+category+"-10.csv";
		String saveNgram = "./resources/tweets/test-extracted/mario-tfidf/mario-"+category+"-2gram-"+percent+".csv";
		String saveWord = "./resources/tweets/test-extracted/marioruby-tfidf/Mario-marioruby-"+category+"-word-"+percent+".csv";
		
		FeatureExtractor fe = new FeatureExtractor(word,null);
		System.out.println("Extracting features...");
		fe.extractFeatures(tweets,saveWord);
	
		//fe.extractFeaturesNgram(tweets, saveNgram);
		System.out.println("Extraction complete!");
		
//		List<Sentence> sentences = null;
//		try {
//			sentences = Testing.readTestData(testTweets);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//		int count = 1;
//		for(Sentence sentence:sentences){
//			fe.extract(sentence);
//			System.out.println("Test " + count +":");
//			Set<Map.Entry<String,Integer>> itSet = sentence.getExtractedWordFeatures().entrySet();
//			
//			for(Map.Entry<String, Integer> entry: itSet){
//				System.out.println(entry.getKey()+": "+entry.getValue());
//			}
//			
//			itSet = sentence.getExtractedNgramFeatures().entrySet();
//			
//			for(Map.Entry<String, Integer> entry: itSet){
//				System.out.println(entry.getKey()+": "+entry.getValue());
//			}
//			count++;
//		}
		
		
		
		
	
		
		
	}
	
	

}
