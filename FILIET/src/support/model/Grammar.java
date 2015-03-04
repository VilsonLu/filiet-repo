package support.model;

import java.util.ArrayList;
import java.util.List;

public class Grammar {
	public List<Rule> rules;
	
	public Grammar() {
		rules = new ArrayList<Rule>();
	}
	
	public void addRule(Rule rule){
		rules.add(rule);
	}
	
	
	/**
	 * @return the rules
	 */
	public List<Rule> getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public int getRuleCount(){
		return rules.size();
	}

	public void printRules(){
		for(Rule r: rules){
			System.out.println("Left: " + r.getType());
			System.out.println("Right: " + r.getValue());
			System.out.println("Extraction: " + r.getAsExtraction());
			if(r.getAsExtraction() != null){
				System.out.println(r.getAsExtraction());
			}
			System.out.println();
		}
	}
}
