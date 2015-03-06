package preprocess;

public class Driver {
	public static void main(String[] args){
		PreprocessorManager pm = new PreprocessorManager();
		String tweet = "@gmanews: Bagsak na ang linya ng komunikasyon sa mga bayan sa Northeastern Samar. #RubyPH | via @PeeweeHero @dzbb keep safe everyone";
		
		pm.PreprocessText(tweet);
	}
}
