import crawler.TwitterCrawler;


public class CrawlerDriver {

	public static void main (String args[]){
		TwitterCrawler crawler = new TwitterCrawler();
		crawler.TweetCrawl();
	}
}