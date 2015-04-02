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

	public final static String CA = "CA";
	public final static String CD = "CD";
	public final static String CH = "CH";
	public final static String D = "D";
	public final static String ANY = "ANY";
	
	private String wordPath = null;
	private String ngramPath = null;

	private FastVector wekaAttributes;
	private FastVector classLabel;
	private Instances dataset;
	
	private int wordCount = 0;;
	private int ngramCount = 0;
	
	public ClassifierBuilder(FastVector classLabel) throws Exception {
		initialize();
	}

	/**
	 * 
	 * @param word
	 *            - word path
	 * @param ngram
	 *            - ngram path
	 * @param classLabel
	 *            - class labels
	 * @throws Exception
	 */
	public ClassifierBuilder(String word, String ngram, FastVector classLabel)
			throws Exception {
		this.wordPath = word;
		this.classLabel = classLabel;
		initialize();
	}

	public void initialize() throws Exception {
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
	 * @param wekaAttributes
	 *            the wekaAttributes to set
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
	 * @param dataset
	 *            the dataset to set
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
		Attribute isURL = new Attribute("isURL");
		Attribute isHashtag = new Attribute("isHashtag");
		Attribute isRetweet = new Attribute("isRetweet");
		Attribute length = new Attribute("length");

		// Word
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;
		List<Attribute> words = null;

		if (wordPath != null) {
			try {

				file = new File(wordPath);
				fr = new FileReader(file);
				br = new BufferedReader(fr);

				words = new ArrayList<Attribute>();
				String line = null;
				while ((line = br.readLine()) != null) {
					String name = line.split("\t")[0];
					Attribute attribute = new Attribute(name);
					words.add(attribute);
					wordCount++;
				}

				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Ngram
		List<Attribute> ngrams = null;
		if (ngramPath != null) {
			try {
				file = new File(ngramPath);
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				ngrams = new ArrayList<Attribute>();
				String line = null;
				while ((line = br.readLine()) != null) {
					String name = line.split("\t")[0];
					Attribute attribute = new Attribute(name);
					ngrams.add(attribute);
					ngramCount++;
				}

				br.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Class
		Attribute category = new Attribute("category", classLabel);

		wekaAttributes = new FastVector();
		wekaAttributes.addElement(isURL);
		wekaAttributes.addElement(isHashtag);
		wekaAttributes.addElement(isRetweet);
		wekaAttributes.addElement(length);

		if (words != null) {
			for (Attribute word : words) {
				wekaAttributes.addElement(word);
			}
		}

		if (ngrams != null) {
			for (Attribute ngram : ngrams) {
				wekaAttributes.addElement(ngram);
			}
		}

		wekaAttributes.addElement(category);

	}

	private void createInstances() throws Exception {
		dataset = new Instances("rel", wekaAttributes, 1);
		dataset.setClassIndex(dataset.numAttributes() - 1);
	}

	public Instance setInstance(Sentence sentence, String classifierType) {

		Instance instance = new Instance(dataset.numAttributes());
		Map<String, Integer> wordValues = sentence.getExtractedWordFeatures();
		Map<String, Integer> ngramValues = sentence.getExtractedNgramFeatures();
		Tweet tweet = sentence.getTweets();

		instance.setValue(((Attribute) wekaAttributes.elementAt(0)),
				getBooleanValue(tweet.getURL()));
		instance.setValue(((Attribute) wekaAttributes.elementAt(1)),
				getBooleanValue(tweet.getHashtag()));
		instance.setValue(((Attribute) wekaAttributes.elementAt(2)),
				getBooleanValue(tweet.getHashtag()));
		instance.setValue(((Attribute) wekaAttributes.elementAt(3)), sentence
				.getExtractedFeatures().get("Length"));

		// Word Features
		// To keep track of the current index
		int currentIndex = 4;
		int wordsize = wordCount + currentIndex;

		if (wordPath != null) {
			for (int i = currentIndex; i < wordsize; i++) {
				String attributeName = null;
				attributeName = ((Attribute) wekaAttributes.elementAt(i)).name();

				if (wordValues.get(attributeName) != null) {
					
					instance.setValue(
							((Attribute) wekaAttributes.elementAt(i)),
							wordValues.get(attributeName));
					
					currentIndex++;
				}
		
			}
		}

		// N-Gram Features
		if (ngramPath != null) {
			int ngramsize = ngramCount + currentIndex;
			for (int i = currentIndex; i < ngramsize; i++) {
				String attributeName = ((Attribute) wekaAttributes.elementAt(i))
						.name();
				instance.setValue(((Attribute) wekaAttributes.elementAt(i)),
						ngramValues.get(attributeName));
				currentIndex++;
			}
		}

		// Class Attribute
		String category = null;
		
		if(!tweet.getCategory().equalsIgnoreCase(classifierType)){
			category = "O";
		} else if(classifierType.equalsIgnoreCase("ANY")){
			category = tweet.getCategory();
		}
		
//		instance.setValue(((Attribute) wekaAttributes.lastElement()),category);
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
