package semantic.parser;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import Utils.SPARQLUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class JsonLdProcessor {

	Model model;
	
	
    public JsonLdProcessor () {
        // Load JSON-LD file from Resources directory
        model = loadJsonLdFromDiskFile(Constants.PROVENANCE_FILE);
        model.add(loadJsonLdFromFile("variables.jsonld"));
    }
    
    public Model getModel () {
    	return model;
    }
    
    public ArrayList<HashMap<String, String>> getActivityData () {
    	
    	// Execute SPARQL query
    	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, Constants.PREFIXES + " SELECT DISTINCT ?activity ?activityL   WHERE {?activity a <http://schema.org/CreateAction>; rdfs:label ?activityL. }");    	
    	
		return list;
    }
    
 public ArrayList<HashMap<String, String>> getActivityInputs (String activityURI) {
    	
    	// Execute SPARQL query
    	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, Constants.PREFIXES + " SELECT DISTINCT  ?activityL ?input  ?inputL   WHERE {<"+activityURI+"> a <http://schema.org/CreateAction>; rdfs:label ?activityL;  <http://schema.org/object> ?input.  ?input rdfs:label ?inputL.  }");    	   	
		return list;
    }
 
 public ArrayList<HashMap<String, String>> getActivityOutputs (String activityURI) {
 	
 	// Execute SPARQL query
 	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, Constants.PREFIXES + " SELECT DISTINCT  ?activityL ?output  ?outputL   WHERE {<"+activityURI+"> a <http://schema.org/CreateAction>; rdfs:label ?activityL;  <http://schema.org/result> ?output.  ?output rdfs:label ?outputL.  }");    	   	
		return list;
 }
 
 public ArrayList<HashMap<String, String>> getActivityAgents (String activityURI) {
	 	
	 	// Execute SPARQL query
	 	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, Constants.PREFIXES + " SELECT DISTINCT  ?activityL ?agent     WHERE {<"+activityURI+"> a <http://schema.org/CreateAction>; rdfs:label ?activityL;  <http://schema.org/agent> ?agent.  }");    	   	
			return list;
	 }
    
    
public ArrayList<HashMap<String, String>> getVariablesInFile (String fileIRI) {
    	String query = Constants.PREFIXES + " SELECT DISTINCT ?variable ?variableL  WHERE {<"+fileIRI+"> schema:exifData ?item. ?item a shp:SelectedVariables; prov:hadMember ?variable. ?variable rdfs:label ?variableL. }";    	
    	
    	// Execute SPARQL query
    	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, query);    	
    	
    	System.out.println (query);
    	
		return list;
    }

public ArrayList<HashMap<String, String>> getVariablesInPlan () {
	String query = Constants.PREFIXES + " SELECT DISTINCT ?variable ?variableL ?sourceL WHERE {?plan a shp:DataLinkagePlan; schema:exifData ?item. ?item a shp:LinkagePlanDataSource; rdfs:label ?sourceL. ?item shp:requestedVariables ?collection. ?collection a shp:RequestedVariables; prov:hadMember ?variable. ?variable rdfs:label ?variableL. }";    	
	//String query = Constants.PREFIXES + " SELECT DISTINCT ?plan  {?plan a shp:DataLinkagePlan;schema:exifData ?item.?item a shp:LinkagePlanDataSource; rdfs:label ?sourceL.?item shp:requestedVariables ?collection.?collection a shp:RequestedVariables; prov:hadMember ?variable.}";
	// Execute SPARQL query
	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, query);    	
	
	System.out.println (query);
	
	return list;
}
 

public ArrayList<HashMap<String, String>>  getVariableStatsForFile(String fileIRI) {
	String query = Constants.PREFIXES + " SELECT ?minValue ?maxValue ?variableL   WHERE {<"+fileIRI+"> schema:exifData ?item. ?item a shp:EntityCharacteristic; shp:targetFile <"+fileIRI+">; shp:targetFeature/rdfs:label ?variableL; shp:minValue ?minValue; shp:maxValue ?maxValue. }" ;    	
	System.out.println (query);
	// Execute SPARQL query
	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, query);    	
	
	
	
	return list;
}


public ArrayList<HashMap<String, String>>  getSummaryStatsForFile(String fileIRI) {
	String query = Constants.PREFIXES + " SELECT ?description ?rowCount  WHERE {<"+fileIRI+"> schema:description ?description. Optional {<"+fileIRI+"> schema:exifData ?item. ?item a shp:EntityCharacteristic; shp:targetFile <"+fileIRI+">; shp:rowCount ?rowCount. NOT EXISTS {?item shp:targetFeature ?feature.} } }" ;    	
	System.out.println (query);
	// Execute SPARQL query
	ArrayList<HashMap<String, String>>  list = SPARQLUtils.executeSparqlQuery(model, query);    	
	
	
	
	return list;
}

    private static  Model loadJsonLdFromFile(String filename) {
        Model model = ModelFactory.createDefaultModel();

        // Load the JSON-LD file from Resources directory
        InputStream inputStream = JsonLdProcessor.class.getClassLoader().getResourceAsStream(filename);

        if (inputStream == null) {
            System.err.println("File not found: " + filename);
            return model;
        }

        // Read the JSON-LD file into the model
        model.read(inputStream, null, "JSON-LD");

        // Close the input stream
        try {
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
    
  
    private static  Model loadJsonLdFromDiskFile(String filePath) {
        Model model = ModelFactory.createDefaultModel();

        
        	 InputStream inputStream;
			try {
				inputStream = new FileInputStream ( filePath );
				if (inputStream == null) {
		            System.err.println("File not found: " + filePath);
		            return model;
		        }

		        // Read the JSON-LD file into the model
		        model.read(inputStream, null, "JSON-LD");

		        // Close the input stream
		        try {
		            inputStream.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        
        
        return model;
    }
    
   
    
    
	
	
	  public static void main(String[] args) {
	        // Load JSON-LD file from Resources directory
	        Model model;
			
				model = loadJsonLdFromFile("data.jsonld");
			

	        // Execute SPARQL query
	        String queryStr =  "PREFIX shp: <https://w3id.org/shp#> PREFIX schema: <http://schema.org/> PREFIX time: <http://www.w3.org/2006/time#> PREFIX geo: <http://www.opengis.net/ont/geosparql#> PREFIX peco: <https://w3id.org/peco#> PREFIX ecfo: <https://w3id.org/ecfo#>  PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX owl: <http://www.w3.org/2002/07/owl#> PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>  PREFIX prov:<http://www.w3.org/ns/prov#> PREFIX qudt: <http://qudt.org/schema/qudt/> "
	        		+ "SELECT *  WHERE {<https://example.com/released/data.csv> schema:exifData ?item. ?item a shp:EntityCharacteristic; shp:targetFile <https://example.com/released/data.csv>; shp:targetFeature/rdfs:label ?variableL; shp:minValue ?minValue; shp:maxValue ?maxValue.}";
	        		

	        Query query = QueryFactory.create(queryStr);
	        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
	            ResultSet results = qexec.execSelect();
	            ResultSetFormatter.out(System.out, results, query);
	        }
	        model.close();
	    }
}