package semantic.parser;

public class Database extends Entity {

	private String  description, abbreviation, version, dataCustodian, contact, lastKnownUpdate,mostRecentRecordDate, oldestRecordDate,contextualInformationLink ;
	
	
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDataCustodian() {
		return dataCustodian;
	}

	public void setDataCustodian(String dataCustodian) {
		this.dataCustodian = dataCustodian;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getLastKnownUpdate() {
		return lastKnownUpdate;
	}

	public void setLastKnownUpdate(String lastKnownUpdate) {
		this.lastKnownUpdate = lastKnownUpdate;
	}

	public String getMostRecentRecordDate() {
		return mostRecentRecordDate;
	}

	public void setMostRecentRecordDate(String mostRecentRecordDate) {
		this.mostRecentRecordDate = mostRecentRecordDate;
	}

	public String getOldestRecordDate() {
		return oldestRecordDate;
	}

	public void setOldestRecordDate(String oldestRecordDate) {
		this.oldestRecordDate = oldestRecordDate;
	}

	public String getContextualInformationLink() {
		return contextualInformationLink;
	}

	public void setContextualInformationLink(String contextualInformationLink) {
		this.contextualInformationLink = contextualInformationLink;
	}

	public Database (String URI) {
		setURI(URI);
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
