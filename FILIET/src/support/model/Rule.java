package support.model;

public class Rule {
	private String type;
	private String value;
	private String asExtraction;
	
	
	private String NUMBERS = "0123456789";
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
	
	// Supporting methods
	private boolean isNumeric(String str)
	{
		String num = str.replaceAll("[^0-9]", "");
	    for (char c : num.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	public boolean matchToken(Token token){
		if(type.equalsIgnoreCase("string")){
			if(value.equals("ANY")){
				return true;
			} else {
				return value.equalsIgnoreCase(token.getWord());
			}
		} 
		
		if(type.equalsIgnoreCase("number")) {
			if(value.equals("ANY")){
				if(isNumeric(token.getWord())){
					return true;
				} 
			} else {
				if(value.equalsIgnoreCase(token.getWord())){
					return true;
				}
			}
			
			return false;
		}
		
		if (type.equalsIgnoreCase("ner")){
			return value.equalsIgnoreCase(token.getNERTag());
		} 
		
		if (type.equalsIgnoreCase("pos")){
			return value.equalsIgnoreCase(token.getPOSTag());
		}
		
		return false;
		
	}
	
}
