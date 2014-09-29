package featureextraction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Sentence;
import model.Token;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;

import com.csvreader.CsvReader;

public class FeatureExtractor {

	private List<String> wordfeatures;
	private List<String> ngramfeatures;
	private HashMap<String, Integer> extractedFeatures;
	public static final int WORD = 0;
	public static final int NGRAM = 1;

	public FeatureExtractor() {
		extractedFeatures = new HashMap<>();
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

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Extracting features for a collection tweets
	 * 
	 * @param path
	 */
	public void extractFeatures(String path) {

		try {
			char delimiter = ';';
			CsvReader csvReader = new CsvReader(path, delimiter);
			csvReader.readHeaders();
			for (String header : csvReader.getHeaders()) {
				System.out.println(header);
			}
			// System.out.println(csvReader.readRecord());
			while (csvReader.readRecord()) {
				for (int i = 0; i < csvReader.getColumnCount(); i++) {
					System.out.print(csvReader.get(i) + " ");
				}
				System.out.println();
			}

			csvReader.close();
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
	 * @param type - tells if it is ngram or word features
	 */
	private void initHashMap(int type) {
		if (type == FeatureExtractor.WORD) {
			for (String word : wordfeatures) {
				extractedFeatures.put(word, 0);
			}
		} else if(type == FeatureExtractor.NGRAM){
			for(String ngram: ngramfeatures){
				extractedFeatures.put(ngram, 0);
			}
		}
	}

	public void extractWordFeatures(List<Token> tokens) {
		HashMap<String, Integer> wordcount = new HashMap<>();
		for (Token token : tokens) {
			for (String word : wordfeatures) {
				String temp = token.getWord();
				if (temp.equalsIgnoreCase(word)) {
					extractedFeatures.put(temp, extractedFeatures.get(temp) + 1);
				}
			}
		}
	}

	public void extract(String text) {
		Tokenizer tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());

		try {
			Sentence tweet = tokenizer.executeStrategy(text);
			List<Token> tokens = tweet.getSentence();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
