package guiComponentImpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Utils.IconTextItem;
import Utils.IconTextListRenderer;
import guiInterface.ActivityListInterface;
import guiInterface.ActivityView;

import semantic.parser.Activity;
import semantic.parser.Agent;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import semantic.parser.LinkagePlan;

public class ActivityListImpl implements ActivityListInterface {
	
	private DefaultListModel<IconTextItem> listModel;
    private JList<IconTextItem> jList;
    private JPanel activityViewer;
	private CommentsJsonLdProcessor commentsJsonLdProcessor;

	public ActivityListImpl(JPanel activityViewer, CommentsJsonLdProcessor commentsJsonLdProcessor) {
		this.activityViewer = activityViewer;
		this.commentsJsonLdProcessor = commentsJsonLdProcessor;
	}

	@Override
	public JPanel getActivityList() {
		JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
       

        // Create a label for the title
        JLabel titleLabel = new JLabel("Activities");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        // Create the list model
        listModel = new DefaultListModel<>();

        // Create the JList with the list model
        jList = new JList<>(listModel);

        // Set a custom renderer for the JList to display icons and text
        jList.setCellRenderer(new IconTextListRenderer());

        // Add a selection listener to the JList
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = jList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        IconTextItem selectedItem = listModel.getElementAt(selectedIndex);
                        System.out.println("Selected Item: " + selectedItem.getText());
                        activityViewer.removeAll();
                        ActivityView view =  new ActivityViewImpl (selectedItem.getActivity(), commentsJsonLdProcessor);
                        activityViewer.add(view.getActivityView());
                        activityViewer.revalidate();
                    }
                }
            }
        });

        // Add the JList to a scroll pane
        JScrollPane scrollPane = new JScrollPane(jList);
        panel.add(scrollPane);

        
       
		return panel;
	}
	
	// we assume that we only have one agent responsible 
	@Override
	public void loadActivities () {
		 
		 ArrayList <Activity> list = new ArrayList <Activity> ();
		
		 JsonLdProcessor dataProcessor = new JsonLdProcessor ();
		 ArrayList<HashMap<String, String>> resultList = dataProcessor.getActivityData();
		
		 for (int i = 0; i  < resultList.size();i++ ) {
			 
			 String activityURI = resultList.get(i).get("activity");
			 
			 Activity newact = new Activity (activityURI);
			 
			 newact.setActivityL(resultList.get(i).get("activityL"));
			 
			 newact.setActivityEndDate(resultList.get(i).get("activityEndTime"));
			 
			 ArrayList<HashMap<String, String>> inputs = dataProcessor.getActivityInputs(activityURI);
			 
			 
			 
			 for (int j=0;j<inputs.size();j++) {
				 
				
				 if (inputs.get(j).get("inputType").contains("DataSet")) {
				 
				 Dataset dataset = new Dataset (inputs.get(j).get("input")); 
				 dataset.setEntityL(inputs.get(j).get("inputL"));
				 newact.getInputs().add(dataset);
				 }
				 
				 if (inputs.get(j).get("inputType").contains("DataLinkagePlan")) {
					 
					 LinkagePlan plan = new LinkagePlan (inputs.get(j).get("input")); 
					 plan.setEntityL(inputs.get(j).get("inputL"));
					 
					
					 
					 newact.getInputs().add(plan);
				 }
				 
                 if (inputs.get(j).get("inputType").contains("Database")) {
					 
					 Dataset dataset = new Dataset (inputs.get(j).get("input")); 
					 dataset.setEntityL(inputs.get(j).get("inputL"));
					 newact.getInputs().add(dataset);
				 }
			 }
			 
			 ArrayList<HashMap<String, String>> outputs = dataProcessor.getActivityOutputs(activityURI);
			 
			
			 
			 for (int j=0;j<outputs.size();j++) {
			 
			 
				 if (outputs.get(j).get("outputType").contains("DataSet")) {
					 
					 Dataset dataset = new Dataset (outputs.get(j).get("output")); 
					 dataset.setEntityL(outputs.get(j).get("outputL"));
					 newact.getOutputs().add(dataset);
					 }
					 
					 if (outputs.get(j).get("outputType").contains("DataLinkagePlan")) {
						 
						 LinkagePlan plan = new LinkagePlan (outputs.get(j).get("output")); 
						 plan.setEntityL(outputs.get(j).get("outputL"));
						 
 
						 newact.getOutputs().add(plan);
					 }
					 
	                 if (outputs.get(j).get("outputType").contains("Database")) {
						 
						 Dataset dataset = new Dataset (outputs.get(j).get("output")); 
						 dataset.setEntityL(outputs.get(j).get("outputL"));
						 newact.getOutputs().add(dataset);
					 }
			 
			 
			 
			 
			 
			 
			 }
			 
			 

			 ArrayList<HashMap<String, String>> agents = dataProcessor.getActivityAgents(activityURI);
			
			 for (int j=0;j<agents.size();j++) {
				 Agent agent = new Agent (agents.get(j).get("agent")); 
				 
				 newact.getAgents().add(agent);
			 }
	
			
		        addItem(newact, true);
		       
			 
		 }
		 
		 if (jList.getSelectedIndex()<0 && listModel.size()>0) {
	        	jList.setSelectedIndex(0);
	        }
		 
		
	}
	
	
	private void addItem(Activity act, boolean hasIcon) {
        // Add text to the list model
        listModel.addElement(new IconTextItem(act, hasIcon));
        
        

        // Get the index of the newly added item
        int index = listModel.getSize() - 1;

        // If the flag is set to true, add an icon
        if (hasIcon) {
            ImageIcon icon = new ImageIcon(ActivityListImpl.class.getClassLoader().getResource("warning.png"));  // Replace with your own icon path
            listModel.getElementAt(index).setIcon(icon);
        }
    }
	
	
	
	@Override
	public DefaultListModel getListModel() {
		
		return listModel;
	}

}
