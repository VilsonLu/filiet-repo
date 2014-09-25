package languagemodeller;

import java.io.IOException;

import cmu.arktweetnlp.Twokenize;

public class Driver {
	public static void main(String[] args){
		NGramModeller wordCount = new NGramModeller();
		String path = "./resources/tweets/Other.csv";
		String charngram = "./resources/model/ngram/char-ngram";
		String save = "./resources/model/wordcount/OWord";
			
		try {
			wordCount.CharNGram(2,40,charngram, path);
			wordCount.countWordFrequency(path, save, 40);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
