
import informationextraction.InformationExtractionEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classifier.implementations.Classifier;
import classifier.implementations.ClassifierImpl;
import classifier.implementations.ClassifierInterface;
import preprocess.PreprocessorManager;
import support.model.Sentence;
import support.other.XmlParser;
import testing.Testing;
import crawler.TwitterCrawler;
import evaluate.Evaluator;
import featureextraction.FeatureExtractor;


public class Driver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String testTweets = "./resources/tweets/ruby-datasets/original/ruby-combined.csv";
		String modelPath = "./resources/model/classifier/r-randomforest-20.model";
		System.out.println("FILIET - Filipino Information Extraction for Twitter");
		System.out.println("Running: " + testTweets);
		System.out.println();
		
		InformationExtractionEngine extractorEngine = new InformationExtractionEngine();
		extractorEngine.setModelPath(modelPath);
		
		List<Sentence> sentences = null;
		try {
			sentences = Testing.readTestData(testTweets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Sentence> processedSentences = new ArrayList<Sentence>();
		
//		String text = "RT @DZMMTeleRadyo: #walangpasok | Klase sa lahat ng antas sa Mandaluyong City, suspendido bukas (Dec. 8) dahil kay #RubyPH - Mayor Abalos";
		try{
		for(Sentence sentence: sentences){
			System.out.println(sentence.getTweets().getTweetID());
			Sentence s = extractorEngine.runExtractor(sentence);
		
			if(sentence.getTweets().getCategory().equalsIgnoreCase("CA")){
				processedSentences.add(s);
			}
		
			
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		XmlParser.saveXMLCA(processedSentences, "./resources/results/hypothesis/ruby-combined-classifier-CA.xml");
		Evaluator evaluator = new Evaluator();
//		evaluator.evaluateClassifier(processedSentences);
		evaluator.evaluateDatasetCA(processedSentences, "./resources/results/annotations/Annotation-CA.csv");
//	
	}
}


