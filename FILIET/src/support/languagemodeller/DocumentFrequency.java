package support.languagemodeller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import opennlp.tools.ngram.NGramModel;
import opennlp.tools.util.StringList;
import preprocess.tokenizer.ArkNLPTokenizerImpl;
import preprocess.tokenizer.OpenNLPTokenizerImpl;
import preprocess.tokenizer.Tokenizer;
import support.model.Token;

public class DocumentFrequency {

	private List<String> documents;
	private Map<String, Integer> wordFrequency;
	private int documentCount = 0;
	
	public DocumentFrequency(String path) throws IOException {
		documents = new ArrayList<String>();
		wordFrequency = new HashMap<String, Integer>();
		readCorpus(path);
	};


	public Map<String, Integer> getWordFrequency() {
		return wordFrequency;
	}

	public void setWordFrequency(Map<String, Integer> wordFrequency) {
		this.wordFrequency = wordFrequency;
	}

	public int getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(int documentCount) {
		this.documentCount = documentCount;
	}
	
	public int getWordFrequency(String word){
		return wordFrequency.getOrDefault(word,1);
	}
	
	public List<String> getListOfWords(){
		return new ArrayList<String>(wordFrequency.keySet());
	}

	public void readCorpus(String path) throws IOException {
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		String line = null;
		String split = ";";
		line = br.readLine();
		
		while ((line = br.readLine()) != null) {
		
			String[] column = line.split(split);
			String tempTweet = column[2].toLowerCase();
			documents.add(tempTweet);
			documentCount++;
		}
		
		br.close();
		
		//Counts the frequency in the document
		countWordsFrequency();
		
		
	}
	
	private void countWordsFrequency(){
		Tokenizer tokenizer = new Tokenizer(new ArkNLPTokenizerImpl());
		for(String document: documents){
			List<Token> tokens = tokenizer.executeStrategy(document).getSentence();
			for(Token token: tokens){
				String word = token.getWord();
				
				if(word.equals("pahirap")){
					System.out.println("Hello <" + word  +">");
				}
				
				if(wordFrequency.get(word) != null){
					wordFrequency.replace(word, wordFrequency.get(word) + 1);
					
				} else {
					wordFrequency.put(word, 1);
				}
			}
		}
	}


}
