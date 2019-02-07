package Tweet;

public class Tweet {
	// TWEET INFORMATION
	private String tweetHandle;
	private String tweetContent;
	
	// LOCATION INFORMATION
	private String tweetGeoLocation;
	private String locationInTweet;
	
	// TIMESTAMP INFORMATION
	private String tweetTimestamp;
	private String tweetDate;
	
	public Tweet(){}

	public String getTweetHandle() {
		return tweetHandle;
	}

	public void setTweetHandle(String tweetHandle) {
		this.tweetHandle = tweetHandle;
	}

	public String getTweetContent() {
		return tweetContent;
	}

	public void setTweetContent(String tweetContent) {
		this.tweetContent = tweetContent;
	}

	public String getTweetGeoLocation() {
		return tweetGeoLocation;
	}

	public void setTweetGeoLocation(String tweetGeoLocation) {
		this.tweetGeoLocation = tweetGeoLocation;
	}

	public String getLocationInTweet() {
		return locationInTweet;
	}

	public void setLocationInTweet(String locationInTweet) {
		this.locationInTweet = locationInTweet;
	}

	public String getTweetTimestamp() {
		return tweetTimestamp;
	}

	public void setTweetTimestamp(String tweetTimestamp) {
		this.tweetTimestamp = tweetTimestamp;
	}

	public String getTweetDate() {
		return tweetDate;
	}

	public void setTweetDate(String tweetDate) {
		this.tweetDate = tweetDate;
	}
	
	
}
