package classifier.implementations;

import classifier.ClassifierBuilder;
import support.model.Sentence;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;

public class MultiClassifierImpl implements ClassifierInterface {
	
	private final static int CA = 0;
	private final static int CD = 1;
	private final static int CH = 2;
	private final static int D = 3;
	private final static int O = 4;
	
	// Classifier
	private weka.classifiers.Classifier caClassifier;
	private weka.classifiers.Classifier cdClassifier;
	private weka.classifiers.Classifier chClassifier;
	private weka.classifiers.Classifier dClassifier;
	private weka.classifiers.Classifier multiClassifier;
	// Instance builder
	private ClassifierBuilder caBuilder;
	private ClassifierBuilder cdBuilder;
	private ClassifierBuilder chBuilder;
	private ClassifierBuilder dBuilder;
	private ClassifierBuilder multiBuilder;
	// Features
	private String caPath;
	private String cdPath;
	private String chPath;
	private String dPath;
	private String multiPath;
	// Model
	private String caModel;
	private String cdModel;
	private String chModel;
	private String dModel;
	private String multiModel;

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
		multiPath = "./resources/model/TFIDF-Scores/mario-ruby/marioruby-word-combined-30.txt";
	}

	private void initializeModelPath() {
		caModel = "./resources/model/classifier/multiclassifier/mario-rf-ca-30.model";
		chModel = "./resources/model/classifier/multiclassifier/mario-rf-ch-30.model";
		dModel = "./resources/model/classifier/multiclassifier/mario-rf-d-30.model";
		cdModel = "./resources/model/classifier/multiclassifier/mario-rf-cd-30.model";
		multiModel = "./resources/model/classifier/multiclassifier/mario-marioruby-randomforest-30-model.model";
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
		
		FastVector multilabel = new FastVector();
		multilabel.addElement("CA");
		multilabel.addElement("CD");
		multilabel.addElement("CH");
		multilabel.addElement("D");
		multilabel.addElement("O");
		multiBuilder = new ClassifierBuilder(multiPath, null, multilabel);
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
		multiClassifier = (weka.classifiers.Classifier) weka.core.SerializationHelper
				.read(multiModel);
		System.out.println("Multi: Success");
		
		
	}

	@Override
	public Sentence classify(Sentence text) {
		// TODO Auto-generated method stub

		Instance caInstance = caBuilder.setInstance(text, ClassifierBuilder.CA);
		Instance cdInstance = cdBuilder.setInstance(text, ClassifierBuilder.CD);
		Instance chInstance = chBuilder.setInstance(text, ClassifierBuilder.CH);
		Instance dInstance = dBuilder.setInstance(text, ClassifierBuilder.D);
		Instance multiInstance = multiBuilder.setInstance(text, ClassifierBuilder.ANY);
		String category = null;

		int vote[] = new int[] {0,0,0,0,0};
		try {
			double caValue = caClassifier.classifyInstance(caInstance);
			String ca = caBuilder.getDataset().classAttribute()
					.value((int) caValue);

			double cdValue = cdClassifier.classifyInstance(cdInstance);
			String cd = cdBuilder.getDataset().classAttribute().value((int)cdValue);

			double chValue = chClassifier.classifyInstance(chInstance);
			String ch = chBuilder.getDataset().classAttribute()
					.value((int) chValue);

			double dValue = dClassifier.classifyInstance(dInstance);
			String d = dBuilder.getDataset().classAttribute()
					.value((int) dValue);
			
			double multiValue = multiClassifier.classifyInstance(multiInstance);
			String multi = multiBuilder.getDataset().classAttribute()
					.value((int) multiValue);
			
			// voting

			if(ca.equalsIgnoreCase("CA")){
				vote[CA]++;
			} else if(ca.equalsIgnoreCase("O")){
				vote[O]++;
			}
			
			if(cd.equalsIgnoreCase("CD")){
				vote[CD]++;
			} else if(cd.equalsIgnoreCase("O")){
				vote[O]++;
			}
			
			if(ch.equalsIgnoreCase("CH")){
				vote[CH]++;
			} else if(ch.equalsIgnoreCase("O")){
				vote[O]++;
			}
			
			if(d.equalsIgnoreCase("D")){
				vote[D]++;
			} else if(d.equalsIgnoreCase("O")){
				vote[O]++;
			}
			
			
			// checking of the votes
			// majority
			if(vote[O] > 4){
				category = "O";
			} else {
				if(ca.equalsIgnoreCase(multi)){
					category = "CA";
				} else if(cd.equalsIgnoreCase(multi)){
					category = "CD";
				} else if(ch.equalsIgnoreCase(multi)){
					category = "CH";
				} else if(d.equalsIgnoreCase(multi)){
					category = "D";
				} else {
					category = "O";
				}
			}
		
			text.setCategory(category);
			return text;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return text;

	}

}
