package Tweet;

public class CasualtiesAndDamageTweet extends Tweet {
	private String victimName;
	private String objectName;
	private String objectDetails;
	
	public CasualtiesAndDamageTweet() {
		super();
	}

	public String getVictimName() {
		return victimName;
	}

	public void setVictimName(String victimName) {
		this.victimName = victimName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectDetails() {
		return objectDetails;
	}

	public void setObjectDetails(String objectDetails) {
		this.objectDetails = objectDetails;
	}
	
	
}
