package classifier.implementations;

import weka.core.Instance;
import classifier.ClassifierBuilder;
import model.Sentence;


public class ClassifierImpl implements ClassifierInterface{

	private String pathModel;
	private weka.classifiers.Classifier classifier;
	private ClassifierBuilder builder;
	
	public ClassifierImpl() throws Exception{
		this.pathModel = "./resources/model/classifier/combined-randomforest.model";
		initialize();
	}
	
	public ClassifierImpl(String pathModel) throws Exception{
		System.out.println("Classifier Model: " + pathModel);
		this.pathModel = pathModel;
		initialize();
	}
	
	private void initialize() throws Exception{
		classifier = (weka.classifiers.Classifier) weka.core.SerializationHelper.read(pathModel);
		builder = new ClassifierBuilder();
	}
	
	@Override
	public String classify(Sentence text) {
		// TODO Auto-generated method stub
		Instance instance = builder.setInstance(text);
		
		System.out.println(instance);
		try {
			double value = classifier.classifyInstance(instance);
			String label = instance.dataset().classAttribute().value((int) value);
			System.out.println("Classification: " + label);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
