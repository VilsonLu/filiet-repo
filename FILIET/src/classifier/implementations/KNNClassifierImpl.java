package classifier.implementations;

import java.io.File;

import model.Sentence;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Instance;
import weka.core.Instances;
import classifier.InstanceBuilder;

public class KNNClassifierImpl implements ClassifierInterface {
	
	private Classifier classifier;
	private String path;
	private Instances trainingData;
	private InstanceBuilder builder;
	
	public KNNClassifierImpl(String path){
		this.path = path;
		System.out.println("KNNClassifier Path: " + path );
		try {
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void init() throws Exception{
		// deserialize the model
		classifier = (Classifier) weka.core.SerializationHelper.read(path);
		// training data
		builder = new InstanceBuilder();
		trainingData = builder.buildTrainingData("./resources/tweets/test-extracted/Batch 2/test-Combined.csv");
	}
	
	@Override
	public String classify(Sentence text) {
		// TODO Auto-generated method stub
		Instances dataset = builder.CreateDataset(text);
		Instance data = builder.CreateInstance(text,dataset);
		data.setClassValue(Instance.missingValue());
		double value = -1;
		String label = null;
		try {
			value = classifier.classifyInstance(data);
			System.out.println("Value: " + value);
			label = dataset.classAttribute().value((int) value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return label;
	}

}
