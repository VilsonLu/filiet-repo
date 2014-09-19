package preprocess;

import model.Sentence;

public class NamedEntityRecognizer {
	private NERInterface strategy;
	
	public NamedEntityRecognizer(NERInterface strategy){
		this.strategy = strategy;
	}
	
	public Sentence executeStrategy(Sentence sentence){
		return this.strategy.execute(sentence);
	}
}
