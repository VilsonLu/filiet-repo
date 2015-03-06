import informationextraction.InformationExtractionEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import support.model.PostExtractedInformation;
import support.model.Sentence;
import support.other.XmlParser;
import testing.Testing;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String testTweets = "./resources/tweets/Testing/Test.csv";

		System.out
				.println("FILIET - Filipino Information Extraction for Twitter");
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

		List<Sentence> processedSentences = new ArrayList<Sentence>();

		// String text =
		// "RT @DZMMTeleRadyo: #walangpasok | Klase sa lahat ng antas sa Mandaluyong City, suspendido bukas (Dec. 8) dahil kay #RubyPH - Mayor Abalos";
		for (Sentence sentence : sentences) {
			System.out.println(sentence.getTweets().getTweetID());
			processedSentences.add(extractorEngine.runExtractor(sentence));
		}
		
		for(Sentence sentence: processedSentences){
			List<PostExtractedInformation> info = sentence.getExtractedInformation();
			for(PostExtractedInformation i :info){
				i.printText();
			}
		}


	}

}
