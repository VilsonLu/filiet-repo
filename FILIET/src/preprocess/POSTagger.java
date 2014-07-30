package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class POSTagger {

	//POS Lookup
	public String[] POSLookUp(String[] tokens) throws FileNotFoundException{
		
		File PosDictionary = new File("./model/Dict File.txt");
		
		Tokenizer tokenizer = new Tokenizer();
				
		Scanner s = null;
		for(int i=0; i<tokens.length; i++){
			s = new Scanner(PosDictionary);
			while(s.hasNextLine()){
				String line = s.nextLine();
				String tagToken[] = line.split("\\s+");
	
				if(tokens[i].equalsIgnoreCase(tagToken[0])){
					if(tagToken[2].equalsIgnoreCase("ENG") || tagToken[2].equalsIgnoreCase("TAG")) {
						tokens[i] = tokens[i]+"_"+tagToken[3];
					} else {
						tokens[i] = tokens[i]+"_"+tagToken[2];
					}
				}
			}
			s.close();
		}
		System.out.println(tokens);
		System.out.println("end");
		return tokens;
	}
}
