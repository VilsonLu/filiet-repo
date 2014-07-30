package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DisasterTagger {
	
	public String[] DisasterTag(String[] text) throws FileNotFoundException{
		File DisasterWords = new File("./model/DisasterWords.txt");
		Scanner s = null;
		for(int i=0; i<text.length; i++){
			s = new Scanner(DisasterWords);
			while(s.hasNextLine()){
				String line = s.nextLine();
			
				if(text[i].contains(line)){
					text[i] = "<disaster="+text[i]+"/>";
				}
			}
			s.close();
		}
		return text;
		
	}
}
