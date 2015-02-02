package languagemodeller;

import java.io.IOException;
import java.util.Iterator;

import opennlp.tools.ngram.NGramModel;
import opennlp.tools.util.StringList;
import cmu.arktweetnlp.Twokenize;

public class Driver {
	public static void main(String[] args){
		NGramModeller model = new NGramModeller();
		String path = "./resources/tweets/ruby-datasets/combined-d.csv";
		String charngram = "./resources/model/ngram/ruby-ngram-d";
		String save = "./resources/model/word/ruby-word-d";
		int top = 30;
			
//		try {
//			model.CharNGram(2,top,charngram, path);
//			model.countWordFrequency(path, save, top);		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		String dataset = "./resources/tweets/ruby-datasets/ruby-combined.csv";
		String categoryDataset = "./resources/tweets/ruby-datasets/combined-d.csv";
		String saveResults = "./resources/model/TFIDF-Scores/d-TFIDF.txt";
		
		try {
			DocumentFrequency documentDataset = new DocumentFrequency(dataset);
			DocumentFrequency documentCategory = new DocumentFrequency(categoryDataset);
			
			WeightScorer tfidfScores = new WeightScorer(documentCategory, documentDataset);
			tfidfScores.computeWeights();
			tfidfScores.saveResults(saveResults);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
