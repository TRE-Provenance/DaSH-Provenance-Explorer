package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import guiInterface.ResultObject;

public class SPARQLUtils {
	
	public static  ArrayList<HashMap<String, String>> executeSparqlQuery(Model model, String sparqlQuery) {
    	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    	 Query query = QueryFactory.create(sparqlQuery);
         try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
             ResultSet results = qexec.execSelect();
            // ResultSetFormatter.out(System.out, results, query);
        
         while (results.hasNext()) {
 			QuerySolution sol = results.next();
 			Iterator<String> it = sol.varNames();
 			HashMap<String, String> map = new HashMap<String, String>();
 			while (it.hasNext()) {
 				String varName = it.next();
 				System.out.println ("->>" + varName);
 				map.put(varName, sol.get(varName).toString());
 			}
 			list.add(map);
 		}
 		System.out.println("Result");
 		System.out.println(list);
         }
 		return list;
    }

	public  static ResultObject getObjectByURI(ArrayList<ResultObject> list, String targetURI) {
        for (ResultObject obj : list) {
            if (obj.getURI().equals(targetURI)) {
                return obj;
            }
        }
        return null; // Object not found
    }
	
	 public static  Model loadJsonLdFromDiskFile(String filePath) {
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
	        
				
					catch ( org.apache.jena.riot.RiotException ex) {
						System.out.println ("File is empty or not in JSON-LD format"); 
						System.out.println (ex.getStackTrace());
						System.out.println (ex.getMessage());
						ex.printStackTrace(System.out);
				}
	        
	        
	        return model;
	    }
	    
}
