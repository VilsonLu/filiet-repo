package preprocess.tokenizer;

import support.model.Sentence;
import support.model.Token;

public class Driver {

	public static void main(String[] args){
		double nano = 1000000000.0;
		String tweet = "U.S.A. :) asan na kayo? bahay-kubo #marioph @ancalert";
		// ArkNLP implementation
		Tokenizer arknlp = new Tokenizer(new ArkNLPTokenizerImpl());

		// OpenNLP implementation
		Tokenizer opennlp = new Tokenizer(new OpenNLPTokenizerImpl());
		long timeIn = System.nanoTime();
		Sentence sentenceArk = arknlp.executeStrategy(tweet);
		long timeOut = System.nanoTime();
		double time = (double) (timeOut-timeIn);

		System.out.println("ArkNLP:" + (time/nano));
		for(Token s: sentenceArk.getSentence()){
			System.out.println(s.getWord());
		}
		
		timeIn = System.nanoTime();
		Sentence sentenceOpen = opennlp.executeStrategy(tweet);
		timeOut = System.nanoTime();
		time = (double) (timeOut-timeIn);
		
		System.out.println("OpenNLP: " + (time/nano));
		for(Token s: sentenceOpen.getSentence()){
			System.out.println(s.getWord());
		}

	}
}
