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
import semantic.parser.Variable;

public class RequestedVariablesNeverUsed implements ValidationRuleInterface{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Requested Variables Not Found In Any File";
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
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?variable ?variableL ?databaseL WHERE {?database rdfs:label ?databaseL.  ?database a shp:Database. ?linkagePlan a shp:DataLinkagePlan; schema:exifData ?dataSource. ?dataSource shp:database ?database;shp:requestedVariables ?varCollection. ?varCollection prov:hadMember ?variable. ?variable rdfs:label ?variableL NOT EXISTS { ?file a shp:DataSet; schema:exifData ?selectedVarCollection. ?selectedVarCollection a shp:SelectedVariables; prov:hadMember ?variable. }  }";

		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
		ArrayList <Variable> violations = new ArrayList <Variable> ();
		
		
		
		for (int i = 0; i < list.size();i++) {
			
			Variable variable = new Variable (list.get(i).get("variable")); 
			variable.setEntityL(list.get(i).get("variableL") + " (source db:"+list.get(i).get("databaseL")+")");
			
			violations.add(variable);
		}

		return violations; 
	}
	

	@Override
	public JPanel getSimpleResult(String[] args, Model model) {
		String variables = "";
		
		
		
		ArrayList<Database> variablesList =  (ArrayList<Database>) new RequestedVariablesNeverUsed ().getViolations(args, model);
	    
		ArrayList <Entity> entities= new ArrayList <Entity> (); 
		
		if (variablesList.size()>0) {
	    	
	    	
	    	for (int i =0; i <variablesList.size();i++ ) {
	    		entities.add(variablesList.get(i));
	    	}
	    	
	    }
	    
	    
		
	    return ValidationUtils.entityResult(getName()+ " ("+variablesList.size()+")", entities);
	}
}


