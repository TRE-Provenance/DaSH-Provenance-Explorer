package semantic.parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.vocabulary.RDF;

import Utils.SPARQLUtils;

public class CommentsJsonLdProcessor {

Model model;
	
 private String commentsPath = Constants.PROVENANCE_DIRECTORY + "comments.jsonld";
	
    public CommentsJsonLdProcessor  () {
        // find comments file    

        model = SPARQLUtils.loadJsonLdFromDiskFile(commentsPath);
        
    }
    
    public Model getModel () {
    	return model;
    }
    
    public void saveModel () {
    	
    	
     // Save the model in JSON-LD format to a file
        try (OutputStream outputStream = new FileOutputStream(commentsPath)) {
            RDFDataMgr.write(outputStream, model, RDFFormat.JSONLD);
            System.out.println("Model saved to " + commentsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }
    
    
    public ArrayList <Comment> getComments (String targetIRI) {
    	
    	ArrayList <Comment> comments = new ArrayList <Comment> ();
    	
    	String sparqlQuery = "Select ?text ?author ?time Where {?comment a <http://www.w3.org/ns/oa#Annotation>; <http://www.w3.org/ns/oa#hasTarget> <"+targetIRI+">; <http://www.w3.org/ns/oa#hasBody> ?body. ?body <http://purl.org/dc/terms/created> ?time; <http://www.w3.org/ns/oa#bodyValue> ?text; <http://purl.org/dc/terms/creator> ?author. } Order by ASC (?time) ";
    	
    	ArrayList<HashMap<String, String>>  result = SPARQLUtils.executeSparqlQuery(model, sparqlQuery);
    	
    	System.out.println ("Query" + sparqlQuery);
    	System.out.println ("Results comments" + result.size());
    	
    	for (int i=0;i<result.size();i++) {
    		Comment comment = new Comment (result.get(i).get("author"), result.get(i).get("time"), result.get(i).get("text"));
    		comments.add(comment);
    	}
    	
    	return comments; 
    }
    
    
    public void saveCommentToModel (String targetId, Comment comment) {
    	// Add some triples to the model (replace with your own data)
    	
    	String commentIRI = "dash:comment/"+UUID.randomUUID();

        model.createResource(commentIRI+"/body")
        .addProperty(model.createProperty("http://www.w3.org/ns/oa#bodyValue"), comment.getText())
        .addProperty(model.createProperty("http://purl.org/dc/terms/created"), comment.getFormattedTimestamp())
        .addProperty(model.createProperty("http://purl.org/dc/terms/creator"), comment.getAuthor());
        
        model.createResource(commentIRI)
             .addProperty(RDF.type,model.createResource("http://www.w3.org/ns/oa#Annotation"))
             .addProperty(model.createProperty("http://www.w3.org/ns/oa#hasBody"), model.createResource(commentIRI+"/body"))
             .addProperty(model.createProperty("http://www.w3.org/ns/oa#hasTarget"), model.createResource(targetId));
        
        saveModel ();

    }
    
    
    
    
    
	
}
