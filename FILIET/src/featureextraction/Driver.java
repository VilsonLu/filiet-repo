package featureextraction;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String tweets = "./resources/tweets/Categorized.csv";
		String ngram = "./resources/model/ngram/char-ngram";
		String word = "./resources/model/wordcount/words";
		String saveModel = "./resources/model/wordcount/extractedFeatureTweets.csv";
		FeatureExtractor fe = new FeatureExtractor(word,ngram);
		fe.extractFeatures(tweets,saveModel);
//		String text = "Ilang lugar naman sa Lungsod ng Makati, lubog pa rin sa baha. #MarioPH #FloodPH";
//		//fe.extract(text);
//		fe.extractNgramFeatures(text);
	}

}
