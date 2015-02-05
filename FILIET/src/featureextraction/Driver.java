package featureextraction;




public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String category = "ch";
		String tweets = "./resources/tweets/ruby-datasets/original/combined-bin-"+category+".csv";
		String ngram = "./resources/model/ngram/ruby-ngram";
		String word = "./resources/model/word/ruby-word";
		String saveModel = "./resources/tweets/test-extracted/ruby-tfidf/ruby-combined-bin-"+category+".csv";
		//String testTweets = "./resources/test-extracte/Batch 2/test-D.csv";

		
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
