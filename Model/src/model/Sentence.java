package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Sentence {
	
	// this class contains the information collected in the crawler
	private Tweet tweets;
	// this class contains other attributes such as word features, n-grams, tweet length.
	private HashMap<String, Integer> extractedWordFeatures;
	private HashMap<String, Integer> extractedNgramFeatures;
	private HashMap<String, Integer> extractedFeatures;
	// this contains the tokens
	private ArrayList<Token> sentence;
	
	public Sentence(){
		this.sentence = new ArrayList<>();
		this.extractedWordFeatures = new HashMap<>();
	}
	
	/**
	 * @return the tweets
	 */
	public Tweet getTweets() {
		return tweets;
	}

	/**
	 * @param tweets the tweets to set
	 */
	public void setTweets(Tweet tweets) {
		this.tweets = tweets;
	}


	/**
	 * @return the extractedWordFeatures
	 */
	public HashMap<String, Integer> getExtractedWordFeatures() {
		return extractedWordFeatures;
	}

	/**
	 * @param extractedWordFeatures the extractedWordFeatures to set
	 */
	public void setExtractedWordFeatures(
			HashMap<String, Integer> extractedWordFeatures) {
		this.extractedWordFeatures = extractedWordFeatures;
	}

	/**
	 * @return the extractedNgramFeatures
	 */
	public HashMap<String, Integer> getExtractedNgramFeatures() {
		return extractedNgramFeatures;
	}

	/**
	 * @param extractedNgramFeatures the extractedNgramFeatures to set
	 */
	public void setExtractedNgramFeatures(
			HashMap<String, Integer> extractedNgramFeatures) {
		this.extractedNgramFeatures = extractedNgramFeatures;
	}

	/**
	 * @return the extractedFeatures
	 */
	public HashMap<String, Integer> getExtractedFeatures() {
		return extractedFeatures;
	}

	/**
	 * @param extractedFeatures the extractedFeatures to set
	 */
	public void setExtractedFeatures(HashMap<String, Integer> extractedFeatures) {
		this.extractedFeatures = extractedFeatures;
	}

	/**
	 * @return the sentence
	 */
	public ArrayList<Token> getSentence() {
		return sentence;
	}

	/**
	 * @param sentence the sentence to set
	 */
	public void setSentence(ArrayList<Token> sentence) {
		this.sentence = sentence;
	}

	/**
	 * This will display the tokens
	 */
	public void DisplaySentence(){
		// code later
	}
	
	/**
	 * Add token to the sentence
	 * @param token
	 */
	public void addToken(Token token){
		sentence.add(token);
	}
	/**
	 * Get the length (number of tokens) of the tweet
	 * @return int
	 */
	
	public int getLength(){
		return this.sentence.size();
	}

	/**
	 * Get a token using the word
	 * @param word
	 */
	public Token getToken(String word){
		for(Token token: sentence){
			if(token.getWord().equalsIgnoreCase(word)){
				return token;
			}
		}
		return null;
	}
	
	/**
	 * Get a token using index
	 * @param ith index
	 * @return the ith token
	 */
	public Token getToken(int i){
		if(i < this.sentence.size()){
			return this.sentence.get(i);
		}
		
		return null;
	}
	
	/**
	 * Replace the ith token
	 * @param i - ith index
	 */
	public void replaceToken(int i, Token token){
		this.sentence.set(i, token);
	}
	
	public void printSentence(){
		for(Token token: sentence){
			token.PrintToken();
			System.out.print(" ");
		}
		
		System.out.println();
	}
	
	
}
