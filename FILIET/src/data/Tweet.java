package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import database.DBFactory;

public class Tweet {
	private long TweetID;
	private String User;
	private String Tweet;
	private Double Latitude;
	private Double Longitude;
	private Boolean Retweet;
	private Boolean Hashtag;
	private Boolean URL;
	
	private Status Status;
	
	public Tweet(Status status){
		this.Status = status;
		this.TweetID = status.getId();
		this.Tweet = status.getText().toString();
		this.User = status.getUser().getScreenName();
		
		if(status.getGeoLocation() != null){
			this.Latitude = status.getGeoLocation().getLatitude();
			this.Longitude = status.getGeoLocation().getLongitude();
		}
		
		this.Retweet = status.isRetweet();
		this.Hashtag = isHashtag(status);
		this.URL = isURL(status);
		
	}
	
	public Boolean isURL(Status status){
		URLEntity[] urls = status.getURLEntities();
		
		if(urls.length == 0)
			return false;
		else
			return true;
	}
	
	public Boolean isHashtag(Status status){
		HashtagEntity[] hashtags = status.getHashtagEntities();
		
		if(hashtags.length == 0)
			return false;
		else
			return true;
	}
	
	
	public long getTweetID() {
		return TweetID;
	}
	public void setTweetID(long tweetID) {
		TweetID = tweetID;
	}
	
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	
	public String getTweet() {
		return Tweet;
	}
	public void setTweet(String tweet) {
		Tweet = tweet;
	}
	
	public Double getLatitude() {
		return Latitude;
	}
	public void setLatitude(Double latitude) {
		Latitude = latitude;
	}
	
	public Double getLongitude() {
		return Longitude;
	}
	public void setLongitude(Double longitude) {
		Longitude = longitude;
	}
	
	/*
	 * This stores the tweet into the database
	 */
	public void StoreTweet() throws SQLException{

		DBFactory factory = DBFactory.getInstance();
		Connection connection = factory.getConnection();
		
		String query = "INSERT INTO tweets (TweetID, User, Tweet, Latitude, Longitude, IsHashtag, IsURL, IsRetweet) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
	
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setLong(1, this.TweetID);
		stmt.setString(2,this.User);
		stmt.setString(3, this.Tweet);
		
		if(Status.getGeoLocation() != null){
			stmt.setDouble(4, this.Latitude);
			stmt.setDouble(5, this.Longitude);
		} else {
			stmt.setNull(4, java.sql.Types.DOUBLE);
			stmt.setNull(5, java.sql.Types.DOUBLE);
		}
		
		stmt.setBoolean(6, this.Hashtag);
		stmt.setBoolean(7, this.URL);
		stmt.setBoolean(8, this.Retweet);
		
		stmt.executeUpdate();
		
		connection.close();
	
	}
}
