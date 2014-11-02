package model;

import java.util.ArrayList;

public class Corpus {
	public ArrayList<Sentence> Corpus = new ArrayList<>();
	
	public Corpus(){
		this.Corpus = new ArrayList<>(); 
	}
	
	public void AddSentence(Sentence sentence){
		if(sentence != null){
			Corpus.add(sentence);
		}
	}
	
	public void ReplaceSentence(int i, Sentence sentence){
		if(sentence != null){
			Corpus.set(i, sentence);
		}
	}
	
	public void PrintCorpus(){
		for(Sentence sentence: Corpus){
			System.out.println(sentence);
		}
	}
}
