package semantic.parser;

public class Agent {
	
private String agentL, description, URI;
	
	public Agent (String URI) {
		setURI(URI);
	}

	public String getAgentL() {
		return agentL;
	}

	public void setAgentL(String datasetL) {
		this.agentL = datasetL;
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
