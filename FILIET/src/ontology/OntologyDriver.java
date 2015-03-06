package ontology;

import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;
import ontology.retriever.OntologyRetriever;

public class OntologyDriver {
	public static void main(String args[]) {
		CallForHelpTweet oCH = new CallForHelpTweet();
		CasualtiesAndDamageTweet oCD = new CasualtiesAndDamageTweet();
		CautionAndAdviceTweet oCA = new CautionAndAdviceTweet();
		DonationTweet oD = new DonationTweet();
		
		OntologyModule oModule = new OntologyModule();
		
		// SAMPLE INITIALIZATION FOR CAUTION AND ADVICE REPORTS
		oCA.setTweetHandle("theonlykyleeeee");
		oCA.setTweetContent("BWAHAHAHAHAHA RT WARNING! Ito ay isang tweet!");
		oCA.setTweetGeoLocation("10.00000121, 145.345300023");
		oCA.setLocationInTweet("Guadalupe");
		oCA.setTweetTimestamp("12/27/2014:00:13:67:40");
		oCA.setTweetDate("December 27, 2014");
		oCA.setTweetAdvice("WARNING! Ito ay isang advice na tweet!");

		// SAMPLE INITIALIZATION FOR CASUALTIES AND DAMAGE REPORTS
		oCD.setTweetHandle("addicteduserrr");
		oCD.setTweetContent("PRAY! RT Binaha ang classrooms ng mga estudyante ng ERV Elem School");
		oCD.setTweetGeoLocation("109.00540121, 45.378000003");
		oCD.setLocationInTweet("Quezon City");
		oCD.setTweetTimestamp("12/27/2014:00:14:47:24");
		oCD.setTweetDate("December 27, 2014");
		oCD.setVictimName("Mga estudyante ng ERV Elem School");
		oCD.setObjectName("ERV Elem School");
		oCD.setObjectDetails("Binaha ang classrooms.");

		// SAMPLE INITIALIZATION FOR DONATION REPORTS
		oD.setTweetHandle("vilsonluuufffy");
		oD.setTweetContent("Hi everyone! The students of ERV Elem School are in need of 1000 pcs of notebooks and bags!");
		oD.setTweetGeoLocation("9.011112321, 231.34569903");
		oD.setLocationInTweet("Makati City");
		oD.setTweetTimestamp("12/30/2014:01:44:09:10");
		oD.setTweetDate("December 30, 2014");
		oD.setResourceName("notebooks and bags");
		oD.setResourceDetails("1000 pcs");
		oD.setVictimName("students of ERV Elem School");
		
		// SAMPLE INITIALIZATION FOR CALL FOR HELP REPORTS
		oCH.setTweetHandle("TheJPGarcia");
		oCH.setTweetContent("Ang mga pamilya ng mga sundalo sa Brgy. Trese ay nangangailangan ng tulong!");
		oCH.setTweetGeoLocation("418.582912321, 102.51084911");
		oCH.setLocationInTweet("Brgy. Trese");
		oCH.setTweetTimestamp("03/21/2015:03:27:00:34");
		oCH.setTweetDate("March 21, 2015");
		oCH.setVictimName("pamilya ng mga sundalo");
		
//		try {
//			oModule.loadOntology();
//			
//			oModule.addCautionAndAdviceReport(oCA);
//			oModule.addCasualtiesAndDamageReport(oCD);
//			oModule.addDonationReport(oD);
//			oModule.addCallForHelpReport(oCH);
//			
//			oModule.displayStoredTweets();
//			oModule.removeOntologyFromManager();
//		} catch (OWLOntologyCreationException e) {
//			e.printStackTrace();
//		}
		
//		OntologyRetriever or = new OntologyRetriever();
//		try {
//			or.loadOntology();
//			or.getStoredTweets();
//			or.removeOntologyFromManager();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
