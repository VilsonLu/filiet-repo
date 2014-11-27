package informationextraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.util.featuregen.StringPattern;
import model.Grammar;
import model.Rule;
import model.Sentence;
import model.Token;

public class PatternMatching {

	private List<Grammar> extractionRules;
	// Path to the extraction-rules file
	private String path;
	public PatternMatching(String path) {
		extractionRules = new ArrayList<>();
		this.path = path;
	}
	
	// this reads the rule file (SOMIDIA rule format)

	private Grammar extractPatternRule(String input) {
		Rule rule = null;

		int head = 0;
		Grammar g = new Grammar();
		while (head < input.length()) {

			// New rule
			int trail = 1;
			if (input.charAt(head) == '<') {
				rule = new Rule();

				trail = head + 1;
				while (input.charAt(head) != ':') {
					head++;
				}

				// Get the left-hand side
				rule.setType(input.substring(trail, head));
				head++;

				// end of the rule
				trail = head;
				while (input.charAt(head) != '>') {
					head++;
				}

				// get the right-hand side
				rule.setValue(input.substring(trail, head));

				if ((head + 1) < input.length()) {
					head++;
					if (input.charAt(head) == '[') {
						while(input.charAt(head) != ']'){
							head++;
						}
						trail = head+1;
						while(head < input.length()-1 ){
							head++;
							if(input.charAt(head) == ' '){
								break;
							}
							
						}
						String extractedAs = input.substring(trail,head+1);
						rule.setAsExtraction(extractedAs.replace(" ", ""));
					}
				}
			}
			g.addRule(rule);
			head++;

		}

		return g;
	}

	public void loadRules() throws IOException {
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while ((line = br.readLine()) != null) {
			extractionRules.add(extractPatternRule(line));
		}
	
	}

	public List<Sentence> match(Sentence sentence) {
		List<Token> tokens = sentence.getSentence();
		List<Sentence> extractedInformation = new ArrayList<Sentence>();
		Sentence extracted = null;
		for(Grammar rp: extractionRules){
			int j = 0;
			List<Rule> rules = rp.getRules();
			extracted = new Sentence();
			for(int i=0; i<tokens.size(); i++){
				while(j<rules.size()){
					if(rules.get(j).matchToken(tokens.get(i))){
						j++;
						extracted.addToken(tokens.get(i));
						break;
					} else {
						j = 0;
						extracted = new Sentence();
						break;
					}
				}
			
			
			}

			// This checks if the rule is successfully match
			if(j==rules.size()){
				extractedInformation.add(extracted);
			}else {
				System.out.println("Rule did not match");
			}
			
			
		}
		return extractedInformation;
	}
}
