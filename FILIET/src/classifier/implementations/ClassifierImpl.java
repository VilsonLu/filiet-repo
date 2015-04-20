package classifier.implementations;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Enumeration;

import support.model.Sentence;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import classifier.ClassifierBuilder;


public class ClassifierImpl implements ClassifierInterface{

	private String pathModel;
	private ClassifierBuilder builder;
	private weka.classifiers.Classifier classifier;
	private String featuresPath;
	
	public ClassifierImpl() throws Exception{
		this.featuresPath = "./resources/model/TFIDF-Scores/mario-ruby/marioruby-word-combined-30.txt";
		this.pathModel = "./resources/model/classifier/multiclassifier/mario-marioruby-randomforest-30-model.model";
		initialize();
	}
	
	
	private void initialize() throws Exception{
		ObjectInputStream modelInObjectFile =   new ObjectInputStream(new FileInputStream(pathModel));
		classifier = (weka.classifiers.Classifier) modelInObjectFile.readObject();
		FastVector classLabel = new FastVector();
		classLabel.addElement("CD");
		classLabel.addElement("CA");
		classLabel.addElement("CH");
		classLabel.addElement("D");
		classLabel.addElement("O");
		builder = new ClassifierBuilder(featuresPath,null,classLabel);
		modelInObjectFile.close();
	}
	
	
	@Override
	public Sentence classify(Sentence text) {
		// TODO Auto-generated method stub
		
		Instance instance = builder.setInstance(text,ClassifierBuilder.ANY);
		String label = null;
		try {
			
			double value = classifier.classifyInstance(instance);
			System.out.println("Classifier Value: " + value);
			label = instance.dataset().classAttribute().value((int) value);
			text.setCategory(label);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

}
