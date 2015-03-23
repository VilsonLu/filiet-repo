package classifier.implementations;

import classifier.ClassifierBuilder;
import support.model.Sentence;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;

public class MultiClassifierImpl implements ClassifierInterface {
	// Classifier
	private weka.classifiers.Classifier caClassifier;
	private weka.classifiers.Classifier cdClassifier;
	private weka.classifiers.Classifier chClassifier;
	private weka.classifiers.Classifier dClassifier;
	// Instance builder
	private ClassifierBuilder caBuilder;
	private ClassifierBuilder cdBuilder;
	private ClassifierBuilder chBuilder;
	private ClassifierBuilder dBuilder;
	// Features
	private String caPath;
	private String cdPath;
	private String chPath;
	private String dPath;
	// Model
	private String caModel;
	private String cdModel;
	private String chModel;
	private String dModel;

	public MultiClassifierImpl() throws Exception {
		initializeFeaturesPath();
		initializeModelPath();
		initializeBuilder();
		initializeClassifier();
	}

	private void initializeFeaturesPath() {
		caPath = "./resources/model/TFIDF-Scores/mario/archive/mario-word-ca-30.txt";
		chPath = "./resources/model/TFIDF-Scores/mario/archive/mario-word-ch-30.txt";
		dPath = "./resources/model/TFIDF-Scores/mario/archive/mario-word-d-30.txt";
		cdPath = "./resources/model/TFIDF-Scores/mario/archive/mario-word-cd-30.txt";
	}

	private void initializeModelPath() {
		caModel = "./resources/model/classifier/multiclassifier/mario-rf-ca-30.model";
		chModel = "./resources/model/classifier/multiclassifier/mario-rf-ch-30.model";
		dModel = "./resources/model/classifier/multiclassifier/mario-rf-d-30.model";
		cdModel = "./resources/model/classifier/multiclassifier/mario-rf-cd-30.model";
	}

	private void initializeBuilder() throws Exception {
		FastVector calabel = new FastVector();
		calabel.addElement("CA");
		calabel.addElement("O");
		caBuilder = new ClassifierBuilder(caPath, null, calabel);

		FastVector cdlabel = new FastVector();
		cdlabel.addElement("CD");
		cdlabel.addElement("O");
		cdBuilder = new ClassifierBuilder(cdPath, null, cdlabel);

		FastVector chlabel = new FastVector();
		chlabel.addElement("CH");
		chlabel.addElement("O");
		chBuilder = new ClassifierBuilder(chPath, null, chlabel);

		FastVector dlabel = new FastVector();
		dlabel.addElement("D");
		dlabel.addElement("O");
		dBuilder = new ClassifierBuilder(dPath, null, dlabel);
	}

	private void initializeClassifier() throws Exception {
		caClassifier = (weka.classifiers.Classifier) weka.core.SerializationHelper
				.read(caModel);
		System.out.println("CA: Success");
		chClassifier = (weka.classifiers.Classifier) weka.core.SerializationHelper
				.read(chModel);
		System.out.println("CH: Success");
		dClassifier = (weka.classifiers.Classifier) weka.core.SerializationHelper
				.read(dModel);
		System.out.println("D: Success");
		cdClassifier = (weka.classifiers.Classifier) weka.core.SerializationHelper
				.read(cdModel);
		System.out.println("CD: Success");
		
	}

	@Override
	public Sentence classify(Sentence text) {
		// TODO Auto-generated method stub

		Instance caInstance = caBuilder.setInstance(text, ClassifierBuilder.CA);
		Instance cdInstance = cdBuilder.setInstance(text, ClassifierBuilder.CD);
		Instance chInstance = chBuilder.setInstance(text, ClassifierBuilder.CH);
		Instance dInstance = dBuilder.setInstance(text, ClassifierBuilder.D);

		try {
			double value = caClassifier.classifyInstance(caInstance);
			String ca = caBuilder.getDataset().classAttribute()
					.value((int) value);

			double cdValue = cdClassifier.classifyInstance(cdInstance);
			String cd = cdBuilder.getDataset().classAttribute().value((int)cdValue);

			double value3 = chClassifier.classifyInstance(chInstance);
			String ch = chBuilder.getDataset().classAttribute()
					.value((int) value3);

			double value4 = dClassifier.classifyInstance(dInstance);
			String d = dBuilder.getDataset().classAttribute()
					.value((int) value4);

			if(ca.equalsIgnoreCase("CA")){
				text.setCategory("CA");
				return text;
			} else {
				if(cd.equalsIgnoreCase("CD")){
					text.setCategory("CD");
					return text;
				} else {
					if(d.equalsIgnoreCase("D")){
						text.setCategory("D");
						return text;
					} else {
						if(ch.equalsIgnoreCase("CH")){
							text.setCategory("CH");
							return text;
						} else {
							text.setCategory("O");
							return text;
						}
					}
				}
			}
	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;

	}

}
