package semantic.parser;

public class Dataset extends Entity {

	private String datasetL, description;
	private String path = "not available";
	
	public Dataset (String URI) {
		setURI(URI);
		
	}

	public String getDatasetL() {
		return datasetL;
	}

	public void setDatasetL(String datasetL) {
		this.datasetL = datasetL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPath(String path) {
		this.path = path;
		
	}
	
	public String getPath() {
		return path;
		
	}

	
}
