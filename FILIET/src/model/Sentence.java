package model;

import java.util.ArrayList;

import classifier.AttributeList;

public class Sentence {
	
	// this class contains the information collected in the crawler
	private Tweet tweets;
	// this class contains other attributes such as word features, n-grams, tweet length.
	private AttributeList attributes;
	// this contains the tokens
	private ArrayList<Token> sentence;
	
	public Sentence(){
		this.sentence = new ArrayList<>();
	}
	
	/*
	 * This will display the tokens
	 */
	public void DisplaySentence(){
		// code later
	}
	
	public void AddToken(Token token){
		sentence.add(token);
	}
	
	public ArrayList<Token> GetSentence(){
		return this.sentence;
	}
	
	public void SetSentence(ArrayList<Token> tokens){
		this.sentence = tokens;
	}
	
	public int GetLength(){
		return this.sentence.size();
	}
	
	public Tweet getTweets() {
		return tweets;
	}

	public void setTweets(Tweet tweets) {
		this.tweets = tweets;
	}

	public AttributeList getAttributes() {
		return attributes;
	}

	public void setAttributes(AttributeList attributes) {
		this.attributes = attributes;
	}

	public ArrayList<Token> getSentence() {
		return sentence;
	}

	public void setSentence(ArrayList<Token> sentence) {
		this.sentence = sentence;
	}

	/*
	 * Get a token using the word
	 */
	public Token GetToken(String word){
		for(Token token: sentence){
			if(token.getWord().equalsIgnoreCase(word)){
				return token;
			}
		}
		return null;
	}
	
	/*
	 * Get a token using index
	 */
	public Token GetToken(int i){
		if(i < this.sentence.size()){
			return this.sentence.get(i);
		}
		
		return null;
	}
	
	/*
	 * Replace the ith token
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
