package support.model;

import java.util.List;

public class PostExtractedInformation {
	private String type;
	private List<ExtractedInformation> compiledInformation;
	private Grammar appliedRules;
	
	
	/**
	 * @return the appliedRules
	 */
	public Grammar getAppliedRules() {
		return appliedRules;
	}
	/**
	 * @param appliedRules the appliedRules to set
	 */
	public void setAppliedRules(Grammar appliedRules) {
		this.appliedRules = appliedRules;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the compiledInformation
	 */
	public List<ExtractedInformation> getCompiledInformation() {
		return compiledInformation;
	}
	/**
	 * @param compiledInformation the compiledInformation to set
	 */
	public void setCompiledInformation(
			List<ExtractedInformation> compiledInformation) {
		this.compiledInformation = compiledInformation;
	}
	
	public void printText(){
		for(ExtractedInformation e: compiledInformation){
			System.out.print(e.getInformationType()+"_"+e.getValue().getWord() +" ");
		}
	}
	
	
}
