package preprocess.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import support.model.Sentence;
import support.model.Token;

public class SomidiaHashNERImpl implements NERInterface {

	private Map<String, String> lookup;

	public SomidiaHashNERImpl() {
		File file = new File("./resources/NamedEntityRecognizerDictModel");
		BufferedReader br = null;
		lookup = new HashMap<String, String>();
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			String category = "unknown";
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.contains("UNIT")) {
					category = "unit";
				} else if (line.contains("LOCATION")) {
					category = "location";
				} else if (line.contains("MONTH")) {
					category = "month";
				} else {
					lookup.put(line, category);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void test(String text) {
		String result = lookup.get(text);
		System.out.println(result + " " + text);
	}

	@Override
	public Sentence execute(Sentence tweet) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tweet.getLength(); i++) {
			Token token = tweet.getToken(i);
			String ner = lookup.get(token.getWord().trim().toLowerCase());
			if (ner != null) {
				token.setNERTag(ner);
				tweet.replaceToken(i, token);
			}
		}

		return tweet;
	}

}
