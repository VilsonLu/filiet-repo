package support.languagemodeller;

import java.io.IOException;
import java.util.Iterator;

import opennlp.tools.ngram.NGramModel;
import opennlp.tools.util.StringList;
import cmu.arktweetnlp.Twokenize;

public class Driver {
	public static void main(String[] args){
		NGramModeller model = new NGramModeller();
		String path = "./resources/tweets/mario-datasets/original/Mario-CA.csv";
		String charngramPath = "./resources/model/ngram/mario/mario-2gram-ca-10";
		String save = "./resources/model/word/ruby-word-d";
		double top = 0.1;
		int ngram = 2;
		
//// 		Count N-Gram
//		try {
//			model.CharNGram(ngram,top,charngramPath, path);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		// TFIDF Word Features
		String dataset = "./resources/tweets/ruby-datasets/original/Ruby-Combined-No-O.csv";
		String categoryDataset = "./resources/tweets/ruby-datasets/original/Ruby-CH.csv";
		String saveResults = "./resources/model/TFIDF-Scores/ruby/ruby-word-CH-20.txt";
		double topWords = 0.2;
		try {
			DocumentFrequency documentDataset = new DocumentFrequency(dataset);
			DocumentFrequency documentCategory = new DocumentFrequency(categoryDataset);
	
			WeightScorer tfidfScores = new WeightScorer(documentCategory, documentDataset);
			tfidfScores.computeWeights();
			//tfidfScores.saveResults(saveResults);
			int size = (int) (tfidfScores.computeSize() * topWords);
			tfidfScores.getTop(saveResults,size);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
