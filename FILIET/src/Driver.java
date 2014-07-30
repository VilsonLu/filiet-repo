import preprocess.PreprocessorManager;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome back to Java");
		PreprocessorManager preprocess = new PreprocessorManager();
		String text = "umiiyak ang puso ko  una  sa mga naging biktima ng bagyo. pangalawa  sa ilang staff na nawalan ng trabaho.";
		preprocess.PreprocessText(text);
			
	}
}


