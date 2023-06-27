package semantic.parser;

import java.util.ArrayList;

import guiInterface.ResultObject;

public class Activity implements ResultObject {

	private String activityType, description,  URI;
	
	

	private boolean failedValidation;
	
	private ArrayList inputs, outputs, agents; 
	
	
	public Activity (String URI) {
		
		setURI (URI);
		setOutputs(new ArrayList <Dataset>());
		setInputs(new ArrayList <Dataset>());
		setAgents(new ArrayList <Agent>());
	}
	
	@Override
	public String getURI() {
		return URI;
	}


	public void setURI(String uRI) {
		URI = uRI;
	}

	public ArrayList getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList inputs) {
		this.inputs = inputs;
	}

	public ArrayList getOutputs() {
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

	public void setActivityL(String activityType) {
		this.activityType = activityType;
	}

	public String getDescription() {
		return description;
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
