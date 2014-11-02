package preprocess.tokenizer;

import java.io.IOException;

import model.Sentence;
import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {
	private TokenizerInterface strategy;
	public Tokenizer(TokenizerInterface strategy){
		this.strategy = strategy;
	}
	
	public Sentence executeStrategy(String text){
		return this.strategy.execute(text);
	}
}
