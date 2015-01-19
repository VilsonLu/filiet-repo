package languagemodeller;

import java.io.IOException;

import cmu.arktweetnlp.Twokenize;

public class Driver {
	public static void main(String[] args){
		NGramModeller model = new NGramModeller();
		String path = "./resources/tweets/ruby-datasets/combined-d.csv";
		String charngram = "./resources/model/ngram/ruby-ngram-d";
		String save = "./resources/model/word/ruby-word-d";
		int top = 30;
			
		try {
			model.CharNGram(2,top,charngram, path);
			model.countWordFrequency(path, save, top);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
