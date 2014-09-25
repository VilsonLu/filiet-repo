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

/*
 * This is a multi-label classifier using k-NN algorithm
 */
class kNNClassifier implements ClassifierInterface {

	private SerializedClassifier classifier;
	
	public kNNClassifier(){
		classifier = new SerializedClassifier();
		classifier.setModelFile(new File("./resources/model/kNN.model"));
	}
	
	@Override
	public String classify(Sentence text) {
		// TODO Auto-generated method stub
		InstanceBuilder builder = new InstanceBuilder();
		Instances dataset = null;
		dataset = builder.CreateDataset();
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

class MultiClassifier implements ClassifierInterface {

	private SerializedClassifier classifierCA;
	//private SerializedClassifier classifierCD;
	private SerializedClassifier classifierD;
	//private SerializedClassifier classifierO;
	
	public MultiClassifier(){
		classifierCA = new SerializedClassifier();
		classifierCA.setModelFile(new File("./resources/model/CA-Model.model"));
		//classifierCD = new SerializedClassifier();
		classifierD = new SerializedClassifier();
		classifierD.setModelFile(new File("./resources/model/D-Model.model"));
	
	}
	
	
	@Override
	public String classify(Sentence text) {
		
		InstanceBuilder builder = new InstanceBuilder();
		Instances dataset = null;
		dataset = builder.CreateDataset();
		Instance data = builder.CreateInstance(text,dataset);
		
		try {
			
			double value = classifierCA.classifyInstance(data);
			String label = dataset.classAttribute().value((int) value);
			if(label == "CA"){
				return label; 
			}
			
			/*
			value = classifierCD.classifyInstance(data);
			label = dataset.classAttribute().value((int) value);
			if(label == "CD"){
				return label;
			}
			*/
			
			value = classifierD.classifyInstance(data);
			label = dataset.classAttribute().value((int) value);
			if(label == "D"){
				return label;
			}
			
			return "O";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	
	}
	
}