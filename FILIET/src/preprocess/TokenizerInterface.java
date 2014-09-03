package preprocess;

import gate.Gate;
import gate.util.GateException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cmu.arktweetnlp.Twokenize;
import data.Sentence;
import data.Token;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public interface TokenizerInterface {
	Sentence execute(String text);
}

class OpenNLPTokenizer implements TokenizerInterface {
	
	public Sentence execute(String text){
		InputStream is;
		String tokens[] = null;
		Sentence sentence = new Sentence();
		try {
			is = new FileInputStream("./resources/en-token.bin");
			TokenizerModel model = new TokenizerModel(is);
			TokenizerME tokenizer = new TokenizerME(model);
			tokens = tokenizer.tokenize(text);
			for(String token: tokens){
				Token word = new Token();
				word.setWord(token);
				sentence.AddToken(word);
			}
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sentence;
	}
}

class ArkNLPTokenizer implements TokenizerInterface{

	@Override
	public Sentence execute(String text) {
		// TODO Auto-generated method stub
		List<String> tokens = Twokenize.tokenizeRawTweetText(text);
		Sentence sentence = new Sentence();
		for(String token: tokens){
			Token t = new Token();
			t.setWord(token);
			sentence.AddToken(t);
		}
		return sentence;
	}
	
}