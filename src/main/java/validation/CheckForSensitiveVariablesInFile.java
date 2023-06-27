package validation;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.rdf.model.Model;

import Utils.SPARQLUtils;
import semantic.parser.Constants;

public class CheckForSensitiveVariablesInFile {
	
	public static ArrayList <HashMap<String, String>> checkFile (String fileIRI, Model model) {
		
		ArrayList <HashMap<String, String>> list = new ArrayList <HashMap<String, String>> ();
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?variable ?variableL  WHERE {<"+fileIRI+"> schema:exifData ?item. ?item a shp:SelectedVariables; prov:hadMember ?variable. ?variable a shp:SensitiveVariable; rdfs:label ?variableL. }";    	
    		System.out.println (query);
		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
		
		return list; 
	}

}
