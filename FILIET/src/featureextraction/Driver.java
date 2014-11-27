package featureextraction;




public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String tweets = "./resources/tweets/test-tweets/Batch 2/CD-Binary.csv";
		String ngram = "./resources/model/ngram/test-charngram";
		String word = "./resources/model/word/test-wordcounts";
		String saveModel = "./resources/tweets/test-extracted/Batch 2/test-CD.csv";
		String testTweets = "./resources/test-extracte/Batch 2/test-D.csv";

		FeatureExtractor fe = new FeatureExtractor(word,ngram);
		fe.extractFeatures(tweets,saveModel);
		
		/*List<Sentence> sentences = Testing.readTestData(testTweets);
	
		int count = 1;
		for(Sentence sentence:sentences){
			fe.extract(sentence);
			System.out.println("Test " + count +":");
			Set<Map.Entry<String,Integer>> itSet = sentence.getExtractedWordFeatures().entrySet();
			
			for(Map.Entry<String, Integer> entry: itSet){
				System.out.println(entry.getKey()+": "+entry.getValue());
			}
			
			itSet = sentence.getExtractedNgramFeatures().entrySet();
			
			for(Map.Entry<String, Integer> entry: itSet){
				System.out.println(entry.getKey()+": "+entry.getValue());
			}
			count++;
		}*/
		
		
		
		
	
		
		
	}
	
	

}