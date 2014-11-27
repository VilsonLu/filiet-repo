package preprocess.normalizer;

import model.Sentence;

public class Normalizer {
	private NormalizerInterface strategy;
	
	public Normalizer(NormalizerInterface strategy) {
		this.strategy = strategy;
	}
	
	public String executeStrategy(String text) {
		return this.strategy.execute(text);
	}
}
