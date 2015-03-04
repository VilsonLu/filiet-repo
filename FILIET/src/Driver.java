
import informationextraction.InformationExtractionEngine;

import java.io.IOException;
import java.util.List;

import classifier.implementations.ClassifierInterface;
import preprocess.PreprocessorManager;
import support.model.Sentence;
import testing.Testing;
import crawler.TwitterCrawler;
import featureextraction.FeatureExtractor;


public class Driver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String testTweets = "./resources/tweets/ruby-datasets/original/combined-bin-d.csv";
		
		System.out.println("FILIET - Filipino Information Extraction for Twitter");
		System.out.println("Running: " + testTweets);
		System.out.println();
		
		InformationExtractionEngine extractorEngine = new InformationExtractionEngine();
		
		List<Sentence> sentences = null;
		try {
			sentences = Testing.readTestData(testTweets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String text = "RT @DZMMTeleRadyo: #walangpasok | Klase sa lahat ng antas sa Mandaluyong City, suspendido bukas (Dec. 8) dahil kay #RubyPH - Mayor Abalos";
		for(Sentence sentence: sentences){
			extractorEngine.runExtractor(sentence);
		}
	
	}
}


