package guiComponentImpl;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import Utils.GuiUtils;
import Utils.ValidationUtils;
import guiInterface.ActivityView;
import semantic.parser.Activity;
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;
import validation.CheckInpusOutputsRowCountMatches;

public class ActivityViewImpl implements ActivityView{

	Activity activity;
	
	public ActivityViewImpl(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public JPanel getActivityView() {
		
		JPanel panel = new JPanel ();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(GuiUtils.addLabel ("Activity Details"));
		panel.add(GuiUtils.wrapTextWithLabel("Activity: ",activity.getActivityType(),null));
		//panel.add(GuiUtils.wrapTextWithLabel("Responsible Person(s): ",activity.getAgents().toString(),null));
		panel.add(GuiUtils.wrapAgentListWithLabelNoColor ("Responsible Person(s): ", activity.getAgents()));
		//panel.add(GuiUtils.wrapTextWithLabel("Responsible Person(s): ",activity.getInputs().toString(),null));
		panel.add(GuiUtils.wrapParameterListWithLabelNoColor ("Inputs: ", activity.getInputs()));
		panel.add(GuiUtils.wrapParameterListWithLabelNoColor ("Outputs: ", activity.getOutputs()));
		panel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
		panel.add(GuiUtils.addLabel ("Validations"));
		
		String resultMatchingRows = "";
		
		JsonLdProcessor dataProcessor = new JsonLdProcessor ();
		ArrayList<HashMap<String, String>> rowCountDoesntMatch =  CheckInpusOutputsRowCountMatches.checkActivity(activity.getURI(), dataProcessor.getModel());
	    if (rowCountDoesntMatch.size()>0) {
	    	
	    	for (int i =0; i <rowCountDoesntMatch.size();i++ ) {
	    		resultMatchingRows = resultMatchingRows + rowCountDoesntMatch.get(i).get("datasetL");
	    		
	    		if (i+1!=rowCountDoesntMatch.size()) {
	    			resultMatchingRows = resultMatchingRows +",";
	        	}
	    	}
	    	
	    }
	   
		panel.add(ValidationUtils.simpleResult("Number of Rows input/output", resultMatchingRows));
		
		panel.add(ValidationUtils.simpleResult("Outputs same as inputs (check hash)", "not implemented"));
		
		panel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
		
		
		System.out.println(activity.getOutputs());
		
		return panel;
	}

}
