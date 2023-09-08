package semantic.parser;

import java.util.ArrayList;

import guiInterface.ResultObject;

public class Activity implements ResultObject {

	private String activityType, description,  URI, activityEndDate;
	
	

	private boolean failedValidation;
	
	private ArrayList inputs, outputs, agents;



	private String activityL; 
	
	
	public Activity (String URI) {
		
		setURI (URI);
		setOutputs(new ArrayList ());
		setInputs(new ArrayList ());
		setAgents(new ArrayList <Agent>());
	}
	
	@Override
	public String getURI() {
		return URI;
	}


	public void setURI(String uRI) {
		URI = uRI;
	}
	
	public ArrayList <Entity> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList inputs) {
		this.inputs = inputs;
	}

	public ArrayList <Entity>  getOutputs() {
		return outputs;
	}

	public void setOutputs(ArrayList outputs) {
		this.outputs = outputs;
	}

	public boolean isFailedValidation() {
		return failedValidation;
	}

	public void setFailedValidation(boolean failedValidation) {
		this.failedValidation = failedValidation;
	}

	public String getActivityType() {
		return activityType;
	}
	
	public void setActivityType(String activityType) {
		this.activityType=activityType;
	}

	public String getActivityL() {
		return activityL ;
	}
	
	public void setActivityL(String activityL) {
		this.activityL = activityL;
	}

	public String getDescription() {
		return description;
	}
	
	public void setActivityEndDate(String activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	public String setActivityEndDate() {
		return activityEndDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList getAgents() {
		return agents;
	}

	public void setAgents(ArrayList agents) {
		this.agents = agents;
	} 
	

}
