package featureextraction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class Tokenizer {

	// OpenNLP 
	public void OpenNLPTokenizer() throws InvalidFormatException, IOException{
		InputStream is = new FileInputStream("./model/en-token.bin");
		TokenizerModel model = new TokenizerModel(is);
		TokenizerME tokenizer = new TokenizerME(model);
		String tokens[] = tokenizer.tokenize("");
		
		for(String token: tokens){
			System.out.println(token);
		}
		
		is.close();

	}
}
