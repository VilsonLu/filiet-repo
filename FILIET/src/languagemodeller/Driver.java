package languagemodeller;

import java.io.IOException;

import cmu.arktweetnlp.Twokenize;

public class Driver {
	public static void main(String[] args){
		NGramModeller model = new NGramModeller();
		String path = "./resources/tweets/Cleaned-Categorized-JP-3.csv";
		String charngram = "./resources/model/ngram/char-ngram";
		String save = "./resources/model/wordcount/wordcounts";
		int top = 50;
			
		try {
			model.CharNGram(2,top,charngram, path);
			model.countWordFrequency(path, save, top);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
