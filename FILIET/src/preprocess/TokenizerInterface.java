package preprocess;

import gate.Gate;
import gate.util.GateException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public interface TokenizerInterface {
	String[] execute(String text);
}

class OpenNLPTokenizer implements TokenizerInterface {
	
	public String[] execute(String text){
		InputStream is;
		String tokens[] = null;
		try {
			is = new FileInputStream("./resources/en-token.bin");
			TokenizerModel model = new TokenizerModel(is);
			TokenizerME tokenizer = new TokenizerME(model);
			tokens = tokenizer.tokenize(text);
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tokens;
	}
}

class TwitIETokenizer implements TokenizerInterface{

	@Override
	public String[] execute(String text) {
		// TODO Auto-generated method stub

		return null;
	}
	
}