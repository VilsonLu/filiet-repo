package classifier;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import model.Sentence;
import model.Tweet;

public class ClassifierDriver {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassifierInterface classifier = new kNNClassifier();
		
		Sentence sentence = new Sentence();
		// Dummy Data
		Tweet sample = new Tweet();
		sample.setLatitude(0.69245);
		sample.setLongitude(1.67845);
		sample.setTweet("Victoneta Subdivision sa Bgy. Potrero Malabon lubog na sa baha. | @DZMMTeleRadyo http://t.co/7aJtIXHfZw via @johnsonmanabat");
		// 512849721635782000
		sample.setTweetID(((long)512849712));
		sample.setUser("Malabon_City");
		sample.setHashtag(false);
		sample.setRetweet(true);
		sample.setURL(false);
		sample.setLanguage("tl");
	
		
		sentence.setTweets(sample);
		
		System.out.println(classifier.classify(sentence));
		
	}

}
