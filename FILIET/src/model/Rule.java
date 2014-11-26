package model;

public class Rule {
	private String type;
	private String value;
	private String asExtraction;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the asExtraction
	 */
	public String getAsExtraction() {
		return asExtraction;
	}
	/**
	 * @param asExtraction the asExtraction to set
	 */
	public void setAsExtraction(String asExtraction) {
		this.asExtraction = asExtraction;
	}	
	
	public boolean matchToken(Token token){
		
		if(type == "string"){
			if(value.equals("ANY")){
				return true;
			} else {
				return value.equalsIgnoreCase(token.getWord());
			}
		} 
		
		if (type == "NER"){
			return value.equalsIgnoreCase(token.getNERTag());
		} 
		
		if (type == "POS"){
			return value.equalsIgnoreCase(token.getPOSTag());
		}
		
		return false;
		
	}
	
}
