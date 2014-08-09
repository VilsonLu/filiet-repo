package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface DisasterTaggerInterface {
	String[] execute(String[] tokens);
}

// Default Implementation of Disaster Tagger
class DefaultDisasterTag implements DisasterTaggerInterface{

	@Override
	public String[] execute(String[] tokens) {
		// TODO Auto-generated method stub
		File DisasterWords = new File("./resources/DisasterWords.txt");
		Scanner s = null;
		
		for(int i=0; i<tokens.length; i++){
			
			try {
				s = new Scanner(DisasterWords);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while(s.hasNextLine()){
				String line = s.nextLine();
				String temp[] = tokens[i].split("_");
				if(temp[0].equalsIgnoreCase(line)){
					tokens[i] = "<disaster="+tokens[i]+"/>";
				}
			}
			s.close();
		}
		
		return tokens;
	}
	
}
