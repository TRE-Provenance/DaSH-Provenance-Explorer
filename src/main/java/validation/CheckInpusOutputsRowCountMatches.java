package validation;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import org.apache.jena.rdf.model.Model;

import Utils.SPARQLUtils;
import gui.MainGuiFrame;
import semantic.parser.Constants;
import semantic.parser.JsonLdProcessor;

public class CheckInpusOutputsRowCountMatches {
	
public static ArrayList <HashMap<String, String>> checkActivity (String activityIRI, Model model) {
		
		ArrayList <HashMap<String, String>> list = new ArrayList <HashMap<String, String>> ();
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?input ?output ?datasetL WHERE {<"+activityIRI+"> a <http://schema.org/CreateAction>;  <http://schema.org/object> ?input;<http://schema.org/result> ?output.  ?input rdfs:label ?datasetL. ?output rdfs:label ?datasetL. ?input schema:exifData [ a shp:EntityCharacteristic; shp:targetFile ?input; shp:rowCount ?rowCountInput]. ?output schema:exifData [ a shp:EntityCharacteristic; shp:targetFile ?output; shp:rowCount ?rowCountOutput]. FILTER (?rowCountOutput != ?rowCountInput) }";    	
		
		
		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
		
		return list; 
	}

public static void main(String[] args) {
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
	checkActivity ("https://www.example.com/DatasetRelease", dataProcessor.getModel());
}

}
