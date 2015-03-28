package classifier.implementations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;









import classifier.ClassifierBuilder;
import support.model.Sentence;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class TestClassifierImpl implements ClassifierInterface {

	private RandomForest randomForest;
	private String pathModel;
	private Instances instances;
	private ClassifierBuilder builder;
	private String pathFeatures;
	
	public TestClassifierImpl(){
		this.pathModel = "./resources/model/classifier/single/mario-marioruby-Combined-word-30.arff";
		try {
			initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initialize() throws Exception{
		DataSource source = new DataSource(pathModel);
		Instances dataset = source.getDataSet();
		dataset.setClassIndex(3);
		randomForest = new RandomForest();
		randomForest.buildClassifier(dataset);
		builder = new ClassifierBuilder(null,null, null);
		builder.setDataset(dataset);

	}
	
	@Override
	public Sentence classify(Sentence text) {
		// TODO Auto-generated method stub
		Instance instance = builder.setInstance(text, ClassifierBuilder.ANY);
		try {
			double value = randomForest.classifyInstance(instance);
			String label = instance.dataset().classAttribute().value((int) value);
			text.setCategory(label);
			return text;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}

}
