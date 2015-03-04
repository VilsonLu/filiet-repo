package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import support.model.Sentence;
import support.model.Tweet;

import com.csvreader.CsvReader;

public class Testing {
	
	public static List<Sentence> readTestData(String path) throws IOException{
		List<Sentence>  sentences = new ArrayList<Sentence>();
		FileReader testData = new FileReader(path);
		CsvReader reader = new CsvReader(testData,';');
		reader.readHeaders();
		String record = "";
		int i =0;
		while(reader.readRecord()){
			
			Sentence sentence = new Sentence();
			Tweet tweet = new Tweet();
			tweet.setTweetID(Long.valueOf(reader.get(0)));
			tweet.setUser(reader.get(1));
			tweet.setTweet(reader.get(2));
		
			if(!reader.get(3).equalsIgnoreCase("NULL")){
				tweet.setLatitude(Double.valueOf(reader.get(3)));
			}
			if(!reader.get(4).equalsIgnoreCase("NULL")){
				tweet.setLongitude(Double.valueOf(reader.get(4)));
			}
			
			
			tweet.setHashtag(isBoolean(reader.get(5)));
			tweet.setURL(isBoolean(reader.get(6)));
			tweet.setRetweet(isBoolean(reader.get(7)));
			tweet.setLanguage(reader.get(8));
			tweet.setCategory(reader.get(9));
			sentence.setTweets(tweet);
			sentences.add(sentence);
		}
		return sentences;
	}
	
	public static Boolean isBoolean(String value){
		if(value.equalsIgnoreCase("1"))
			return true;
		else 
			return false;
	}
}
