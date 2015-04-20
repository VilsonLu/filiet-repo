import informationextraction.InformationExtractionEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import binder.Binder;
import ontology.OntologyModule;
import classifier.implementations.Classifier;
import classifier.implementations.ClassifierImpl;
import classifier.implementations.ClassifierInterface;
import preprocess.PreprocessorManager;
import support.model.PostExtractedInformation;
import support.model.Sentence;
import support.other.XmlParser;
import testing.Reader;
import crawler.TwitterCrawler;
import evaluate.EvaluatorClassifier;
import evaluate.EvaluatorIE;
import featureextraction.FeatureExtractor;

public class Driver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String testTweets = "./resources/tweets/mario-datasets/original/mario-combined.csv";
		System.out.println("FILIET - Filipino Information Extraction for Twitter");
		System.out.println("Running: " + testTweets);
		System.out.println();

		InformationExtractionEngine extractorEngine = new InformationExtractionEngine();

		List<Sentence> sentences = null;
		try {
			sentences = Reader.readCSVFile(testTweets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Sentence> processedSentences = new ArrayList<Sentence>();
//		OntologyModule ontology = new OntologyModule();
//		ontology.loadOntology("./resources/ontology/FILIET_Ontology2.owl");
		try {

			for (Sentence sentence : sentences) {
				System.out.println(sentence.getTweets().getTweetID());

				
				String category = sentence.getTweets().getCategory();
				
				if(category.equalsIgnoreCase("CA")){
					
				//	ontology.addCautionAndAdviceReport(Binder.bindCA(s));
				} else if(category.equalsIgnoreCase("CD")){
					
				//	ontology.addCasualtiesAndDamageReport(Binder.bindCD(s));
				} else if(category.equalsIgnoreCase("CH")){
					
					
				//	ontology.addCallForHelpReport(Binder.bindCH(s));
				} else if(category.equalsIgnoreCase("D")){
					Sentence s = extractorEngine.runExtractor(sentence);
					processedSentences.add(s);
				//	ontology.addDonationReport(Binder.bindD(s));
				} 

			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		XmlParser.saveXML(processedSentences,
				"./resources/results/hypothesis/mario-d-no-loc.xml");

		
	}
}
