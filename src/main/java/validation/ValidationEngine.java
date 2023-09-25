package validation;

import java.util.ArrayList;
import java.util.HashMap;

public class ValidationEngine {
	
	private HashMap <String, ArrayList <ValidationRuleInterface>> settings ;  
	
	public HashMap<String, ArrayList<ValidationRuleInterface>> getSettings() {
		return settings;
	}

	public void setSettings(HashMap<String, ArrayList<ValidationRuleInterface>> settings) {
		this.settings = settings;
	}

	public ValidationEngine () {
		
		//settings need to be pulled from external YAMML file - to do 
		
		settings = new HashMap <String,ArrayList <ValidationRuleInterface>> (); 
		
		
ArrayList<ValidationRuleInterface> linkageChecks = new ArrayList <ValidationRuleInterface> ();
		

linkageChecks.add(new ExpectedChangeOfHash());
		
		settings.put("https://w3id.org/shp#IdLinkage", linkageChecks);
		
		ArrayList<ValidationRuleInterface> signoffChecks = new ArrayList <ValidationRuleInterface> ();
		
		signoffChecks.add(new CheckInpusOutputsRowCountMatches());
		signoffChecks.add(new UnexpectedChangeOfHash());
		
		settings.put("https://w3id.org/shp#SignOff", signoffChecks);
		
		
ArrayList<ValidationRuleInterface> releaseChecks = new ArrayList <ValidationRuleInterface> ();
		
        releaseChecks.add(new CheckInpusOutputsRowCountMatches());
        releaseChecks.add(new UnexpectedChangeOfHash());
		
		settings.put("https://w3id.org/shp#DatasetRelease", releaseChecks);
		
		
        ArrayList<ValidationRuleInterface> datasetChecks = new ArrayList <ValidationRuleInterface> ();
        
        datasetChecks.add(new CheckMinConstraintsInFile());
        datasetChecks.add(new CheckMaxConstraintsInFile());
        datasetChecks.add(new CheckForSensitiveVariablesInFile());
      
		
		
		settings.put("https://w3id.org/shp#DataSet",datasetChecks);
		
		
		ArrayList<ValidationRuleInterface> linkagePlanChecks = new ArrayList <ValidationRuleInterface> ();
		
		linkagePlanChecks.add(new AllDatasourcesUsed());
		linkagePlanChecks.add(new AllDataSourcesUsedInReleased());
		linkagePlanChecks.add(new RequestedVariablesNeverUsed());
		linkagePlanChecks.add(new RequestedVariablesNeverUsedInReleasedFiles());
		
		
		
		settings.put("https://w3id.org/shp#DataLinkagePlan",linkagePlanChecks);
		
		
		
		
		
	}

}
