package ontology.retriever;

import java.util.ArrayList;

import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;

public class RetrievedTweet {
	ArrayList<CallForHelpTweet> retrievedCFHTweets;
	ArrayList<CasualtiesAndDamageTweet> retrievedCADTweets;
	ArrayList<CautionAndAdviceTweet> retrievedCATweets;
	ArrayList<DonationTweet> retrievedDTweets;
	
	public ArrayList<CallForHelpTweet> getRetrievedCFHTweets() {
		return retrievedCFHTweets;
	}
	public void setRetrievedCFHTweets(ArrayList<CallForHelpTweet> retrievedCFHTweets) {
		this.retrievedCFHTweets = retrievedCFHTweets;
	}
	public ArrayList<CasualtiesAndDamageTweet> getRetrievedCADTweets() {
		return retrievedCADTweets;
	}
	public void setRetrievedCADTweets(
			ArrayList<CasualtiesAndDamageTweet> retrievedCADTweets) {
		this.retrievedCADTweets = retrievedCADTweets;
	}
	public ArrayList<CautionAndAdviceTweet> getRetrievedCATweets() {
		return retrievedCATweets;
	}
	public void setRetrievedCATweets(
			ArrayList<CautionAndAdviceTweet> retrievedCATweets) {
		this.retrievedCATweets = retrievedCATweets;
	}
	public ArrayList<DonationTweet> getRetrievedDTweets() {
		return retrievedDTweets;
	}
	public void setRetrievedDTweets(ArrayList<DonationTweet> retrievedDTweets) {
		this.retrievedDTweets = retrievedDTweets;
	}
}
