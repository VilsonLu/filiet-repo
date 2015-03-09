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
		String print = "";
		for(Rule r: rules){
			print += "<"+r.getType()+":"+r.getValue()+">";
			if(r.getAsExtraction() != null){
				print+="[as]"+r.getAsExtraction()+" ";
			}
			
			
		}
		System.out.println(print);
	}
}
