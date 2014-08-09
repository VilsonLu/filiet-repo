
import crawler.TwitterCrawler;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("FILIET - Filipino Information Extraction for Twitter");
		
		TwitterCrawler crawler = new TwitterCrawler();
		crawler.TweetCrawl();
		
		//PreprocessorManager preprocess = new PreprocessorManager();
		//String text = "Magnitude 4.3 quake jolts Antique, Boracay Lindol everywhere";
		//preprocess.PreprocessText(text);
		

			
	}
}


