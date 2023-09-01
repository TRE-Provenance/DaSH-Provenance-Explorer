package validation;

import java.util.List;

import org.apache.jena.rdf.model.Model;

import semantic.parser.Entity;

public interface ValidationRuleInterface<E> {

	//name of the validation rule 
	public String getName ();
	
	//returns the types of target object to which such rule can be applied (e.g., prov:activity, shp:Dataset, etc.
	public List <String> getTargetTypes ();
		
	public List <E> getViolations (String [] args, Model model);
	
	
}
