package languagemodeller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import languagemodeller.NGramModeller.CustomComparator;

public class WeightScorer {

	private DocumentFrequency categoryDataset;
	private DocumentFrequency documentDataset;

	private Map<String, Double> weights;
	private List<String> stopwords;
	private static String wordAffix = "W_";

	private String stopWordPath = "./resources/model/filipino-stopwords";

	public WeightScorer(DocumentFrequency categoryDataset,
			DocumentFrequency documentDataset) {
		this.categoryDataset = categoryDataset;
		this.documentDataset = documentDataset;
		weights = new HashMap<String, Double>();

		try {
			loadStopWords();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Gets the list of stopwords from a file
	private void loadStopWords() throws IOException {
		stopwords = new ArrayList<String>();
		File file = new File(stopWordPath);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String entry = null;
		while ((entry = br.readLine()) != null) {
			stopwords.add(entry);
		}
	}

	private Double computeTF(String term) {
		int frequency = categoryDataset.getWordFrequency(term);

		if (frequency > 0) {
			return 1 + Math.log(frequency);
		}

		return 0.0;
	}

	private Double computeTFIDF(String term) {
		Double idf = Math.log(documentDataset.getDocumentCount()
				/ documentDataset.getWordFrequency(term));
		Double tf = computeTF(term);
		return tf * idf;
	}

	/*
	 * Computes the TFIDF scores of all the words in the list
	 */
	public Map<String, Double> computeWeights() {

		List<String> vocabularies = categoryDataset.getListOfWords();
		System.out.println(vocabularies.size());
		for (String word : vocabularies) {
			weights.put(word, computeTFIDF(word));
			System.out.println(word + ":" + weights.get(word));
		}

		return weights;
	}

	public void getTop(String saveFile, int n) throws IOException {

		List<Map.Entry<String, Double>> entries = new ArrayList<>();
		int i = 0;

		Set<Map.Entry<String, Double>> tfidfWeights = weights.entrySet();
		for (Map.Entry<String, Double> entry : tfidfWeights) {

			if (isValid(entry.getKey())) {

				if (i < n) {
					entries.add(entry);
					i++;
				} else {
					int min = -1;
					for (int j = 0; j < n; j++) {
						if (entries.get(j).getValue() < entry.getValue()) {
							min = j;
						}
					}

					if (min > -1) {
						entries.remove(min);
						entries.add(entry);
					}
				}

			} else {
				System.out.println("Else stopword " + entry.getKey());
			}
		}

		// Sort the list into ascending order
		Collections.sort(entries, new CustomComparator());

		/*
		 * int k = 1; for (Map.Entry<String, Integer> entry : entries) {
		 * System.out.println(k + " " + entry.getKey() + ": " +
		 * entry.getValue()); k++; }
		 */

		this.saveResults(entries, saveFile);

	}

	private Boolean isValid(String word) {
		// removes punctuation marks

		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(word);
		if (m.find()) {

		}
		String punctuation = "”“@â€¦.,?!@:;()-'|\"\\/â€¦‘";
		if (punctuation.contains(word)) {
			return false;
		}

		// removes urls
		String url = "\\b(?:http|https):\\/{2}\\S+\\b";
		if (word.matches(url)) {
			return false;
		}

		// removes numbers
		if (word.matches("[0-9]+")) {
			return false;
		}

		// removes stopwords
		for (String stop : stopwords) {
			if (stop.equalsIgnoreCase(word))
				return false;
		}

		return true;
	}

	public void saveResults(String path) throws FileNotFoundException {
		System.out.println("Saving model...");
		PrintWriter writer = new PrintWriter(path);
		Set<Map.Entry<String, Double>> entries = weights.entrySet();
		for (Map.Entry<String, Double> entry : entries) {
			writer.println(entry.getKey() + " " + entry.getValue());
		}
		writer.close();
		System.out.println("Saving successful...");
	}

	public void saveResults(List<Map.Entry<String, Double>> lists, String path)
			throws FileNotFoundException {
		System.out.println("Saving model...");
		PrintWriter writer = new PrintWriter(path);
		List<Map.Entry<String, Double>> entries = lists;
		for (Map.Entry<String, Double> entry : entries) {
			writer.println(wordAffix + entry.getKey() + " " + entry.getValue());
		}
		writer.close();
		System.out.println("Saving successful...");
	}

	class CustomComparator implements Comparator<Map.Entry<String, Double>> {
		@Override
		public int compare(Map.Entry<String, Double> o1,
				Map.Entry<String, Double> o2) {
			return o1.getValue().compareTo(o2.getValue());
		}
	}

}
