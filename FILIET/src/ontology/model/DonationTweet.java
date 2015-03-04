package ontology.model;

public class DonationTweet extends Tweet {
	private String victimName;
	private String resourceName;
	private String resourceDetails;
	
	public DonationTweet() {
		super();
	}

	public String getVictimName() {
		return victimName;
	}

	public void setVictimName(String victimName) {
		this.victimName = victimName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceDetails() {
		return resourceDetails;
	}

	public void setResourceDetails(String resourceDetails) {
		this.resourceDetails = resourceDetails;
	}

}
