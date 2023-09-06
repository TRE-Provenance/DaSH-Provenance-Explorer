package semantic.parser;

public class Database extends Entity {

	private String databaseL, description;
	
	
	public Database (String URI) {
		setURI(URI);
		
	}

	public String getDatabaseL() {
		return databaseL;
	}

	public void setDatabaseL(String databaseL) {
		this.databaseL = databaseL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
