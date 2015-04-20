package crawler;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;

import preprocess.PreprocessorManager;
import support.database.DBFactory;
import support.model.Tweet;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.RateLimitStatusListener;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterCrawler {
	private String ConsumerKey;
	private String ConsumerSecret;
	private String AccessKey;
	private String AccessSecret;
	private TwitterStreamFactory twitterStreamFactory;
	
	private PreprocessorManager manager;
	
	public TwitterCrawler(){
			try {
				//BasicConfigurator.configure();
				InitializeKey();
				InitializeTwitter();
			} catch (IOException | TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void InitializeKey() throws IOException{
		Properties property = new Properties();
		InputStream input = null;
		
		input = new FileInputStream("./resources/twitter.properties");
		property.load(input);
		
		ConsumerKey = property.getProperty("ConsumerKey");
		ConsumerSecret = property.getProperty("ConsumerSecret");
		AccessKey = property.getProperty("AccessKey");
		AccessSecret = property.getProperty("AccessSecret");
		
	}
	
	public void InitializeTwitter() throws TwitterException, IOException {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
			.setOAuthConsumerKey(ConsumerKey)
			.setOAuthConsumerSecret(ConsumerSecret)
			.setOAuthAccessToken(AccessKey)
			.setOAuthAccessTokenSecret(AccessSecret);
		
		twitterStreamFactory = new TwitterStreamFactory(cb.build());
	}

	public void CrawlTweet() {
		
		UserStreamListener listener = new UserStreamListener(){
			private int tweetCount = 0;

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatus(Status status) {
				// TODO Auto-generated stub
				System.out.println("onStatus @" + status.getUser().getScreenName() + " - " + status.getText());
				System.out.println(tweetCount);
				tweetCount++;		
				StoreTweets(status);
				
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onException(Exception e) {
				// TODO Auto-generated method stub
				System.out.println("This tweet cannot be stored");	
			}

			@Override
			public void onBlock(User arg0, User arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDeletionNotice(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDirectMessage(DirectMessage arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFavorite(User arg0, User arg1, Status arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFollow(User source, User followedUser) {
				// TODO Auto-generated method stub
			    
			}

			@Override
			public void onFriendList(long[] friendIds) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onUnblock(User arg0, User arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUnfavorite(User arg0, User arg1, Status arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUnfollow(User arg0, User arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListCreation(User arg0, UserList arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListDeletion(User arg0, UserList arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListMemberAddition(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListMemberDeletion(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListSubscription(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListUnsubscription(User arg0, User arg1,
					UserList arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserListUpdate(User arg0, UserList arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onUserProfileUpdate(User arg0) {
				// TODO Auto-generated method stub
				
			}
			
			private void StoreTweets(Status status){
				Tweet tweet = new Tweet(status);
				try {
					tweet.StoreTweet();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		
		FilterQuery fq = new FilterQuery();
		String keywords[] = {"#reliefPH","#nopower", "#nowater", "#roadalert", "#tracingPH", "#rescuePH", "#floodPH", "#chedengph"};
		fq.track(keywords);
		
		TwitterStream tweetStream = twitterStreamFactory.getInstance();
		tweetStream.addListener(listener);
		tweetStream.filter(fq);

	}
	
}


