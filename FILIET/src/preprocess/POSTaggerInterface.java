package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface POSTaggerInterface {
	String[] execute(String[] tokens);
}

/*
 * Using POS Lookup for the implementation of POS Tagger
 */
class POSLookup implements POSTaggerInterface {

	@Override
	public String[] execute(String[] tokens) {
		// TODO Auto-generated method stub

		File PosDictionary = new File("./resources/Dict File.txt");

		Scanner s = null;
		for (int i = 0; i < tokens.length; i++) {

			try {
				s = new Scanner(PosDictionary);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			while (s.hasNextLine()) {
				String line = s.nextLine();
				String tagToken[] = line.split("\\s+");

				if (tokens[i].equalsIgnoreCase(tagToken[0])) {
					if (tagToken[2].equalsIgnoreCase("ENG")
							|| tagToken[2].equalsIgnoreCase("TAG")) {
						tokens[i] = tokens[i] + "_" + tagToken[3];
					} else {
						tokens[i] = tokens[i] + "_" + tagToken[2];
					}
				}
			}
			s.close();
		}
		
		return tokens;

	}

}
