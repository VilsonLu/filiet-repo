package preprocess.disastertagger;

import java.io.File;
import java.util.Scanner;

import model.Sentence;

public class DefaultDisasterTaggerImpl implements DisasterTaggerInterface {
	@Override
	public Sentence execute(Sentence tokens) {
		// TODO Auto-generated method stub
		File DisasterWords = new File("./resources/DisasterWords");
		Scanner s = null;
		/*
		for(int i=0; i<tokens.GetLength(); i++){
			Token token = tokens.GetToken(i);
			try {
				s = new Scanner(DisasterWords);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while(s.hasNextLine()){
				String line = s.nextLine();
				if(token.getWord().equalsIgnoreCase(line)){
					token.setIsDisasterWord(true);
					tokens.ReplaceToken(i, token);
				} 
			}
			s.close();
		}
		*/
		return tokens;
	}
}
