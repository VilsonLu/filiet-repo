package ruleinduction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opennlp.tools.util.featuregen.StringPattern;
import support.model.ExtractedInformation;
import support.model.Grammar;
import support.model.PostExtractedInformation;
import support.model.Rule;
import support.model.Sentence;
import support.model.Token;

public class RuleInductor {
	private Map<String, List<Grammar>> categorizeRule;
	// Path to the extraction-rules file
	private String path;

	public RuleInductor(String path) throws IOException {
		categorizeRule = new HashMap<String, List<Grammar>>();
		this.path = path;
		loadRules();
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
		String category = null;
		List<Grammar> rules = null;

		boolean flag = false;
		while ((line = br.readLine()) != null) {
			if (line.contains("<Category>:")) {
				category = line.split(" ")[1];
				rules = new ArrayList<Grammar>();
			} else if (line.equals("<end>")) {
				categorizeRule.put(category, rules);
			} else {
				rules.add(extractPatternRule(line));

			}

		}

	}

	public List<PostExtractedInformation> match(Sentence sentence) {
		List<Token> tokens = sentence.getSentence();
		int tokenSize = tokens.size();

		// temporary
		// String category = sentence.getCategory();
		//String category = sentence.getCategory();
		String category = sentence.getTweets().getCategory();
		List<Grammar> extractionRules = categorizeRule.get(category);

		// for successfully matched rules
		List<PostExtractedInformation> extractedInformation = new ArrayList<PostExtractedInformation>();

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
							ruleIndex = 0;
							match = false;
							temp = new ArrayList<ExtractedInformation>();
						}
					}

					if (ruleIndex >= rules.size()) {
						if (match) {
							System.out.println("Rule Match");
							
							PostExtractedInformation extractInfo = new PostExtractedInformation();
							extractInfo.setCompiledInformation(temp);
							extractInfo.setAppliedRules(rp);
							extractedInformation.add(extractInfo);
							extract = null;
							ruleIndex = 0;
							temp = null;
							temp = new ArrayList<ExtractedInformation>();
						}
					}
					tokenIndex++;
				}

			}

		}
		

		return extractedInformation;
	}
}
