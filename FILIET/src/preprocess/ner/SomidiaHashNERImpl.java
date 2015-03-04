package preprocess.ner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import support.model.Sentence;
import support.model.Token;

public class SomidiaHashNERImpl implements NERInterface {

	private Map<String, String> lookup;

	public SomidiaHashNERImpl() {
		File file = new File("./resources/NamedEntityRecognizerDictModel");
		Scanner s = null;
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		lookup = new HashMap<String, String>();

		String category = null;
		while (s.hasNextLine()) {
			String line = s.nextLine();
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

		s.close();
	}

	@Override
	public Sentence execute(Sentence tweet) {
		// TODO Auto-generated method stub
		for (int i = 0; i < tweet.getLength(); i++) {
			Token token = tweet.getToken(i);
			String ner = lookup.get(token.getWord().toLowerCase());
			if (ner != null) {
				token.setNERTag(ner);
				token.setPOSTag("NN");
				tweet.replaceToken(i, token);
			}
		}

		return tweet;
	}

}
