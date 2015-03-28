package preprocess.postagger;

import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import support.model.Sentence;
import support.model.Token;

public class Driver {

	public static void main(String[] args){
		double nano = 1000000000.0;
		String message = "RT @MMDA: FLOOD UPDATE: AS OF 9:28 PM Ma.Clara Araneta - Subided. #mmda";

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
