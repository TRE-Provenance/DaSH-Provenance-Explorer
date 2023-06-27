package Utils;

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
}
