package main;
import informationextraction.InformationExtractionEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ontology.OntologyModule;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import binder.Binder;
import support.model.PostExtractedInformation;
import support.model.Sentence;
import support.other.XmlParser;
import testing.Reader;
import ui.mainUI;

public class Test {

	public void startIE(String filepath) throws Exception {
		// TODO Auto-generated method stub
		String testTweets = filepath;
		String ontologyPath = "./resources/ontology/Ruby_OWL.owl";

		System.out
				.println("FILIET - Filipino Information Extraction for Twitter");
		System.out.println("Running: " + testTweets);
		System.out.println();

		InformationExtractionEngine extractorEngine = new InformationExtractionEngine();
		OntologyModule ontology = new OntologyModule();
		ontology.loadOntology(ontologyPath);
		
		List<Sentence> sentences = null;
		try {
			sentences = Reader.readCSVFile(testTweets);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Sentence> processedSentences = new ArrayList<Sentence>();

		// String text =
		// "RT @DZMMTeleRadyo: #walangpasok | Klase sa lahat ng antas sa Mandaluyong City, suspendido bukas (Dec. 8) dahil kay #RubyPH - Mayor Abalos";
		for (Sentence sentence : sentences) {
			System.out.println(sentence.getTweets().getTweetID());
			processedSentences.add(extractorEngine.runExtractor(sentence));

		}

		for (Sentence sentence : processedSentences) {
			if (sentence.getTweets().getCategory().equalsIgnoreCase("CA")) {
				CautionAndAdviceTweet ca = Binder.bindCA(sentence);
				System.out.println("Advice: " + ca.getTweetAdvice());
				System.out.println("Location in Tweet "
						+ ca.getLocationInTweet());
				System.out.println("Content " + ca.getTweetContent());
				System.out.println("User " + ca.getTweetHandle());
				System.out.println("Date " + ca.getTweetDate());
				System.out.println("Timestamp " + ca.getTweetTimestamp());
				System.out.println("Geolocation " + ca.getTweetGeoLocation());
			} else if(sentence.getTweets().getCategory().equalsIgnoreCase("CD")){
				CasualtiesAndDamageTweet cd = Binder.bindCD(sentence);
				System.out.println("Detail " + cd.getObjectDetails() );
				System.out.println("Object Name " + cd.getObjectName() );
				System.out.println("Location in Tweet "
						+ cd.getLocationInTweet());
				System.out.println("Content " + cd.getTweetContent());
				System.out.println("User " + cd.getTweetHandle());
				System.out.println("Date " + cd.getTweetDate());
				System.out.println("Timestamp " + cd.getTweetTimestamp());
				System.out.println("Geolocation " + cd.getTweetGeoLocation());
				ontology.addCasualtiesAndDamageReport(cd);
			}
			
			ontology.displayStoredTweets();
			System.out.println();
		}
		
		ontology.removeOntologyFromManager();
	}

}
