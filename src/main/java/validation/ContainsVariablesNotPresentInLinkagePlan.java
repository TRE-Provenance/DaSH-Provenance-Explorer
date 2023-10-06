
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

public class ContainsVariablesNotPresentInLinkagePlan implements ValidationRuleInterface{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Dataset Contains Variables Not Present In Linkage Plan";
	}

	@Override
	public List getTargetTypes() {
		ArrayList <String> list = new ArrayList <String> ();			
		list.add("https://w3id.org/shp#DataLinkagePlan");
		return list;
	}

	@Override
	public List getViolations(String[] args, Model model) {
        String fileIRI = args [0];
		
		ArrayList <HashMap<String, String>> list = new ArrayList <HashMap<String, String>> ();
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?variable ?variableL  WHERE {<"+fileIRI+"> schema:exifData ?item. ?item a shp:SelectedVariables; prov:hadMember ?variable.  ?variable rdfs:label ?variableL NOT EXISTS { ?linkagePlan a shp:DataLinkagePlan; schema:exifData ?dataSource. ?dataSource shp:requestedVariables ?varCollection. ?varCollection prov:hadMember ?variable. }  }";

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
		
		
		
		ArrayList<Database> variablesList =  (ArrayList<Database>) new ContainsVariablesNotPresentInLinkagePlan ().getViolations(args, model);
	    
		ArrayList <Entity> entities= new ArrayList <Entity> (); 
		
		if (variablesList.size()>0) {
	    	
	    	
	    	for (int i =0; i <variablesList.size();i++ ) {
	    		entities.add(variablesList.get(i));
	    	}
	    	
	    }
	    
	    
		
	    return ValidationUtils.entityResult(getName()+ " ("+variablesList.size()+")", entities);
	}
}



