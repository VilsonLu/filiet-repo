import preprocess.PreprocessorManager;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome back to Java");
		PreprocessorManager preprocess = new PreprocessorManager();
		String text = "Magnitude 4.3 quake jolts Antique, Boracay Lindol everywhere";
		String tokens[] = preprocess.PreprocessText(text);
			
	}
}


