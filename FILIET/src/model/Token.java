package model;

public class Token {
	private String Word;
	private String POSTag;
	private String NERTag;
	private Boolean IsDisasterWord;
	
	/**
	 * @return the word
	 */
	public String getWord() {
		return Word;
	}
	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		Word = word;
	}
	/**
	 * @return the pOSTag
	 */
	public String getPOSTag() {
		return POSTag;
	}
	/**
	 * @param pOSTag the pOSTag to set
	 */
	public void setPOSTag(String pOSTag) {
		POSTag = pOSTag;
	}
	/**
	 * @return the nERTag
	 */
	public String getNERTag() {
		return NERTag;
	}
	/**
	 * @param nERTag the nERTag to set
	 */
	public void setNERTag(String nERTag) {
		NERTag = nERTag;
	}
	/**
	 * @return the isDisasterWord
	 */
	public Boolean getIsDisasterWord() {
		if(IsDisasterWord == null){
			return false;
		}
		return IsDisasterWord;
	}
	/**
	 * @param isDisasterWord the isDisasterWord to set
	 */
	public void setIsDisasterWord(Boolean isDisasterWord) {
		IsDisasterWord = isDisasterWord;
	}	
	
	public void PrintToken(){
		String word = this.Word;
		
		if(this.POSTag != null){
			word = word + "_" + this.POSTag;
		}
		
		if(this.NERTag != null){
			if(this.NERTag.equalsIgnoreCase("location")){
				word = "<location: " + this.Word + "/>";
			}
		}
		
		if(this.IsDisasterWord != null){
			if(this.IsDisasterWord){
				word = "<disaster: " + this.Word + "/>";
			}
		}
		
		System.out.print("\""+word+"\",");
	}
	
	public void TokenXML(){
		
	}
	
}
