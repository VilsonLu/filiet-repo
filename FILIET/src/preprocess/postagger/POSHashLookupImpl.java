package preprocess.postagger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Sentence;
import model.Token;

public class POSHashLookupImpl implements POSTaggerInterface{

	private Map<String, String> dictionary;

	public POSHashLookupImpl() {
		dictionary = new HashMap<String, String>();
		File PosDictionary = new File("./resources/posDictionary");
		
		Scanner s;
		try {
			s = new Scanner(PosDictionary);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String tagToken[] = line.split("\\s+");

				if (tagToken[2].equalsIgnoreCase("ENG")
						|| tagToken[2].equalsIgnoreCase("TAG")) {
					dictionary.put(tagToken[0].toLowerCase(), tagToken[3]);
				} else {
					dictionary.put(tagToken[0], tagToken[2]);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Sentence execute(Sentence tokens){
		for(int i=0; i<tokens.getLength(); i++){
			Token token = tokens.getToken(i);
			token.setPOSTag(dictionary.get(token.getWord().toLowerCase()));
			tokens.replaceToken(i, token);
		}
		return tokens;
	}


}
