package preprocess.postagger;

import model.Sentence;
import model.Token;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;

public class Driver {

	public static void main(String[] args){
		double nano = 1000000000.0;
		String message = "U.S.A. :) asan na kayo? bahay-kubo #marioph @ancalert";

		Tokenizer arknlp = new Tokenizer(new ArkNLPTokenizerImpl());
		Sentence tweet = arknlp.executeStrategy(message);
		
		// ArkNLP implementation
		POSTagger poslookup = new POSTagger(new POSLookupImpl());

		// OpenNLP implementation
		POSTagger hashlookup = new POSTagger(new POSHashLookupImpl());
		
		long timeIn = System.nanoTime();
		Sentence sentencePos = poslookup.executeStrategy(tweet);
		long timeOut = System.nanoTime();
		double time = (double) (timeOut-timeIn);

		System.out.println("POS Lookup:" + (time/nano));
		for(Token s: sentencePos.getSentence()){
			System.out.println(s.getWord()+":"+s.getPOSTag());
		}
		
		timeIn = System.nanoTime();
		Sentence sentenceOpen = hashlookup.executeStrategy(tweet);
		timeOut = System.nanoTime();
		time = (double) (timeOut-timeIn);
		
		System.out.println("Hash Lookup: " + (time/nano));
		for(Token s: sentenceOpen.getSentence()){
			System.out.println(s.getWord()+":"+s.getPOSTag());
		}
	}
}
