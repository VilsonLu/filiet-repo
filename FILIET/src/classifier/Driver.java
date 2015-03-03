package classifier;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import model.Sentence;
import informationextraction.InformationExtractionEngine;
import testing.Testing;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String testTweets = "./resources/tweets/ruby-datasets/original/combined-bin-d.csv";
		InformationExtractionEngine ie = new InformationExtractionEngine();
		
		try {
			List<Sentence> sentences = Testing.readTestData(testTweets);
			Sentence sentence = sentences.get(0);
			sentence = ie.runExtractor(sentence);
			
			ClassifierBuilder featureBuilder = new ClassifierBuilder();
			FastVector weka = featureBuilder.getWekaAttributes();
			
			Instances dataset = featureBuilder.createInstances();
			System.out.println("Total Attributes: " + dataset.numAttributes());
			featureBuilder.setInstance(dataset, sentence);
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		for(int i=0; i<weka.size(); i++){
//			System.out.println(((Attribute)weka.elementAt(i)).name());
//		}
	}

}
