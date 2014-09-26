package preprocess.tokenizer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import model.Sentence;
import model.Token;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;


/**
 * Uses OpenNLP Tokenizer to implement the tokenizer
 * @author Vilson
 *
 */
public class OpenNLPTokenizerImpl  implements TokenizerInterface{
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
