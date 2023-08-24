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

import guiInterface.ActivityListInterface;
import guiInterface.ActivityView;
import guiInterface.ResultObject;
import semantic.parser.Activity;
import semantic.parser.Agent;
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import semantic.parser.LinkagePlan;

public class ActivityListImpl implements ActivityListInterface {
	
	private DefaultListModel<IconTextItem> listModel;
    private JList<IconTextItem> jList;
    private JPanel activityViewer;

	public ActivityListImpl(JPanel activityViewer) {
		this.activityViewer = activityViewer;
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
                        ActivityView view =  new ActivityViewImpl (selectedItem.getActivity());
                        activityViewer.add(view.getActivityView());
                        activityViewer.revalidate();
                    }
                }
            }
        });

        // Add the JList to a scroll pane
        JScrollPane scrollPane = new JScrollPane(jList);
        panel.add(scrollPane);

        loadActivities ();
       
		return panel;
	}
	
	// we assume that we only have one agent responsible 
	private void loadActivities () {
		 
		 ArrayList <Activity> list = new ArrayList <Activity> ();
		
		 JsonLdProcessor dataProcessor = new JsonLdProcessor ();
		 ArrayList<HashMap<String, String>> resultList = dataProcessor.getActivityData();
		
		 for (int i = 0; i  < resultList.size();i++ ) {
			 
			 String activityURI = resultList.get(i).get("activity");
			 
			 Activity newact = new Activity (activityURI);
			 
			 newact.setActivityL(resultList.get(i).get("activityL"));
			 
			 ArrayList<HashMap<String, String>> inputs = dataProcessor.getActivityInputs(activityURI);
			 
			 
			 
			 for (int j=0;j<inputs.size();j++) {
				 
				 System.out.println (inputs.get(j).get("inputType"));
				 
				 if (inputs.get(j).get("inputType").contains("Dataset")) {
				 
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
				 Dataset dataset = new Dataset (outputs.get(j).get("output")); 
				 dataset.setDatasetL(outputs.get(j).get("outputL"));
				 newact.getOutputs().add(dataset);
			 }
			 
			 

			 ArrayList<HashMap<String, String>> agents = dataProcessor.getActivityAgents(activityURI);
			
			 for (int j=0;j<agents.size();j++) {
				 Agent agent = new Agent (agents.get(j).get("agent")); 
				 
				 newact.getAgents().add(agent);
			 }
	
			
		        addItem(newact, true);
		       
			 
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
	
	
	// Custom class to hold the icon and text for an item
    private static class IconTextItem {
        private final String text;
        private Icon icon;
        private Activity activity;

        public IconTextItem(Activity act, boolean hasIcon) {
        	this.activity = act;
            this.text = act.getActivityType();
            if (hasIcon) {
                this.icon = new ImageIcon(ActivityListImpl.class.getClassLoader().getResource("warning.png")); // Replace with your own icon path
            }
        }
        
        public Activity getActivity() {
        	return activity;
        }

        public String getText() {
            return text;
        }

        public Icon getIcon() {
            return icon;
        }

        public void setIcon(Icon icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    // Custom list cell renderer to display icons and text
    private static class IconTextListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
        	JPanel panel = new JPanel(new BorderLayout());

            if (value instanceof IconTextItem) {
                IconTextItem item = (IconTextItem) value;

                // Create a label for the text
                JLabel textLabel = new JLabel(item.getText());

                // Set the icon on the left side of the text
                if (item.getIcon() != null) {
                    JLabel iconLabel = new JLabel(item.getIcon());
                    iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                    panel.add(iconLabel, BorderLayout.EAST);
                }
                
                panel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
                panel.add(textLabel, BorderLayout.CENTER);

                // Set the background and selection color
                if (isSelected) {
                    panel.setBackground(list.getSelectionBackground());
                    panel.setForeground(list.getSelectionForeground());
                } else {
                    panel.setBackground(list.getBackground());
                    panel.setForeground(list.getForeground());
                }

                panel.setEnabled(list.isEnabled());
                panel.setFont(list.getFont());
                panel.setOpaque(true);
            }

            return panel;
        }
    }

}
