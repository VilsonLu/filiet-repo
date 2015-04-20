package binder;

import java.util.List;

import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;
import support.languagemodeller.Filter;
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
		String locationInTweet = "";
		String advice = "";
		if (extract != null) {
			for (PostExtractedInformation post : extract) {
				List<ExtractedInformation> eInfo = post
						.getCompiledInformation();
				for (ExtractedInformation e : eInfo) {
					// location
					String type = e.getInformationType();
					if (type != null) {
						if (type.equalsIgnoreCase("ADVICE")) {
							advice += e.getValue().getWord() + " ";
						} else if (type.equalsIgnoreCase("LOCATION")) {
							locationInTweet += e.getValue().getWord() + " ";
						}
					}
				}
			}
		}

		// Location
		ca.setLocationInTweet(Filter.removeNonAlphaNumeric(locationInTweet.trim()));
		// Advice
		ca.setTweetAdvice(Filter.removeNonAlphaNumeric(advice.trim()));

		// Tweet Information
		Tweet tweet = sentence.getTweets();
		ca.setTweetContent(Filter.removeNonAlphaNumeric(sentence.getRawTweet()));
		ca.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			ca.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + " "
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
			cd.setVictimName(Filter.removeNonAlphaNumeric(victimName));
		}

		if (objectName != null) {
			cd.setObjectName(Filter.removeNonAlphaNumeric(objectName));
		}

		if (objectDetail != null) {
			cd.setObjectDetails(Filter.removeNonAlphaNumeric(objectDetail));
		}

		if (locationInTweet != null) {
			cd.setLocationInTweet(Filter.removeNonAlphaNumeric(locationInTweet));
		}

		// Tweet
		Tweet tweet = sentence.getTweets();
		cd.setTweetContent(Filter.removeNonAlphaNumeric(sentence.getRawTweet()));
		cd.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			cd.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + " "
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

		if (extract != null) {
			for (PostExtractedInformation post : extract) {
				List<ExtractedInformation> eInfo = post
						.getCompiledInformation();
				for (ExtractedInformation i : eInfo) {
					if (i.getInformationType() != null) {
						if (i.getInformationType().equalsIgnoreCase("DETAIL")
								|| i.getInformationType().equalsIgnoreCase(
										"NUMBER")) {
							resourceDetail += i.getValue().getWord() + " ";
						} else if (i.getInformationType().equalsIgnoreCase(
								"RESOURCE")) {
							resourceName += i.getValue().getWord() + " ";
						} else if (i.getInformationType().equalsIgnoreCase(
								"VICTIM")) {
							victimName += i.getValue().getWord() + " ";
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
			d.setVictimName(Filter.removeNonAlphaNumeric(victimName));
		}

		if (resourceName != null) {
			d.setResourceName(Filter.removeNonAlphaNumeric(resourceName));
		}
		if (resourceDetail != null) {
			d.setResourceDetails(Filter.removeNonAlphaNumeric(resourceDetail));
		}

		if (locationInTweet != null) {
			d.setLocationInTweet(Filter.removeNonAlphaNumeric(locationInTweet));
		}

		// Tweet
		Tweet tweet = sentence.getTweets();
		d.setTweetContent(Filter.removeNonAlphaNumeric(sentence.getRawTweet()));
		d.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			d.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + " "
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

		if (extract != null) {
			for (PostExtractedInformation post : extract) {
				List<ExtractedInformation> eInfo = post
						.getCompiledInformation();
				for (ExtractedInformation i : eInfo) {
					if (i.getInformationType() != null) {
						if (i.getInformationType().equalsIgnoreCase("VICTIM")) {
							victimName = i.getValue().getWord();
						} else if (i.getInformationType().equalsIgnoreCase(
								"LOCATION")) {
							locationInTweet += i.getValue().getWord() +" ";

						}
					}
				}
			}
		}
		// Casualties And Damage
		if (victimName != null) {
			ch.setVictimName(Filter.removeNonAlphaNumeric(victimName));
		}

		if (locationInTweet != null) {
			ch.setLocationInTweet(Filter.removeNonAlphaNumeric(locationInTweet));
		}

		// Tweet
		Tweet tweet = sentence.getTweets();
		ch.setTweetContent(Filter.removeNonAlphaNumeric(sentence.getRawTweet()));
		ch.setTweetHandle(tweet.getUser());
		if (tweet.getLatitude() != null || tweet.getLongitude() != null) {
			ch.setTweetGeoLocation(Double.toString(tweet.getLatitude()) + " "
					+ Double.toString(tweet.getLongitude()));
		} else {
			ch.setTweetGeoLocation("null");
		}
		ch.setTweetTimestamp("null");
		ch.setTweetDate("null");

		return ch;

	}

}
