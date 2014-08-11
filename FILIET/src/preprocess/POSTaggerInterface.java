package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import data.Sentence;
import data.Token;

public interface POSTaggerInterface {
	Sentence execute(Sentence tokens);
}

/*
 * Using POS Lookup for the implementation of POS Tagger
 */
class POSLookup implements POSTaggerInterface {

	@Override
	public Sentence execute(Sentence tokens) {
		// TODO Auto-generated method stub

		File PosDictionary = new File("./resources/posDictionary");
		Scanner s = null;
		
		
		for(int i=0; i<tokens.GetLength(); i++){
			try {
				s = new Scanner(PosDictionary);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while(s.hasNextLine()){
				String line = s.nextLine();
				String tagToken[] = line.split("\\s+");
				Token token = tokens.GetToken(i);
				if(token.getWord().equalsIgnoreCase(tagToken[0])){
					if (tagToken[2].equalsIgnoreCase("ENG")
							|| tagToken[2].equalsIgnoreCase("TAG")) {
						token.setPOSTag(tagToken[3]);
						
					} else {
						token.setPOSTag(tagToken[2]);
						
					}
					tokens.ReplaceToken(i, token);	
				}	
			}
			s.close();
		}
	
		
		return tokens;

	}

}
