package preprocess.postagger;

import model.Sentence;


public class POSTagger {
	private POSTaggerInterface strategy;
	
	public POSTagger(POSTaggerInterface strategy){
		this.strategy = strategy;
	}
	
	public Sentence executeStrategy(Sentence text){
		return this.strategy.execute(text);
	}
}