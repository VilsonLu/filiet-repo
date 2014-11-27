
import preprocess.PreprocessorManager;
import crawler.TwitterCrawler;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("FILIET - Filipino Information Extraction for Twitter");
		System.out.println();
		//TwitterCrawler crawler = new TwitterCrawler();
		//crawler.TweetCrawl();
		
		PreprocessorManager preprocess = new PreprocessorManager();
		String text = "Kailangan na tlga ng military efforts s most part of Leyte. Nagkkgulo n.";
		preprocess.PreprocessText(text);
		

			
	}
}


