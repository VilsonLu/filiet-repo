package patternextraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.util.featuregen.StringPattern;
import model.ExtractedInformation;
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
						while (input.charAt(head) != ']') {
							head++;
						}
						trail = head + 1;
						while (head < input.length() - 1) {
							head++;
							if (input.charAt(head) == ' ') {
								break;
							}

						}
						String extractedAs = input.substring(trail, head + 1);
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

	public List<ExtractedInformation> match(Sentence sentence) {
		List<Token> tokens = sentence.getSentence();
		int tokenSize = tokens.size();
		// for successfully matched rules
		List<ExtractedInformation> extractedInformation = new ArrayList<ExtractedInformation>();

		List<ExtractedInformation> temp = null;
		ExtractedInformation extract = null;
		for (Grammar rp : extractionRules) {
			rp.printRules();
			List<Rule> rules = rp.getRules();
			temp = new ArrayList<ExtractedInformation>();
			boolean match = false;
			int tokenIndex = 0;
			int ruleIndex = 0;
			if (rp.getRuleCount() <= tokenSize) {

				// can match
				while (tokenIndex < tokenSize) {
					if (ruleIndex < rules.size()) {
						match = rules.get(ruleIndex).matchToken(
								tokens.get(tokenIndex));
						if (match) {
							extract = new ExtractedInformation();
							extract.setInformationType(rules.get(ruleIndex)
									.getAsExtraction());
							extract.setValue(tokens.get(tokenIndex));
							temp.add(extract);
							ruleIndex++;
						} else {
							temp = new ArrayList<ExtractedInformation>();
							ruleIndex = 0;
							match = false;
						}
					} else {
						if(match){
							System.out.println("Rule matches");
							for(ExtractedInformation e: temp){
								System.out.println(e.getInformationType()+": " + e.getValue().getWord());
							}
						} 
						break;
					}
					tokenIndex++;
				}
				
				if(!match){
					System.out.println("The rule did not match");
				}
				
			} else {
				System.out.println("The rule did not match");
			}

		}
		return extractedInformation;
	}
}
