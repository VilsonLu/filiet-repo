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

	private static String empty = "na";

	public static CautionAndAdviceTweet bindCA(Sentence sentence) {

		CautionAndAdviceTweet ca = new CautionAndAdviceTweet();
		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();

		// Get the location in tweet
		String locationInTweet = "";
		String advice = "";
		for (PostExtractedInformation post : extract) {
			List<ExtractedInformation> eInfo = post.getCompiledInformation();
			for (ExtractedInformation e : eInfo) {
				// location
				if (e.getInformationType() != null) {
					if (e.getInformationType().equalsIgnoreCase("location")) {
						locationInTweet += e.getValue().getWord() + " ";
					} else if (e.getInformationType()
							.equalsIgnoreCase("ADVICE")) {
						advice += e.getValue().getWord() + " ";
					}
				} 
			}
		}

		// Caution and Advice
		if (locationInTweet != null)
			ca.setLocationInTweet(locationInTweet);

		if (advice != null)
			ca.setTweetAdvice(advice);

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

		return ca;
	}

	public static CasualtiesAndDamageTweet bindCD(Sentence sentence) {
		CasualtiesAndDamageTweet cd = new CasualtiesAndDamageTweet();

		String victimName = "";
		String objectDetail = "";
		String objectName = "";
		String locationInTweet = "";

		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();
		if (extract != null) {
			for (PostExtractedInformation post : extract) {
				List<ExtractedInformation> eInfo = post
						.getCompiledInformation();
				for (ExtractedInformation i : eInfo) {
					if (i.getInformationType() != null) {
						if (i.getInformationType().equalsIgnoreCase("DETAIL")) {
							objectDetail += i.getValue().getWord() + " ";
						} else if (i.getInformationType().equalsIgnoreCase(
								"UNIT")
								|| i.getInformationType().equalsIgnoreCase(
										"NUMBER")
								|| i.getInformationType().equalsIgnoreCase(
										"OBJECT")) {
							objectName += i.getValue().getWord() + " ";
						} else if (i.getInformationType().equalsIgnoreCase(
								"VICTIM")) {
							victimName = i.getValue().getWord();
						} else if (i.getInformationType().equalsIgnoreCase(
								"LOCATION")) {

							locationInTweet += i.getValue().getWord() + " ";

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
			cd.setObjectDetails("null");
		}

		if (locationInTweet != null) {
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

		String victimName = "";
		String resourceDetail = "";
		String resourceName = "";
		String locationInTweet = "";

		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();

		for (PostExtractedInformation post : extract) {
			List<ExtractedInformation> eInfo = post.getCompiledInformation();
			for (ExtractedInformation i : eInfo) {
				if (i.getInformationType() != null) {
					if (i.getInformationType().equalsIgnoreCase("DETAIL") || i.getInformationType().equalsIgnoreCase("NUMBER")) {
						resourceDetail += i.getValue().getWord() + " ";
					} else if (i.getInformationType().equalsIgnoreCase("RESOURCE")) {
						resourceName += i.getValue().getWord() + " ";
					} else if (i.getInformationType()
							.equalsIgnoreCase("VICTIM")) {
						victimName += i.getValue().getWord() +" ";
					} else if (i.getInformationType().equalsIgnoreCase(
							"LOCATION")) {

						locationInTweet += i.getValue().getWord() + " ";

					}
				}
			}
		}

		// Casualties And Damage
		if (victimName != null) {
			d.setVictimName(victimName);
		}

		if (resourceName != null) {
			d.setResourceName(resourceName);
		}
		if (resourceDetail != null) {
			d.setResourceDetails(resourceDetail);
		}

		if (locationInTweet != null) {
			d.setLocationInTweet(locationInTweet);
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

		String victimName = "";
		String locationInTweet = "";
		List<PostExtractedInformation> extract = sentence
				.getExtractedInformation();

		for (PostExtractedInformation post : extract) {
			List<ExtractedInformation> eInfo = post.getCompiledInformation();
			for (ExtractedInformation i : eInfo) {
				if (i.getInformationType() != null) {
					if (i.getInformationType().equalsIgnoreCase("VICTIM")) {
						victimName = i.getValue().getWord();
					} else if (i.getInformationType().equalsIgnoreCase(
							"LOCATION")) {
						locationInTweet += i.getValue().getWord();
					
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

		if (locationInTweet != null) {
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
