package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface DisasterTaggerInterface {
	String[] execute(String[] tokens);
}

class DefaultDisasterTag implements DisasterTaggerInterface{

	@Override
	public String[] execute(String[] tokens) {
		// TODO Auto-generated method stub
		File DisasterWords = new File("./model/DisasterWords.txt");
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
			
				if(tokens[i].contains(line)){
					tokens[i] = "<disaster="+tokens[i]+"/>";
				}
			}
			s.close();
		}
		
		return tokens;
	}
	
}
