package binder;

import java.util.List;

import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;
import support.model.ExtractedInformation;
import support.model.PostExtractedInformation;
import support.model.Sentence;
import support.model.Tweet;

public class Binder {

	public static CautionAndAdviceTweet bindCA(Sentence sentence) {

		CautionAndAdviceTweet ca = new CautionAndAdviceTweet();
		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();

		// Get the location in tweet
		String locationInTweet = null;
		String advice = null;
		for (PostExtractedInformation post : extract) {
			List<ExtractedInformation> eInfo = post.getCompiledInformation();
			for (ExtractedInformation e : eInfo) {
				// location
				if (e.getInformationType() != null) {
					if (e.getInformationType().equalsIgnoreCase("location")) {
						if (locationInTweet == null) {
							locationInTweet = e.getValue().getWord();
						} else {
							locationInTweet += ", " + e.getValue().getWord();
						}
					}
				} else {
					if (advice != null)
						advice += " " + e.getValue().getWord();
					else
						advice = e.getValue().getWord();
				}
			}
		}
		System.out.println(locationInTweet);
		System.out.println(advice);
		// Tweet
		Tweet tweet = sentence.getTweets();
		ca.setTweetContent(sentence.getRawTweet());
		ca.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			ca.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + ","
					+ Double.toString(tweet.getLongitude()));
		} else {
			ca.setTweetGeoLocation("null");
		}
		ca.setTweetTimestamp("null");
		ca.setTweetDate("null");

		// Caution and Advice
		ca.setLocationInTweet(locationInTweet);
		ca.setTweetAdvice(advice);

		return ca;
	}

	public static CasualtiesAndDamageTweet bindCD(Sentence sentence) {
		CasualtiesAndDamageTweet cd = new CasualtiesAndDamageTweet();

		String victimName = null;
		String objectDetail = null;
		String objectName = null;
		String locationInTweet = null;

		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();

		for (PostExtractedInformation post : extract) {
			List<ExtractedInformation> eInfo = post.getCompiledInformation();
			for (ExtractedInformation i : eInfo) {
				if (i.getInformationType() != null) {
					if (i.getInformationType().equalsIgnoreCase("DETAIL")) {
						objectDetail = i.getValue().getWord();
					} else if (i.getInformationType().equalsIgnoreCase("UNIT")) {
						objectName = i.getValue().getWord();
					} else if (i.getInformationType()
							.equalsIgnoreCase("VICTIM")) {
						victimName = i.getValue().getWord();
					} else if (i.getInformationType().equalsIgnoreCase("LOCATION")){
						if (locationInTweet == null) {
							locationInTweet = i.getValue().getWord();
						} else {
							locationInTweet += ", " + i.getValue().getWord();
						}
					}
				}
			}
		}

		// Casualties And Damage
		if (victimName != null) {
			cd.setVictimName(victimName);
		} else {
			cd.setVictimName("null");
		}

		if (objectName != null) {
			cd.setObjectName(objectName);
		} else {
			cd.setObjectName("null");
		}

		if (objectDetail != null) {
			cd.setObjectDetails(objectDetail);
		} else {
			cd.setObjectName("null");
		}
		
		if(locationInTweet != null){
			cd.setLocationInTweet(locationInTweet);
		} else {
			cd.setLocationInTweet("null");
		}

		// Tweet
		Tweet tweet = sentence.getTweets();
		cd.setTweetContent(sentence.getRawTweet());
		cd.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			cd.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + ","
					+ Double.toString(tweet.getLongitude()));
		} else {
			cd.setTweetGeoLocation("null");
		}
		cd.setTweetTimestamp("null");
		cd.setTweetDate("null");

		return cd;

	}
	
	public static DonationTweet bindD(Sentence sentence) {
		DonationTweet d = new DonationTweet();

		String victimName = null;
		String resourceDetail = null;
		String resourceName = null;
		String locationInTweet = null;

		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();

		for (PostExtractedInformation post : extract) {
			List<ExtractedInformation> eInfo = post.getCompiledInformation();
			for (ExtractedInformation i : eInfo) {
				if (i.getInformationType() != null) {
					if (i.getInformationType().equalsIgnoreCase("DETAIL")) {
						resourceDetail = i.getValue().getWord();
					} else if (i.getInformationType().equalsIgnoreCase("UNIT")) {
						resourceName = i.getValue().getWord();
					} else if (i.getInformationType()
							.equalsIgnoreCase("VICTIM")) {
						victimName = i.getValue().getWord();
					} else if (i.getInformationType().equalsIgnoreCase("LOCATION")){
						if (locationInTweet == null) {
							locationInTweet = i.getValue().getWord();
						} else {
							locationInTweet += ", " + i.getValue().getWord();
						}
					}
				}
			}
		}

		// Casualties And Damage
		if (victimName != null) {
			d.setVictimName(victimName);
		} else {
			d.setVictimName("null");
		}

		if (resourceName != null) {
			d.setResourceName(resourceName);
		} else {
			d.setResourceName("null");
		}

		if (resourceDetail != null) {
			d.setResourceDetails(resourceDetail);
		} else {
			d.setResourceName("null");
		}
		
		if(locationInTweet != null){
			d.setLocationInTweet(locationInTweet);
		} else {
			d.setLocationInTweet("null");
		}

		// Tweet
		Tweet tweet = sentence.getTweets();
		d.setTweetContent(sentence.getRawTweet());
		d.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			d.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + ","
					+ Double.toString(tweet.getLongitude()));
		} else {
			d.setTweetGeoLocation("null");
		}
		d.setTweetTimestamp("null");
		d.setTweetDate("null");

		return d;

	}
	
	public static CallForHelpTweet bindCH(Sentence sentence) {
		CallForHelpTweet ch = new CallForHelpTweet();

		String victimName = null;
		String locationInTweet = null;
		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();

		for (PostExtractedInformation post : extract) {
			List<ExtractedInformation> eInfo = post.getCompiledInformation();
			for (ExtractedInformation i : eInfo) {
				if (i.getInformationType() != null) {
					if (i.getInformationType()
							.equalsIgnoreCase("VICTIM")) {
						victimName = i.getValue().getWord();
					} else if (i.getInformationType().equalsIgnoreCase("LOCATION")){
						if (locationInTweet == null) {
							locationInTweet = i.getValue().getWord();
						} else {
							locationInTweet += ", " + i.getValue().getWord();
						}
					}
				}
			}
		}

		// Casualties And Damage
		if (victimName != null) {
			ch.setVictimName(victimName);
		} else {
			ch.setVictimName("null");
		}
		
		if(locationInTweet != null){
			ch.setLocationInTweet(locationInTweet);
		} else {
			ch.setLocationInTweet("null");
		}

		// Tweet
		Tweet tweet = sentence.getTweets();
		ch.setTweetContent(sentence.getRawTweet());
		ch.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			ch.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + ","
					+ Double.toString(tweet.getLongitude()));
		} else {
			ch.setTweetGeoLocation("null");
		}
		ch.setTweetTimestamp("null");
		ch.setTweetDate("null");

		return ch;

	}
	
}
