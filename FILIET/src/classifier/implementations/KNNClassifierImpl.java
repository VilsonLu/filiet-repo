package classifier.implementations;

import java.io.File;

import classifier.InstanceBuilder;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Instance;
import weka.core.Instances;
import model.Sentence;

public class KNNClassifierImpl implements ClassifierInterface {
	
	private SerializedClassifier classifier;
	
	public KNNClassifierImpl(){
		init();
	}
	
	public void init(){
		classifier = new SerializedClassifier();
		classifier.setModelFile(new File("./resources/model/kNN.model"));
	}
	
	@Override
	public String classify(Sentence text) {
		// TODO Auto-generated method stub
		InstanceBuilder builder = new InstanceBuilder();
		Instances dataset = null;
		dataset = builder.CreateDataset(text);
		Instance data = builder.CreateInstance(text,dataset);
		double value = -1;
		String label = null;
		try {
			value = classifier.classifyInstance(data);
			label = dataset.classAttribute().value((int) value);
			System.out.println(dataset.classAttribute().value((int) value ));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return label;
	}

}
