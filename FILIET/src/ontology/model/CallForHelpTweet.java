package ontology.model;

public class CallForHelpTweet extends Tweet {
	private String victimName;

	public CallForHelpTweet() {
		super();
	}
	
	public String getVictimName() {
		return victimName;
	}

	public void setVictimName(String victimName) {
		this.victimName = victimName;
	}
}
