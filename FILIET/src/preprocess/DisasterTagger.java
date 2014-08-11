package preprocess;

import data.Sentence;


public class DisasterTagger {
	private DisasterTaggerInterface strategy;
	
	public DisasterTagger(DisasterTaggerInterface strategy){
		this.strategy = strategy;
	}
	
	public Sentence executeStrategy(Sentence tokens){
		return this.strategy.execute(tokens);
	}
}
