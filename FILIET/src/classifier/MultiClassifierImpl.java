package classifier;

import java.io.File;

import model.Sentence;
import weka.classifiers.misc.SerializedClassifier;
import weka.core.Instance;
import weka.core.Instances;


/*
 * This is a multi-label classifier using k-NN algorithm
 */

public class MultiClassifierImpl implements ClassifierInterface{
	/**
	 * This is the classifier for Caution and Advice Category
	 */
	private SerializedClassifier classifierCA;
	//private SerializedClassifier classifierCD;
	/**
	 * This is the classifier for Donation Category
	 */
	private SerializedClassifier classifierD;
	//private SerializedClassifier classifierO;
	
	public MultiClassifierImpl(){
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
			if(label == "CA") {
				return label; 
			}
			
			/*
			value = classifierCD.classifyInstance(data);
			label = dataset.classAttribute().value((int) value);
			if(label == "CD") {
				return label;
			}
			*/
			
			value = classifierD.classifyInstance(data);
			label = dataset.classAttribute().value((int) value);
			if(label == "D") {
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
