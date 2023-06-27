package semantic.parser;

public class Dataset {

	private String datasetL, description, URI;
	
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

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}
}
