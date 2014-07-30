package preprocess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {

	// OpenNLP 
	public String[] OpenNLPTokenizer(String string) throws InvalidFormatException, IOException{
		
		InputStream is = new FileInputStream("./model/en-token.bin");
		TokenizerModel model = new TokenizerModel(is);
		TokenizerME tokenizer = new TokenizerME(model);
		String tokens[] = tokenizer.tokenize(string);
		
		for(String token: tokens){
			System.out.println(token);
		}
		
		is.close();
		
		return tokens;

	}
}
