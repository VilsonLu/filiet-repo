package preprocess.tokenizer;

import java.util.List;

import model.Sentence;
import model.Token;
import cmu.arktweetnlp.Twokenize;

/**
 * Uses ArkNLP API to implement the tokenizer
 * @author Vilson
 *
 */
public class ArkNLPTokenizerImpl implements TokenizerInterface{

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
