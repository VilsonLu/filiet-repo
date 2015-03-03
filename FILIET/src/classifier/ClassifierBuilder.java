package classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Sentence;
import model.Tweet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class ClassifierBuilder {

	private String wordPath = "./resources/model/word/ruby-word";
	private String ngramPath = "./resources/model/ngram/ruby-ngram";

	private FastVector wekaAttributes;
	
	public ClassifierBuilder() {
		buildFeatures();
	}

	public ClassifierBuilder(String word, String ngram) {
		this.wordPath = word;
		this.ngramPath = ngram;
		buildFeatures();
	}
	
	/**
	 * @return the wekaAttributes
	 */
	public FastVector getWekaAttributes() {
		return wekaAttributes;
	}

	/**
	 * @param wekaAttributes the wekaAttributes to set
	 */
	public void setWekaAttributes(FastVector wekaAttributes) {
		this.wekaAttributes = wekaAttributes;
	}

	/**
	 * This builds the features for the classifier
	 */
	@SuppressWarnings("resource")
	public void buildFeatures() {
		// Attributes
		Attribute tweetID = new Attribute("tweetID");
		Attribute user = new Attribute("user",(FastVector) null);
		Attribute tweet = new Attribute("tweet",(FastVector) null);
		Attribute longitude = new Attribute("longitude");
		Attribute latitude = new Attribute("latitude");
		Attribute isURL = new Attribute("isURL");
		Attribute isHashtag = new Attribute("isHashtag");
		Attribute isRetweet = new Attribute("isRetweet");
		FastVector languageVector = new FastVector();
		languageVector.addElement("tl");
		Attribute language = new Attribute("language",languageVector);

		// N-Word

		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		List<Attribute> words = null;

		try {

			file = new File(wordPath);
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			words = new ArrayList<Attribute>();
			String line = null;
			while ((line = br.readLine()) != null) {
				String name = line.split(" ")[0];
				Attribute attribute = new Attribute(name);
				words.add(attribute);
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Word
		List<Attribute> ngrams = null;
		try {
			file = new File(ngramPath);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			ngrams = new ArrayList<Attribute>();
			String line = null;
			while ((line = br.readLine()) != null) {
				String name = line.split(" ")[0];
				Attribute attribute = new Attribute(name);
				ngrams.add(attribute);
			}

			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Class
		FastVector classAttribute = new FastVector();
		classAttribute.addElement("CA");
		classAttribute.addElement("CD");
		classAttribute.addElement("CH");
		classAttribute.addElement("D");
		classAttribute.addElement("O");
		Attribute category = new Attribute("category", classAttribute);

		wekaAttributes = new FastVector();
		wekaAttributes.addElement(tweetID);
		wekaAttributes.addElement(user);
		wekaAttributes.addElement(tweet);
		wekaAttributes.addElement(longitude);
		wekaAttributes.addElement(latitude);
		wekaAttributes.addElement(isURL);
		wekaAttributes.addElement(isHashtag);
		wekaAttributes.addElement(isRetweet);
		wekaAttributes.addElement(language);

		for (Attribute word : words) {
			wekaAttributes.addElement(word);
		}

		for (Attribute ngram : ngrams) {
			wekaAttributes.addElement(ngram);
		}

		wekaAttributes.addElement(category);
		
	}
	
	public Instances createInstances(){
		Instances dataset = new Instances("rel", wekaAttributes, 1);
		dataset.setClassIndex(wekaAttributes.size()-1);
		return dataset;
	}
	
	public void setInstance(Instances dataset, Sentence sentence){
		Instance instance = new Instance(dataset.numAttributes());
		Map<String,Integer> wordValues = sentence.getExtractedWordFeatures();
		Map<String,Integer> ngramValues = sentence.getExtractedNgramFeatures();
		Tweet tweet = sentence.getTweets();
		instance.setValue(((Attribute) wekaAttributes.elementAt(0)), tweet.getTweetID());
		instance.setValue(((Attribute) wekaAttributes.elementAt(1)), tweet.getUser());
		instance.setValue(((Attribute) wekaAttributes.elementAt(2)), tweet.getTweet());
		
		if(tweet.getLatitude() != null){
			instance.setValue(((Attribute) wekaAttributes.elementAt(3)), tweet.getLatitude());
		} else {
			instance.setMissing(((Attribute) wekaAttributes.elementAt(3)));
		}
		if(tweet.getLongitude() != null){
			instance.setValue(((Attribute) wekaAttributes.elementAt(4)), tweet.getLongitude());
		} else {
			instance.setMissing(((Attribute) wekaAttributes.elementAt(4)));
		}
		
		instance.setValue(((Attribute) wekaAttributes.elementAt(5)), getBooleanValue(tweet.getURL()));
		instance.setValue(((Attribute) wekaAttributes.elementAt(6)), getBooleanValue(tweet.getHashtag()));
		instance.setValue(((Attribute) wekaAttributes.elementAt(7)), getBooleanValue(tweet.getHashtag()));
		instance.setValue(((Attribute) wekaAttributes.elementAt(8)), tweet.getLanguage());
		
		// To keep track of the current index
		int currentIndex = 9 ;
		int wordsize = wordValues.size() + currentIndex;
		for(int i=9; i<wordsize; i++){
			String attributeName = ((Attribute) wekaAttributes.elementAt(i)).name();
			instance.setValue(((Attribute) wekaAttributes.elementAt(i)), wordValues.get(attributeName));
			currentIndex++;
		}
		
		int ngramsize = ngramValues.size() + currentIndex;
		System.out.println("Ngram Size: " + ngramsize);
		System.out.println("Current Index: "+ currentIndex);
		for(int i=currentIndex; i<ngramsize; i++){
			
		}
		
		// Class Attribute
		instance.setValue(((Attribute) wekaAttributes.elementAt(wekaAttributes.size()-1)), tweet.getTweetID());
	}
	
	
	// Support method
	private int getBooleanValue(Boolean value) {
		if (value)
			return 1;
		else
			return 0;
	}
}
