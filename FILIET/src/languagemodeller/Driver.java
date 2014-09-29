package languagemodeller;

import java.io.IOException;

import cmu.arktweetnlp.Twokenize;

public class Driver {
	public static void main(String[] args){
		NGramModeller wordCount = new NGramModeller();
		String path = "./resources/tweets/Categorized.csv";
		String charngram = "./resources/model/ngram/char-ngram";
		String save = "./resources/model/wordcount/words";
		int top = 50;
			
		try {
			wordCount.CharNGram(2,top,charngram, path);
			wordCount.countWordFrequency(path, save, top);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
