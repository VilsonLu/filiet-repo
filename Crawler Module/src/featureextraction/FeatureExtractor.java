package featureextraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.Sentence;
import model.Token;
import model.Tweet;
import opennlp.tools.ngram.NGramModel;
import opennlp.tools.util.StringList;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import au.com.bytecode.opencsv.CSVWriter;

import com.csvreader.CsvReader;

public class FeatureExtractor {

	public static final int WORD = 0;
	public static final int NGRAM = 1;
	public static final int TWEET = 2;

	
	/*
	 * Headers
	 */
	private List<String> wordfeatures;
	private List<String> ngramfeatures;

	/*
	 * This is where the n-grams, word features, and tweet length values are
	 * stored
	 */
	private HashMap<String, Integer> extractedWordFeatures;
	private HashMap<String, Integer> extractedNgramFeatures;
	private HashMap<String, Integer> extractedFeatures;

	/*
	 * Label Index
	 */
	private int classIndex;

	/**
	 * @param wordModel - files that contained t list of words
	 * @param ngramModel - files that contained th
	 */
	public FeatureExtractor(String wordModel, String ngramModel) {
		readModel(wordModel, FeatureExtractor.WORD);
		readModel(ngramModel, FeatureExtractor.NGRAM);
	}

	/**
	 * @return the wordfeatures
	 */
	public List<String> getWordfeatures() {
		return wordfeatures;
	}

	/**
	 * @param wordfeatures
	 *            the wordfeatures to set
	 */
	public void setWordfeatures(List<String> wordfeatures) {
		this.wordfeatures = wordfeatures;
	}

	/**
	 * @return the ngramfeatures
	 */
	public List<String> getNgramfeatures() {
		return ngramfeatures;
	}

	/**
	 * @param ngramfeatures
	 *            the ngramfeatures to set
	 */
	public void setNgramfeatures(List<String> ngramfeatures) {
		this.ngramfeatures = ngramfeatures;
	}
	
	

	/**
	 * @return the classIndex
	 */
	public int getClassIndex() {
		return classIndex;
	}

	/**
	 * @param classIndex the classIndex to set
	 */
	public void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	/**
	 * Reads a model file
	 * 
	 * @param path
	 *            - the path to the model
	 * @param type
	 *            - tells if it is a word model or n-gram model
	 */
	public void readModel(String path, int type) {

		List<String> lists = new ArrayList<>();

		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			String line = null;

			while ((line = br.readLine()) != null) {
				String[] row = line.split(" ");
				lists.add(row[0]);
			}

			switch (type) {
			case 0:
				wordfeatures = lists;
				break;
			case 1:
				ngramfeatures = lists;
				break;
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

	/**
	 * Extracting features for a collection of tweets
	 * 
	 * @param path
	 *            - source of the collection of tweets
	 * @param save
	 *            - the path/filename
	 */

	// Still under construction (no impacts on other classes)
	public void extractFeatures(String path, String save) {
		List<String> headers = new ArrayList<>();
		List<String[]> rows = new ArrayList<>();
		try {

			/*
			 * The reader
			 */
			
			char delimiter = ';';
			CsvReader csvReader = new CsvReader(path, delimiter);
			int headerCount = 0;
			int rowCount = 0;
			/*
			 * The writer
			 */
			File file = new File(save);
			FileWriter fw = new FileWriter(file, true);
			CSVWriter csvWriter = new CSVWriter(fw,',','\"');
			csvReader.readHeaders();
			String[] header = csvReader.getHeaders();

			String removePunctuation = "\"";
			String removeSingleQuote = "\'";

			/*
			 * Writes the header
			 */

			classIndex = 0;
			for (String h : header) {
				headers.add(h);
				classIndex++;
				headerCount++;
			}
			
			// headers for word features
			for (String w : wordfeatures) {
				headers.add(w);
				headerCount++;
			}

			//headers for ngram
			 for(String g: ngramfeatures){
				headers.add(g);
				headerCount++;
			
			}
			 
			//headers for length
			headers.add("Length");
			headerCount++;
			
			
			csvWriter.writeNext(headers.toArray(new String[headers.size()]));

			
			while (csvReader.readRecord()) {
				ArrayList<String> row = new ArrayList<>();
	
				String tweet = null;
				rowCount = 0;
				extractedWordFeatures = new HashMap<>();
				for (int i = 0; i < csvReader.getColumnCount(); i++) {
					/*
					 * This removes " and '. The punctuation marks is causing
					 * problem with Weka
				 	 */
					if (i == FeatureExtractor.TWEET) {
						String tempTweet = csvReader.get(i);
						tempTweet = tempTweet.replaceAll(removePunctuation, "");
						tweet = tempTweet.replaceAll(removeSingleQuote, "");
						row.add(tweet);
					

					} else {
						String temp = csvReader.get(i);
						if(temp.equals("NULL")){
							row.add(null);
						} else {
							row.add(temp);
						}
					}
					
					rowCount++;
				}

				/*
				 * Extraction of the features
				 */
				extract(tweet);
				
				/*
				 * Writing to CSV
				 */

				// Word features
				Set<Map.Entry<String, Integer>> entries = extractedWordFeatures.entrySet();
				for (Map.Entry<String, Integer> entry : entries) {
					Integer value = entry.getValue();
					row.add(value.toString());
					rowCount++;
				}
				
				// N-gram features
				entries = extractedNgramFeatures.entrySet();
				for (Map.Entry<String, Integer> entry : entries) {
					Integer value = entry.getValue();
					row.add(value.toString());
					rowCount++;
				}
				
				// Other features
				entries = extractedFeatures.entrySet();
				for (Map.Entry<String, Integer> entry : entries) {
					Integer value = entry.getValue();
					row.add(value.toString());
					rowCount++;
				}
				
				rows.add(row.toArray(new String[row.size()]));
			
				System.out.println(headerCount + ", " + rowCount);
			}
			
			csvWriter.writeAll(rows);
			csvWriter.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the hashmap
	 * 
	 * @param type - tells if it is ngram or word features
	 * 
	 */
	private void initHashMap(int type) {
		if (type == FeatureExtractor.WORD) {
			for (String word : wordfeatures) {
				extractedWordFeatures.put(word, 0);
			}
		} else if (type == FeatureExtractor.NGRAM) {
			for (String ngram : ngramfeatures) {
				extractedNgramFeatures.put(ngram, 0);
			}
		}
	}
	
	/**
	 * This extracts the hashtag feature from a tokenized text
	 * @param tokens
	 */
	
	public void extractHashTag(List<Token> tokens){
		int  isHashtag = 0;
		
		for(Token token: tokens){
			String temp = token.getWord();
			if(temp.startsWith("#")){
				isHashtag = 1;
				break;
			}
		}
		
	}

	/**
	 * This extracts the word features from a tokenized text
	 * 
	 * @param tokens
	 *            - tokenized tweet
	 */
	
	public void extractWordFeatures(List<Token> tokens) {
		
		initHashMap(FeatureExtractor.WORD);
		for (Token token : tokens) {
			for (String word : wordfeatures) {
				String tempToken = token.getWord();
				String tempWord = word.substring(2);
				if (tempToken.equalsIgnoreCase(tempWord)) {
					extractedWordFeatures.put(word,
							extractedWordFeatures.get(word) + 1);
				}
			}
		}
	}
	

	/**
	 * This extracts the n-gram features from a tokenized text
	 * 
	 * @param sentence
	 *            - untokenized tweet
	 */

	public void extractNgramFeatures(String sentence) {
		
		initHashMap(FeatureExtractor.NGRAM);
		NGramModel model = new NGramModel();
		model.add(sentence, 2, 2);

		Iterator<StringList> it = model.iterator();
		while (it.hasNext()) {
			StringList temp = it.next();
			String tempNgram = temp.getToken(0).toString().replace(' ', '_');
			for (String ngram : ngramfeatures) {
				String tempNgramFeature = ngram.substring(2);
				if (tempNgramFeature.equalsIgnoreCase(tempNgram)) {
					extractedNgramFeatures.put(ngram, model.getCount(temp));
				}
			}

		}

	}

	/**
	 * This extracts the tweet length from a tokenized text
	 * 
	 * @param sentence
	 *            - tokenized tweet
	 */
	public void extractTweetLength(List<Token> sentence) {
		
		int numWords = sentence.size();
		extractedFeatures.put("Length", numWords);
	}

	public void extract(String sentence) {

		Tokenizer tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());

			Sentence tweet = tokenizer.executeStrategy(sentence);
			List<Token> tokens = tweet.getSentence();
			
			extractedFeatures = new HashMap<String, Integer>();
			extractedWordFeatures = new HashMap<String, Integer>();
			extractedNgramFeatures = new HashMap<String, Integer>();
			
			extractWordFeatures(tokens);
			extractNgramFeatures(sentence);
			extractTweetLength(tokens);
	
	}

	/**
	 * Extracts the n-gram, word and tweet length features
	 * 
	 * @param sentence
	 *            - the text that will be extracted
	 */

	public void extract(Sentence sentence) {

		List<Token> tokens = sentence.getSentence();
		Tweet tweet = sentence.getTweets();
		
		extractedFeatures = new HashMap<String, Integer>();
		extractedWordFeatures = new HashMap<String, Integer>();
		extractedNgramFeatures = new HashMap<String, Integer>();
		
		extractWordFeatures(tokens);
		extractNgramFeatures(tweet.getTweet());
		extractTweetLength(tokens);
		
		sentence.setExtractedWordFeatures(extractedWordFeatures);
		sentence.setExtractedNgramFeatures(extractedNgramFeatures);
		sentence.setExtractedFeatures(extractedFeatures);

	}

}
