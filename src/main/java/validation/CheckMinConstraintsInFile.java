package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.apache.jena.rdf.model.Model;

import Utils.SPARQLUtils;
import Utils.ValidationUtils;
import semantic.parser.Constants;
import semantic.parser.Dataset;
import semantic.parser.Entity;

public class CheckMinConstraintsInFile implements ValidationRuleInterface{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Variables Violating Min Constraint";
	}

	@Override
	public List getTargetTypes() {
		ArrayList <String> list = new ArrayList <String> ();			
		list.add("http://schema.org/MediaObject");
		return list;
	}

	@Override
	public List getViolations(String[] args, Model model) {
        String fileIRI = args [0];
		
		ArrayList <HashMap<String, String>> list = new ArrayList <HashMap<String, String>> ();
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?variableL ?constraintMin  WHERE {<"+fileIRI+">  prov:wasDerivedFrom* ?database; schema:exifData ?item. ?database a shp:Database. ?linkagePlan a shp:DataLinkagePlan; schema:exifData ?dataSource. ?dataSource shp:database ?database; shp:constraint ?constriant. ?constriant shp:targetFeature ?variable; shp:minValue ?constraintMin.  ?item shp:targetFeature ?variable; shp:minValue ?actualMin. ?variable rdfs:label ?variableL FILTER (?constraintMin > ?actualMin)}";

		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
		ArrayList <String> violations = new ArrayList <String> ();
		
		for (int i = 0; i < list.size();i++) {
			
			violations.add(list.get(i).get("variableL")+" (min"+list.get(i).get("constraintMin")+")");
		}

		return violations; 
	}
	

	@Override
	public JPanel getSimpleResult(String[] args, Model model) {
		String variables = "";
		
		
		
		ArrayList<String> variablesList =  (ArrayList<String>) new CheckMinConstraintsInFile ().getViolations(args, model);
	    if (variablesList.size()>0) {
	    	
	    	
	    	for (int i =0; i <variablesList.size();i++ ) {
	    		variables = variables + variablesList.get(i);
	    		
	    		if (i+1!=variablesList.size()) {
	    			variables = variables +",";
	        	}
	    	}
	    	
	    }
		
	    return ValidationUtils.simpleResult(getName(), variables);
	}
}
