package featureextraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Test.Testing;
import au.com.bytecode.opencsv.CSVReader;
import opennlp.tools.util.InvalidFormatException;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import model.Sentence;
import model.Tweet;


public class Driver {

	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String tweets = "./resources/tweets/Cleaned-Categorized-JP-3.csv";
		String ngram = "./resources/model/ngram/char-ngram";
		String word = "./resources/model/wordcount/wordcounts";
		String saveModel = "./resources/tweets/preprocessed/testExtraction.csv";
		String testTweets = "./resources/tweets/testdata/test-data.csv";
		FeatureExtractor fe = new FeatureExtractor(word,ngram);
		//fe.extractFeatures(tweets,saveModel);
		
		List<Sentence> sentences = Testing.readTestData(testTweets);
	
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
		}
		
		
		
		
	
		
		
	}
	
	

}
