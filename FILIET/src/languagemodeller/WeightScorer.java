package languagemodeller;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WeightScorer {

	private DocumentFrequency categoryDataset;
	private DocumentFrequency documentDataset;
	
	private Map<String, Double> weights;

	
	public WeightScorer(DocumentFrequency categoryDataset, DocumentFrequency documentDataset){
		this.categoryDataset = categoryDataset;
		this.documentDataset = documentDataset;
		weights = new HashMap<String, Double>();
	}
	
	private Double computeTF(String term){
		int frequency = categoryDataset.getWordFrequency(term);
		
		if(frequency > 0){
			return 1 + Math.log(frequency);
		} 
		
		return 0.0;
	}
	
	private Double computeTFIDF(String term){
		Double idf = Math.log(documentDataset.getDocumentCount() / documentDataset.getWordFrequency(term));
		Double tf = computeTF(term);
		return tf * idf;
	}
	
	public Map<String, Double> computeWeights(){
		
		List<String> vocabularies = categoryDataset.getListOfWords();
		System.out.println(vocabularies.size());
		for(String word: vocabularies){
			weights.put(word, computeTFIDF(word));
			System.out.println(word+":"+weights.get(word));
		}
		
		return weights;
	}
	
	public void saveResults(String path) throws FileNotFoundException{
		System.out.println("Saving model...");
		PrintWriter writer = new PrintWriter(path);
		Set<Map.Entry<String, Double>> entries = weights.entrySet();
		for (Map.Entry<String, Double> entry : entries) {
			writer.println(entry.getKey() + " " + entry.getValue());
		}
		writer.close();
		System.out.println("Saving successful...");
	}
	
	
	
}
