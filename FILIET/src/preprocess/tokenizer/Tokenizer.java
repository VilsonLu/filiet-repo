package preprocess.tokenizer;

import java.io.IOException;

import opennlp.tools.util.InvalidFormatException;
import support.model.Sentence;

public class Tokenizer {
	private TokenizerInterface strategy;
	public Tokenizer(TokenizerInterface strategy){
		this.strategy = strategy;
	}
	
	public Sentence executeStrategy(String text){
		return this.strategy.execute(text);
	}
}
