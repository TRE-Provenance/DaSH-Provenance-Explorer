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
		
		ArrayList<ValidationRuleInterface> signoffChecks = new ArrayList <ValidationRuleInterface> ();
		
		signoffChecks.add(new CheckInpusOutputsRowCountMatches());
		
		settings.put("https://w3id.org/shp#SignOff", signoffChecks);
		
        ArrayList<ValidationRuleInterface> datasetChecks = new ArrayList <ValidationRuleInterface> ();
        
        datasetChecks.add(new CheckMinConstraintsInFile());
        datasetChecks.add(new CheckMaxConstraintsInFile());
		
		
		
		settings.put("https://w3id.org/shp#DataSet",datasetChecks);
		
		
	}

}
