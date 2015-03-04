package classifier;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import classifier.implementations.Classifier;
import classifier.implementations.ClassifierImpl;
import model.Sentence;
import informationextraction.InformationExtractionEngine;
import testing.Testing;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

public class Driver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String testTweets = "./resources/tweets/mario-datasets/original/mario-combined.csv";
		InformationExtractionEngine ie = new InformationExtractionEngine();
		
		try {
			List<Sentence> sentences = Testing.readTestData(testTweets);
		
			
			ClassifierBuilder featureBuilder = new ClassifierBuilder();
			FastVector weka = featureBuilder.getWekaAttributes();
			
			Classifier classifier = new Classifier(new ClassifierImpl("./resources/model/classifier/testmodel.model"));
			for(Sentence sentence: sentences){
				Sentence temp = ie.runExtractor(sentence);
				classifier.executeStrategy(temp);
				System.out.println("Actual: " + temp.getTweets().getCategory());
			}
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		for(int i=0; i<weka.size(); i++){
//			System.out.println(((Attribute)weka.elementAt(i)).name());
//		}
	}

}
