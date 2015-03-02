package model;

import java.util.ArrayList;
import java.util.List;

public class ExtractedInformation {

	
	private String informationType;
	private Token value;
	
	
	/**
	 * @return the informationType
	 */
	public String getInformationType() {
		return informationType;
	}
	/**
	 * @param informationType the informationType to set
	 */
	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}
	/**
	 * @return the value
	 */
	public Token getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(Token value) {
		this.value = value;
	}
	
	public void setTemplate(String informationType, Token value, String category){
		if(category.equalsIgnoreCase("ca")){
			if(informationType.equals("MONTH")){
				
			} else if(informationType.equals("DATE")){
				
			} else if(informationType.equals("ADVICE")){
				
			} else if(informationType.equals("LOCATION")){
				
			}
		} else if(category.equalsIgnoreCase("d")){
			// code later
		} else if(category.equalsIgnoreCase("ch")){
			// code later
		} else if(category.equalsIgnoreCase("cd")){
			// code later
		}
	}
	
}
