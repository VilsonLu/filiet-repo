package classifier;

import java.io.File;
import java.io.FileNotFoundException;

import model.Sentence;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Instance;
import weka.core.Instances;

public interface ClassifierInterface {
	public String classify(Sentence text);
}



