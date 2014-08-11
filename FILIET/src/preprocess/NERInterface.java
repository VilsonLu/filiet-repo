package preprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import data.Sentence;
import data.Token;

public interface NERInterface {
	public Sentence execute(Sentence tweet);
}

class SomidiaNER implements NERInterface {

	@Override
	public Sentence execute(Sentence tweet) {
		// TODO Auto-generated method stub
		File file = new File("./resources/NamedEntityRecognizerDictModel");
		Scanner s = null;
		
		for(int i=0; i<tweet.GetLength(); i++){
			Token token = tweet.GetToken(i);
			String category = null;
			try {
				s = new Scanner(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while(s.hasNextLine()){
				String line = s.nextLine();
				if(line.contains("DISASTER")){
					category = "disaster";
				} else if(line.contains("LOCATION")){
					category = "location";
				} else if(token.getWord().equalsIgnoreCase(line.toString())){
					token.setNERTag(category);
					token.setPOSTag("NN");
					tweet.ReplaceToken(i, token);
					break;
				}
			}
			
			
			s.close();
		}
		
		
		return tweet;
	}
	
}

