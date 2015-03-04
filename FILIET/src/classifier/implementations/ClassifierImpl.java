package classifier.implementations;

import support.model.Sentence;
import weka.core.Instance;
import classifier.ClassifierBuilder;


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
	
	public ClassifierImpl(String pathModel, ClassifierBuilder builder) throws Exception{
		this.pathModel = pathModel;
		initialize(builder);
	}
	
	private void initialize() throws Exception{
		classifier = (weka.classifiers.Classifier) weka.core.SerializationHelper.read(pathModel);
		builder = new ClassifierBuilder();
	}
	
	private void initialize(ClassifierBuilder builder) throws Exception{
		classifier = (weka.classifiers.Classifier) weka.core.SerializationHelper.read(pathModel);
		this.builder = builder;
	}
	
	@Override
	public String classify(Sentence text) {
		// TODO Auto-generated method stub
		Instance instance = builder.setInstance(text);
		
		String label = null;
		try {
			double value = classifier.classifyInstance(instance);
			label = instance.dataset().classAttribute().value((int) value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return label;
	}

}
