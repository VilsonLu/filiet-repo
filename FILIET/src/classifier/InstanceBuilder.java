package classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.Sentence;
import model.Tweet;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class InstanceBuilder {

	public Instances CreateDataset() {
		
		String path = "./resources/model/attributes";
		FileReader fr = null;
		BufferedReader br = null;
		FastVector vector = null;
		String line = null;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
			
			vector = new FastVector();
			while( (line=br.readLine()) != null){
				Attribute attribute = new Attribute(line);
				vector.addElement(attribute);
			}
			
			br.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Attribute label = SetClassLabel();
		vector.addElement(label);
		
		Instances instances =  new Instances("ClassifierInstance",vector,0);
		
		// Set the class label;
		instances.setClassIndex(instances.numAttributes()-1);
		
		return instances;
	}
	
	private Attribute SetClassLabel(){
		
		FastVector classVal = new FastVector();
		classVal.addElement("CA");
		classVal.addElement("CD");
		classVal.addElement("O");
		classVal.addElement("D");
		
		Attribute classlabel = new Attribute("Category", classVal);
		return classlabel;
	}
	
	public Instance SetInstanceValue(Instances instances, Sentence sentence){
		
		Instance data = new Instance(instances.numAttributes());
		Tweet sample = sentence.getTweets();
		
		// features extracted from the crawler
		data.setDataset(instances);
		data.setValue(0, sample.getTweetID());
		data.attribute(1).addStringValue(sample.getUser());
		data.attribute(2).addStringValue(sample.getTweet());
		data.setValue(3,sample.getLatitude());
		data.setValue(4,sample.getLongitude());
		data.setValue(5, getBooleanValue(sample.getHashtag()));
		data.setValue(6, getBooleanValue(sample.getRetweet()));
		data.setValue(7, getBooleanValue(sample.getURL()));
		data.attribute(8).addStringValue(sample.getLanguage());
		// features extracted from feature extraction
		
		instances.add(data);
		return data;
	}
	/**
	 * 
	 * @param sentence
	 * @return Instance
	 */
	public Instance CreateInstance(Sentence sentence){

		Instances instances = null;
		Instance instance = null;
		instances = CreateDataset();
		instance = SetInstanceValue(instances, sentence);
		
		return instance;
	}
	
	
	/**
	 * This will create an instance object 
	 * @param sentence - this contains the annotated data
	 * @param dataset - this contains the schema 
	 * @return Instance
	 */
	public Instance CreateInstance(Sentence sentence, Instances dataset){

		Instance instance = SetInstanceValue(dataset,sentence);
		return instance;
	}
	
	// Supporting functions
	
	private int getBooleanValue(Boolean value){
		if(value)
			return 1;
		else
			return 0;
	}
	
}
