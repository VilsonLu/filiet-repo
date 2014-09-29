package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Sentence {
	
	// this class contains the information collected in the crawler
	private Tweet tweets;
	// this class contains other attributes such as word features, n-grams, tweet length.
	private HashMap<String, Integer> attributes;
	// this contains the tokens
	private ArrayList<Token> sentence;
	
	public Sentence(){
		this.sentence = new ArrayList<>();
		this.attributes = new HashMap<>();
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
	 * @return the attributes
	 */
	public HashMap<String, Integer> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(HashMap<String, Integer> attributes) {
		this.attributes = attributes;
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
	public void AddToken(Token token){
		sentence.add(token);
	}
	/**
	 * Get the length (number of tokens) of the tweet
	 * @return int
	 */
	public int GetLength(){
		return this.sentence.size();
	}
	/**
	 * Get a token using the word
	 * @param word
	 */
	public Token GetToken(String word){
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
	public Token GetToken(int i){
		if(i < this.sentence.size()){
			return this.sentence.get(i);
		}
		
		return null;
	}
	
	/**
	 * Replace the ith token
	 * @param i - ith index
	 */
	public void ReplaceToken(int i, Token token){
		this.sentence.set(i, token);
	}
	
	public void PrintSentence(){
		for(Token token: sentence){
			token.PrintToken();
			System.out.print(" ");
		}
		
		System.out.println();
	}
}
