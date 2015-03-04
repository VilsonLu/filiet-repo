package preprocess.postagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import support.model.Sentence;
import support.model.Token;

/**
 * 
 * @author Vilson Using POS Lookup for the implementation of POS Tagger
 */
public class POSLookupImpl implements POSTaggerInterface {

	@Override
	public Sentence execute(Sentence tokens) {

		// TODO Auto-generated method stub

		File PosDictionary = new File("./resources/posDictionary");
		Scanner s = null;

		for (int i = 0; i < tokens.getLength(); i++) {
			try {
				s = new Scanner(PosDictionary);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while (s.hasNextLine()) {
				String line = s.nextLine();
				String tagToken[] = line.split("\\s+");
				Token token = tokens.getToken(i);
				if (token.getWord().equalsIgnoreCase(tagToken[0])) {
					if (tagToken[2].equalsIgnoreCase("ENG")
							|| tagToken[2].equalsIgnoreCase("TAG")) {
						token.setPOSTag(tagToken[3]);

					} else {
						token.setPOSTag(tagToken[2]);

					}
					tokens.replaceToken(i, token);
				}
			}
			s.close();
		}

		return tokens;

	}
}
