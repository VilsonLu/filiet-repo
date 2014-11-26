
import model.Sentence;
import model.Tweet;
import classifier.implementations.ClassifierInterface;
import classifier.implementations.KNNClassifierImpl;
import featureextraction.FeatureExtractor;


public class ClassifierDriver {
	public static void main(String[] args){
		
	}
		String ngram = "./resources/model/ngram/char-ngram";
		String word = "./resources/model/wordcount/wordcounts";
		String modelPath = "./resources/model/classifier/kNN-2.model";
		// TODO Auto-generated method stub
		ClassifierInterface classifier = new KNNClassifierImpl(modelPath);
		FeatureExtractor fe = new FeatureExtractor(word,ngram);
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
		sample.setRetweet(false);
		sample.setURL(true);
		sample.setLanguage("tl");
	
		
		sentence.setTweets(sample);
		fe.extract(sentence);
		
	
		System.out.println(classifier.classify(sentence));
}
