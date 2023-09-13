package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.apache.jena.rdf.model.Model;

import Utils.SPARQLUtils;
import Utils.ValidationUtils;
import semantic.parser.Constants;
import semantic.parser.Database;
import semantic.parser.Entity;

public class AllDataSourcesUsedInReleased implements ValidationRuleInterface{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Requested Databases Not Influencing Released Files";
	}

	@Override
	public List getTargetTypes() {
		ArrayList <String> list = new ArrayList <String> ();			
		list.add("https://w3id.org/shp#DataLinkagePlan");
		return list;
	}

	@Override
	public List getViolations(String[] args, Model model) {
      //  String fileIRI = args [0];
		
		ArrayList <HashMap<String, String>> list = new ArrayList <HashMap<String, String>> ();
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?database ?databaseL ?file  WHERE {?database rdfs:label ?databaseL.  ?database a shp:Database. ?linkagePlan a shp:DataLinkagePlan; schema:exifData ?dataSource. ?dataSource shp:database ?database. NOT EXISTS { ?file a shp:DataSet;  prov:wasDerivedFrom* ?database. ?activity a shp:DataRelease;schema:result ?file}  }";

		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
		ArrayList <Database> violations = new ArrayList <Database> ();
		
		
		
		for (int i = 0; i < list.size();i++) {
			
			Database database = new Database (list.get(i).get("database")); 
		   	database.setEntityL(list.get(i).get("databaseL"));
			
			violations.add(database);
		}

		return violations; 
	}
	

	@Override
	public JPanel getSimpleResult(String[] args, Model model) {
		String variables = "";
		
		
		
		ArrayList<Database> variablesList =  (ArrayList<Database>) new AllDataSourcesUsedInReleased ().getViolations(args, model);
	    
		ArrayList <Entity> entities= new ArrayList <Entity> (); 
		
		if (variablesList.size()>0) {
	    	
	    	
	    	for (int i =0; i <variablesList.size();i++ ) {
	    		entities.add(variablesList.get(i));
	    	}
	    	
	    }
	    
	    
		
	    return ValidationUtils.entityResult(getName()+ " ("+variablesList.size()+")", entities);
	}
}


