package featureextraction;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FeatureExtractor fe = new FeatureExtractor();
		String tweets = "./resources/tweets/Categorized.csv";
		String ngram = "./resources/model/ngram/char-ngram";
		String word = "./resources/model/wordcount/words";
		fe.readModel(ngram, FeatureExtractor.NGRAM);
		fe.readModel(word, FeatureExtractor.WORD);
		fe.extractFeatures(tweets);
		
	}

}
