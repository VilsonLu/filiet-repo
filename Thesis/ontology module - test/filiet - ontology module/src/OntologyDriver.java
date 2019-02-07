import org.semanticweb.owlapi.model.OWLOntologyCreationException;


public class OntologyDriver {
	/*  
	 	HOW TO INSTANTIATE OR USE THE STRING ARRYAS PROVIDED ABOVE:
	 	
	 	<+> For the 'extractionInfo' array:
	 								[for DONATION tweets]		[for CAUTION/ADVICE tweets]			[for CASUALTIES/DAMAGE]
	 		1. extractionInfo[0]	- category					- category							- category 
	 		2. extractionInfo[1]	- victimName				- tweetAdvice						- objectName
	 		3. extractionInfo[2]	- resourceName													- objectDetails
	 		4. extractionInfo[3]	- resourceDetails												- victimName
	 		
	 	<+> For the 'tweetInfo' array:
	 		1. tweetInfo[0] = value for the tweetHandle 
	 		2. tweetInfo[1] = value for the tweetContent
	 		
	 	<+> For the 'locationInfo' array:
	 		1. locationInfo[0] = value for the geoLocationOfTweet 
	 		2. locationInfo[1] = value for the locationInTweet
	 		
	 	<+> For the 'timestampInfo' array:
	 		1. timestampInfo[0] = value for the tweetTimestamp 
	 		2. timestampInfo[1] = value for the tweetDate
	*/
	
	//saveTweetToOntology(String category, String[] tweetInfo, String[] locationInfo, String[] timestampInfo)
	public static void main(String args[]) {
		String[] extractionInfo = new String[4];
		String[] tweetInfo = new String[2];
		String[] locationInfo = new String[2];
		String[] timestampInfo = new String[2];
		
		OntologyModule oMod = new OntologyModule();
		
//		extractionInfo[0] = "CA";
//		extractionInfo[1] = "WARNING! Ito ay isang advice na tweet!";
//		tweetInfo[0] = "theonlykyleeeee"; 
//		tweetInfo[1] = "BWAHAHAHAHAHA RT WARNING! Ito ay isang tweet!";
//		locationInfo[0] = "10.00000121, 145.345300023"; 
//		locationInfo[1] = "Guadalupe";
//		timestampInfo[0] = "12/27/2014:00:13:67:40"; 
//		timestampInfo[1] = "December 27, 2014";
		
//		extractionInfo[0] = "CD";
//		extractionInfo[1] = "ERV Elem School";
//		extractionInfo[2] = "Binaha ang classrooms.";
//		extractionInfo[3] = "Mga estudyante ng ERV Elem School";
//		tweetInfo[0] = "addicteduserrr"; 
//		tweetInfo[1] = "PRAY! RT Binaha ang classrooms ng mga estudyante ng ERV Elem School";
//		locationInfo[0] = "109.00540121, 45.378000003"; 
//		locationInfo[1] = "Quezon City";
//		timestampInfo[0] = "12/27/2014:00:14:47:24"; 
//		timestampInfo[1] = "December 27, 2014";
		
		extractionInfo[0] = "D";
		extractionInfo[1] = "students of ERV Elem School";
		extractionInfo[2] = "notebooks and bags";
		extractionInfo[3] = "1000 pcs";
		tweetInfo[0] = "vilsonluuufffy"; 
		tweetInfo[1] = "Hi everyone! The students of ERV Elem School are in need of 1000 pcs of notebooks and bags!";
		locationInfo[0] = "9.011112321, 231.34569903"; 
		locationInfo[1] = "Makati City";
		timestampInfo[0] = "12/30/2014:01:44:09:10"; 
		timestampInfo[1] = "December 30, 2014";
		
		try {
			oMod.loadOntology();
			//oMod.saveTweetToOntology(extractionInfo, tweetInfo, locationInfo, timestampInfo);
			oMod.displayStoredTweets();
			oMod.removeOntologyFromManager();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
}
