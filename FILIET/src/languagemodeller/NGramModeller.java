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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import opennlp.tools.ngram.NGramModel;
import opennlp.tools.util.StringList;
import cmu.arktweetnlp.Twokenize;
import edu.berkeley.nlp.tokenizer.Tokenizer;

public class NGramModeller {
	private HashMap<String, Integer> frequency;
	private Tokenizer tokenizer;

	public NGramModeller() {
		frequency = new HashMap<>();
	}

	/*
	 * This method counts the frequency of each word in a collection of tweets
	 * 
	 * @param path - this is the path to the csv file
	 * 
	 * @param saveFile - this is the path where the output will be saved
	 * 
	 * @param topN - gets the n-highest frequency
	 */
	public void countWordFrequency(String path, String saveFile, int topN)
			throws IOException {

		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String line = null;
		String split = ";";

		while ((line = br.readLine()) != null) {
			String[] column = line.split(split);
			List<String> tokens = Twokenize.tokenizeRawTweetText(column[2]);
			for (String token : tokens) {
				if (frequency.get(token) == null) {
					frequency.put(token, 1);
				} else {
					frequency.put(token, frequency.get(token) + 1);
				}
			}
		}

		this.getTop(saveFile, topN);
	}

	/*
	 * This method counts the frequency of n-gram
	 */
	public void CharNGram(int ngram, int topN, String saveFile, String path)
			throws IOException {

		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String line = null;
		String split = ";";

		frequency = new HashMap<>();

		NGramModel modeller = new NGramModel();

		while ((line = br.readLine()) != null) {
			String[] fields = line.split(split);
			modeller.add(fields[2], ngram, ngram);
		}

		Iterator<StringList> it = modeller.iterator();
		while (it.hasNext()) {
			StringList temp = it.next();
			String tempString = temp.getToken(0).replace(' ', '_');
			frequency.put(tempString, modeller.getCount(temp));

		}

		this.getTop(saveFile, topN);
	}

	/*
	 * Save the list to a file
	 */
	private void saveFile(List<Map.Entry<String, Integer>> entries, String path)
			throws FileNotFoundException {
		System.out.println("Saving model...");
		PrintWriter writer = new PrintWriter(path);
		for (Map.Entry<String, Integer> entry : entries) {
			writer.println(entry.getKey() + " " + entry.getValue());
		}
		writer.close();
		System.out.println("Saving successful...");
	}

	/*
	 * Get the top N highest word frequency. Result is save to a file.
	 */
	private void getTop(String saveFile, int n) throws IOException {

		List<Map.Entry<String, Integer>> entries = new ArrayList<>();
		int i = 0;

		//String punctuations = "";
		String punctuations = "\".,\'!?:-()|";
		for (Map.Entry<String, Integer> entry : frequency.entrySet()) {
			if (!punctuations.contains(entry.getKey())) {
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

			}

		}

		// Sort the list into ascending order
		Collections.sort(entries, new CustomComparator());

		/*
		 * int k = 1; for (Map.Entry<String, Integer> entry : entries) {
		 * System.out.println(k + " " + entry.getKey() + ": " +
		 * entry.getValue()); k++; }
		 */

		this.saveFile(entries, saveFile);

	}

	class CustomComparator implements Comparator<Map.Entry<String, Integer>> {
		@Override
		public int compare(Map.Entry<String, Integer> o1,
				Map.Entry<String, Integer> o2) {
			return o1.getValue().compareTo(o2.getValue());
		}
	}
}
