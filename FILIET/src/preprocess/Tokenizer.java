package preprocess;

import java.io.IOException;

import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {
	private TokenizerInterface strategy;
	public Tokenizer(TokenizerInterface strategy){
		this.strategy = strategy;
	}
	
	public String[] executeStrategy(String text) throws InvalidFormatException, IOException{
		return this.strategy.execute(text);
	}
}
