import java.io.IOException;

import featureextraction.Tokenizer;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome back to Java");
		
		Tokenizer tokenizer = new Tokenizer();
		try {
			tokenizer.OpenNLPTokenizer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
