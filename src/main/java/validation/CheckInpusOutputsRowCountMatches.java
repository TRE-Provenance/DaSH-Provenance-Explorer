package validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.jena.rdf.model.Model;

import Utils.SPARQLUtils;
import Utils.ValidationUtils;
import gui.MainGuiFrame;
import semantic.parser.Constants;
import semantic.parser.Dataset;
import semantic.parser.Entity;
import semantic.parser.JsonLdProcessor;

public class CheckInpusOutputsRowCountMatches implements ValidationRuleInterface{
	
	
@Override
public  List<Entity> getViolations (String [] args, Model model) {
	
	   String activityIRI = args [0];
		
		ArrayList <HashMap<String, String>> list = new ArrayList <HashMap<String, String>> ();
		
		String query = Constants.PREFIXES + " SELECT DISTINCT ?input ?output ?datasetL WHERE {<"+activityIRI+"> a <http://schema.org/CreateAction>;  <http://schema.org/object> ?input;<http://schema.org/result> ?output.  ?input a shp:DataSet; rdfs:label ?datasetL. ?output a shp:DataSet; rdfs:label ?datasetL. ?input schema:exifData [ a shp:EntityCharacteristic; shp:targetFile ?input; shp:rowCount ?rowCountInput]. ?output schema:exifData [ a shp:EntityCharacteristic; shp:targetFile ?output; shp:rowCount ?rowCountOutput]. FILTER (?rowCountOutput != ?rowCountInput) }";    	
		
		// Execute SPARQL query
		list = SPARQLUtils.executeSparqlQuery(model, query);    	
		
		ArrayList <Entity> violations = new ArrayList <Entity> ();
		
		for (int i = 0; i < list.size();i++) {
			
			Dataset d = new Dataset (list.get(i).get("output"));
			
			d.setEntityL(list.get(i).get("datasetL"));
			
			violations.add(d);
		}

		return violations; 
	}



@Override
public String getName() {
	// TODO Auto-generated method stub
	return "Number of Rows input/output";
}

@Override
public List<String> getTargetTypes() {
	ArrayList <String> list = new ArrayList <String> ();			
			list.add("http://schema.org/CreateAction");
	return list ;
}

@Override
public JPanel getSimpleResult (String [] args, Model model)  {
	
	String resultMatchingRows = "";
	
	
	
	ArrayList<Entity> rowCountDoesntMatch =  (ArrayList<Entity>) new CheckInpusOutputsRowCountMatches ().getViolations(args, model);
    if (rowCountDoesntMatch.size()>0) {
    	
    	for (int i =0; i <rowCountDoesntMatch.size();i++ ) {
    		resultMatchingRows = resultMatchingRows + rowCountDoesntMatch.get(i).getEntityL();
    		
    		if (i+1!=rowCountDoesntMatch.size()) {
    			resultMatchingRows = resultMatchingRows +",";
        	}
    	}
    	
    }
	
    return ValidationUtils.simpleResult(getName(), resultMatchingRows);
}







}
