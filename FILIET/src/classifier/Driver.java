package classifier;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import classifier.implementations.Classifier;
import classifier.implementations.ClassifierImpl;
import informationextraction.InformationExtractionEngine;
import support.model.Sentence;
import testing.Testing;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;

public class Driver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String testTweets = "./resources/tweets/mario-datasets/original/mario-combined.csv";
		InformationExtractionEngine ie = new InformationExtractionEngine();
		double nano = 1000000000.0;
		try {
			List<Sentence> sentences = Testing.readTestData(testTweets);
					
			Classifier classifier = new Classifier(new ClassifierImpl("./resources/model/classifier/testmodel.model"));
			//for(Sentence sentence: sentences){
				
				Sentence temp = ie.runExtractor(sentences.get(0));
				long start = System.nanoTime();
				classifier.executeStrategy(temp);
				long end = System.nanoTime();
				double time = ((double)(end - start))/nano;
				
				
				System.out.println("Actual: " + temp.getTweets().getCategory());
				System.out.println("Time: " + time);
			//}
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
