package classifier.implementations;

import model.Sentence;
import preprocess.disastertagger.DisasterTaggerInterface;

public class Classifier {
	private ClassifierInterface strategy;
	
	public Classifier(ClassifierInterface strategy) {
		this.strategy = strategy;
	}

	public String executeStrategy(Sentence sentence) {
		return this.strategy.classify(sentence);
	}
}
