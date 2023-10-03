package guiComponentImpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import org.apache.jena.rdf.model.Model;

import Utils.GuiUtils;
import Utils.ValidationUtils;
import guiInterface.ActivityView;
import guiInterface.CommentListInterface;
import semantic.parser.Activity;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Dataset;
import semantic.parser.Entity;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;
import validation.CheckInpusOutputsRowCountMatches;
import validation.ValidationEngine;
import validation.ValidationRuleInterface;

public class ActivityViewImpl implements ActivityView{

	Activity activity;
	private CommentsJsonLdProcessor commentsJsonLdProcessor;
	private JPanel activityViewPanel;
	
	public ActivityViewImpl(Activity activity, CommentsJsonLdProcessor commentsJsonLdProcessor) {
		this.activity = activity;
		this.commentsJsonLdProcessor = commentsJsonLdProcessor;
		
		activityViewPanel = new JPanel ();
		// Create a LineBorder with a specified color and thickness
       		activityViewPanel.setLayout(new BoxLayout(activityViewPanel, BoxLayout.Y_AXIS));
		//activityViewPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		activityViewPanel.add(GuiUtils.addLabel ("Activity Details"));
		activityViewPanel.add(GuiUtils.wrapTextWithLabel("Activity: ",activity.getDescription(),null));
		//panel.add(GuiUtils.wrapTextWithLabel("Responsible Person(s): ",activity.getAgents().toString(),null));
		activityViewPanel.add(GuiUtils.wrapAgentListWithLabelNoColor ("Completed by: ", activity.getAgents()));
		//panel.add(GuiUtils.wrapTextWithLabel("Responsible Person(s): ",activity.getInputs().toString(),null));
		//activityViewPanel.add(GuiUtils.wrapParameterListWithLabelNoColor ("Inputs: ", activity.getInputs(),commentsJsonLdProcessor));
		//activityViewPanel.add(GuiUtils.wrapParameterListWithLabelNoColor ("Inputs: ",null);
		EntityListImpl entityList = new EntityListImpl (activity.getInputs(), Color.WHITE , commentsJsonLdProcessor); 
		JPanel listPanel = entityList.getEntityList();
		activityViewPanel.add(GuiUtils.addLabel ("Inputs (" + entityList.getListModel().getSize() +")" ));
		activityViewPanel.add(listPanel);
		
		entityList = new EntityListImpl (activity.getOutputs(), Color.WHITE , commentsJsonLdProcessor); 
		listPanel = entityList.getEntityList();
		activityViewPanel.add(GuiUtils.addLabel ("Outputs (" + entityList.getListModel().getSize() +")" ));
		activityViewPanel.add(listPanel);
		
		//activityViewPanel.add(GuiUtils.wrapParameterListWithLabelNoColor ("Outputs: ", activity.getOutputs(),commentsJsonLdProcessor));
		activityViewPanel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
		activityViewPanel.add(GuiUtils.addLabel ("Validations"));
		
		
		
	
	
		String [] args = {activity.getURI()};
		JsonLdProcessor dataProcessor = new JsonLdProcessor ();
		
		ValidationEngine engine = new ValidationEngine () ; 
		
		ArrayList<ValidationRuleInterface> validations = engine.getSettings().get(activity.getActivityType());
		
		
	    if (validations!=null) {	
		for (int i=0; i<validations.size();i++) {
			
			 activityViewPanel.add( validations.get(i).getSimpleResult (args, dataProcessor.getModel()));
			
		}
	    }
	    
	    else {
	    	JLabel label = new JLabel ("No validation checks set for this activity ");
	    	label.setAlignmentX(Component.LEFT_ALIGNMENT);
	    	 activityViewPanel.add(  GuiUtils.wrapTextWithLabel ("No validation checks set for this activity ","", null));
	    }
	   
	    
	    
		
	 //   activityViewPanel.add(ValidationUtils.simpleResult("Outputs same as inputs (check hash)", "not implemented"));
		
	    activityViewPanel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
		
	    activityViewPanel.add(GuiUtils.addLabel ("Comments"));
		
		CommentListInterface comments = new CommentListImpl (activity.getURI(),commentsJsonLdProcessor);
		
		activityViewPanel.add(comments.getCommentList());
		
		System.out.println(activity.getOutputs());
		
	}
	
	@Override
	public JPanel getActivityView() {
		
		JPanel panel = new JPanel (new BorderLayout());
		panel.add(activityViewPanel,BorderLayout.CENTER);
		return activityViewPanel;
	}

}
