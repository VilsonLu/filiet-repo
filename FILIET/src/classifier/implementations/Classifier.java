package classifier.implementations;

import preprocess.disastertagger.DisasterTaggerInterface;
import support.model.Sentence;

public class Classifier {
	private ClassifierInterface strategy;
	
	public Classifier(ClassifierInterface strategy) {
		this.strategy = strategy;
	}

	public Sentence executeStrategy(Sentence sentence) {
		return this.strategy.classify(sentence);
	}
}
