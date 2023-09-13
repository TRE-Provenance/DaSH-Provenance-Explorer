package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import org.apache.jena.rdf.model.Model;

import Utils.SPARQLUtils;
import Utils.ValidationUtils;
import semantic.parser.Constants;
import semantic.parser.Entity;
import semantic.parser.Variable;

public class CheckForSensitiveVariablesInFile implements ValidationRuleInterface {
	
	public static ArrayList <HashMap<String, String>> checkFile (String fileIRI, Model model) {
		
		ArrayList <HashMap<String, String>> list = new ArrayList <HashMap<String, String>> ();
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?variable ?variableL  WHERE {<"+fileIRI+"> schema:exifData ?item. ?item a shp:SelectedVariables; prov:hadMember ?variable. ?variable a shp:SensitiveVariable; rdfs:label ?variableL. }";    	
    		System.out.println (query);
		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
		
		return list; 
	}

	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Sensitive Variables Found";
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
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?variable ?variableL  WHERE {<"+fileIRI+"> schema:exifData ?item. ?item a shp:SelectedVariables; prov:hadMember ?variable. ?variable a shp:SensitiveVariable; rdfs:label ?variableL. }";    	
    		System.out.println (query);
		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
	
		/*
		ArrayList <String> violations = new ArrayList <String> ();
		
		for (int i = 0; i < list.size();i++) {
			
			violations.add(list.get(i).get("variableL"));
		}
		*/
		
ArrayList <Variable> violations = new ArrayList <Variable> ();
	
		
		
		for (int i = 0; i < list.size();i++) {
			
			Variable variable = new Variable (list.get(i).get("variable")); 
			variable.setEntityL(list.get(i).get("variableL"));
			
			violations.add(variable);
		}

		return violations; 
	}
	

	@Override
	public JPanel getSimpleResult(String[] args, Model model) {
		ArrayList<Variable> variablesList =  (ArrayList<Variable>) new CheckForSensitiveVariablesInFile ().getViolations(args, model);
		ArrayList <Entity> entities= new ArrayList <Entity> (); 
				
				if (variablesList.size()>0) {
			    	
			    	
			    	for (int i =0; i <variablesList.size();i++ ) {
			    		entities.add(variablesList.get(i));
			    	}
			    	
			    }
			    
			    
				
			    return ValidationUtils.entityResult(getName() + " ("+variablesList.size()+")", entities);
	}
}
