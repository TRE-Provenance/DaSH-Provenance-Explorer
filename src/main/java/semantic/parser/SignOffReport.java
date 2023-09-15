package semantic.parser;


public class SignOffReport extends Entity{

	private String result;
	
	public SignOffReport (String uri) {
		this.setURI(uri);
	}
	
	
	public void setResult(String result) {
		this.result = result;
		
	}

	public String getResult() {
		
		return result;
	}

}
