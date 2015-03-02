
import informationextraction.InformationExtractionEngine;

import java.io.IOException;
import java.util.List;

import classifier.implementations.ClassifierInterface;
import classifier.implementations.KNNClassifierImpl;
import model.Sentence;
import preprocess.PreprocessorManager;
import testing.Testing;
import crawler.TwitterCrawler;
import featureextraction.FeatureExtractor;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("FILIET - Filipino Information Extraction for Twitter");
		System.out.println();
		
		String ngram = "./resources/model/ngram/ruby-ngram";
		String word = "./resources/model/word/ruby-word";
		String modelPath = "./resources/model/classifier/combined-randomforest.model";
		PreprocessorManager preprocess = new PreprocessorManager();
		FeatureExtractor fe = new FeatureExtractor(word,ngram);
		ClassifierInterface classifier = new KNNClassifierImpl(modelPath);
		
		InformationExtractionEngine extractorEngine = new InformationExtractionEngine();
		
		
		String text = "RT @DZMMTeleRadyo: #walangpasok | Klase sa lahat ng antas sa Mandaluyong City, suspendido bukas (Dec. 8) dahil kay #RubyPH - Mayor Abalos";
		extractorEngine.runExtractor(text);
	
		
//		
//		String path = "./resources/tweets/Testing/Test.csv";
//		try {
//			List<Sentence> sentences = Testing.readTestData(path);
//			for(Sentence sentence: sentences){
//				System.out.println("Preprocessing...");
//				sentence.setSentence(preprocess.PreprocessText(sentence.getRawTweet()).getSentence());
//				System.out.println("Extracting features...");
//				fe.extract(sentence);
//				System.out.println("Classifying...");
//				System.out.println(classifier.classify(sentence));
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

			
	}
}


