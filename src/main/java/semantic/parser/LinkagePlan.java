package semantic.parser;

public class LinkagePlan extends Entity{
	
	private String planL, description;
	
	public LinkagePlan (String URI) {
		setURI(URI);
		
	}

	public String getDatasetL() {
		return planL;
	}

	public void setDatasetL(String datasetL) {
		this.planL = datasetL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	


}
