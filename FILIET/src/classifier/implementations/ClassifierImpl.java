package classifier.implementations;

import support.model.Sentence;
import weka.core.FastVector;
import weka.core.Instance;
import classifier.ClassifierBuilder;


public class ClassifierImpl implements ClassifierInterface{

	private String pathModel;
	private weka.classifiers.Classifier classifier;
	private ClassifierBuilder builder;
	
	private String featuresPath;
	
	public ClassifierImpl() throws Exception{
		this.pathModel = "./resources/model/classifier/testmodel2.model";
		initialize();
	}
	
	public ClassifierImpl(String pathModel) throws Exception{
		System.out.println("Classifier Model: " + pathModel);
		this.pathModel = pathModel;
		initialize();
	}
	
	
	private void initialize() throws Exception{
		classifier = (weka.classifiers.Classifier) weka.core.SerializationHelper.read(pathModel);
		FastVector classLabel = new FastVector();
		classLabel.addElement("CA");
		classLabel.addElement("CD");
		classLabel.addElement("CH");
		classLabel.addElement("D");
		classLabel.addElement("O");
		builder = new ClassifierBuilder(classLabel);
	}
	
	
	@Override
	public Sentence classify(Sentence text) {
		// TODO Auto-generated method stub
		Instance instance = builder.setInstance(text,ClassifierBuilder.ANY);
		String label = null;
		try {
			double value = classifier.classifyInstance(instance);
			label = instance.dataset().classAttribute().value((int) value);
			text.setCategory(label);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

}
