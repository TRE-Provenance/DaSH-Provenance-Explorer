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
import guiInterface.CommentListInterface;
import semantic.parser.Activity;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;
import validation.CheckInpusOutputsRowCountMatches;

public class ActivityViewImpl implements ActivityView{

	Activity activity;
	private CommentsJsonLdProcessor commentsJsonLdProcessor;
	private JPanel activityViewPanel;
	
	public ActivityViewImpl(Activity activity, CommentsJsonLdProcessor commentsJsonLdProcessor) {
		this.activity = activity;
		this.commentsJsonLdProcessor = commentsJsonLdProcessor;
		
		activityViewPanel = new JPanel ();
		activityViewPanel.setLayout(new BoxLayout(activityViewPanel, BoxLayout.PAGE_AXIS));
		activityViewPanel.add(GuiUtils.addLabel ("Activity Details"));
		activityViewPanel.add(GuiUtils.wrapTextWithLabel("Activity: ",activity.getActivityType(),null));
		//panel.add(GuiUtils.wrapTextWithLabel("Responsible Person(s): ",activity.getAgents().toString(),null));
		activityViewPanel.add(GuiUtils.wrapAgentListWithLabelNoColor ("Responsible Person(s): ", activity.getAgents()));
		//panel.add(GuiUtils.wrapTextWithLabel("Responsible Person(s): ",activity.getInputs().toString(),null));
		activityViewPanel.add(GuiUtils.wrapParameterListWithLabelNoColor ("Inputs: ", activity.getInputs(),commentsJsonLdProcessor));
		activityViewPanel.add(GuiUtils.wrapParameterListWithLabelNoColor ("Outputs: ", activity.getOutputs(),commentsJsonLdProcessor));
		activityViewPanel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
		activityViewPanel.add(GuiUtils.addLabel ("Validations"));
		
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
	   
	    activityViewPanel.add(ValidationUtils.simpleResult("Number of Rows input/output", resultMatchingRows));
		
	    activityViewPanel.add(ValidationUtils.simpleResult("Outputs same as inputs (check hash)", "not implemented"));
		
	    activityViewPanel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
		
	    activityViewPanel.add(GuiUtils.addLabel ("Comments"));
		
		CommentListInterface comments = new CommentListImpl (activity.getURI(),commentsJsonLdProcessor);
		
		activityViewPanel.add(comments.getCommentList());
		
		System.out.println(activity.getOutputs());
		
	}
	
	@Override
	public JPanel getActivityView() {
		
		
		return activityViewPanel;
	}

}
