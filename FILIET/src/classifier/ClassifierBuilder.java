package classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import support.model.Sentence;
import support.model.Tweet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class ClassifierBuilder {

	private String wordPath = "./resources/model/word/ruby-word";
	private String ngramPath = "./resources/model/ngram/ruby-ngram";

	private FastVector wekaAttributes;
	private Instances dataset;
	
	public ClassifierBuilder() throws Exception {
		initialize();
	}

	public ClassifierBuilder(String word, String ngram) throws Exception {
		this.wordPath = word;
		this.ngramPath = ngram;
		initialize();
	}
	
	public void initialize() throws Exception{
		buildFeatures();
		createInstances();
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
	 * @return the dataset
	 */
	public Instances getDataset() {
		return dataset;
	}

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(Instances dataset) {
		this.dataset = dataset;
	}

	/**
	 * This builds the features for the classifier
	 */
	@SuppressWarnings("resource")
	private void buildFeatures() {
		// Attributes
		Attribute tweetID = new Attribute("tweetID");
		Attribute user = new Attribute("user",(FastVector) null);
		Attribute tweet = new Attribute("tweet",(FastVector) null);
		Attribute longitude = new Attribute("longitude");
		Attribute latitude = new Attribute("latitude");
		Attribute isURL = new Attribute("isURL");
		Attribute isHashtag = new Attribute("isHashtag");
		Attribute isRetweet = new Attribute("isRetweet");
		Attribute length = new Attribute("length");
		FastVector languageVector = new FastVector();
		languageVector.addElement("tl");
		languageVector.addElement("en");
		languageVector.addElement("pt");
		languageVector.addElement("in");
		languageVector.addElement("tr");
		languageVector.addElement("es");
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
		wekaAttributes.addElement(length);
		wekaAttributes.addElement(language);
		

		for (Attribute word : words) {
			wekaAttributes.addElement(word);
		}

		for (Attribute ngram : ngrams) {
			wekaAttributes.addElement(ngram);
		}

		wekaAttributes.addElement(category);
		
	}
	
	private void createInstances() throws Exception{
		dataset = new Instances("rel", wekaAttributes, 1);
		
		// Remove the tweetID (do not include in the computation)
//		Remove rm = new Remove();
//		rm.setAttributeIndices("1");
//		rm.setInputFormat(dataset);
//		dataset = Filter.useFilter(dataset, rm);
		
		// Remove the TweetID in the list of attribute
		//wekaAttributes.removeElementAt(0);

		dataset.setClassIndex(dataset.numAttributes()-1);
	}
	
	public Instance setInstance(Sentence sentence){
		
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
		instance.setValue(((Attribute) wekaAttributes.elementAt(8)), sentence.getExtractedFeatures().get("Length"));
		instance.setValue(((Attribute) wekaAttributes.elementAt(9)), tweet.getLanguage());
		
		// Word Features
		// To keep track of the current index
		int currentIndex = 10 ;
		int wordsize = wordValues.size() + currentIndex;
		for(int i=currentIndex; i<wordsize; i++){
			String attributeName = ((Attribute) wekaAttributes.elementAt(i)).name();
			instance.setValue(((Attribute) wekaAttributes.elementAt(i)), wordValues.get(attributeName));
			currentIndex++;
		}
		
		// N-Gram Features
		int ngramsize = ngramValues.size() + currentIndex;
		for(int i=currentIndex; i<ngramsize; i++){
			String attributeName = ((Attribute) wekaAttributes.elementAt(i)).name();
			instance.setValue(((Attribute) wekaAttributes.elementAt(i)), ngramValues.get(attributeName));
			currentIndex++;
		}
		
		// Class Attribute
		instance.setValue(((Attribute) wekaAttributes.lastElement()), tweet.getCategory());
		instance.setDataset(dataset);
		return instance;
	}
	
	
	// Support method
	private int getBooleanValue(Boolean value) {
		if (value)
			return 1;
		else
			return 0;
	}
}
