package guiComponentImpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Utils.IconTextItem;
import Utils.IconTextItemEntity;
import Utils.IconTextListRenderer;
import gui.DatabaseFrame;
import gui.DatasetFrame;
import gui.LinkagePlanFrame;
import guiInterface.ActivityView;
import guiInterface.EntityListInterface;
import semantic.parser.Activity;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Database;
import semantic.parser.Dataset;
import semantic.parser.Entity;
import semantic.parser.LinkagePlan;

public class EntityListImpl implements EntityListInterface {
	
	private DefaultListModel<IconTextItemEntity> listModel;
    private JList<IconTextItemEntity> jList;
	
    private JPanel  panel;
	private CommentsJsonLdProcessor commentsJsonLdProcessor;

	
	public EntityListImpl( ArrayList <Entity> entries, CommentsJsonLdProcessor commentsJsonLdProcessor) {
		
		this.commentsJsonLdProcessor = commentsJsonLdProcessor;
		
		panel = new JPanel();
		
		panel.setLayout(new BorderLayout());

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
                        IconTextItemEntity selectedItem = listModel.getElementAt(selectedIndex);
                        System.out.println("Selected Item: " + selectedItem.getText());
                        
                        if (selectedItem.getEntity() instanceof Database) {
                    	   new DatabaseFrame ((Database) selectedItem.getEntity(),commentsJsonLdProcessor);
                       }
                        
                        if (selectedItem.getEntity() instanceof Dataset) {
                     	   new DatasetFrame ((Dataset) selectedItem.getEntity(),commentsJsonLdProcessor);
                        }
                        
                        if (selectedItem.getEntity() instanceof LinkagePlan) {
                      	   new LinkagePlanFrame ();
                         }
                    }
                }
            }
        });

        // Add the JList to a scroll pane
        panel.setMinimumSize(new Dimension(100, 50)); // Set your desired minimum size
        panel.setPreferredSize(new Dimension(100, 50)); // Set your desired minimum size

        
        JScrollPane scrollPane = new JScrollPane(jList);
        
        panel.add(scrollPane,  BorderLayout.CENTER);
        loadEntities (entries);
	}

	private void loadEntities(ArrayList<Entity> entries) {
		for (int i=0; i < entries.size();i++) {
			addItem(entries.get(i), true);
			
		}
		
	}
	
	private void addItem(Entity en, boolean hasIcon) {
        // Add text to the list model
        listModel.addElement(new IconTextItemEntity(en, hasIcon));
		

        // Get the index of the newly added item
        int index = listModel.getSize() - 1;

        // If the flag is set to true, add an icon
        if (hasIcon) {
    //        ImageIcon icon = new ImageIcon(EntityListImpl.class.getClassLoader().getResource("warning.png"));  // Replace with your own icon path
  //        listModel.getElementAt(index).setIcon(icon);
        }
	}

	@Override
	public JPanel getEntityList() {
		// TODO Auto-generated method stub
	    //panel.add(new JLabel("djhe"));
		return panel;
	}

	@Override
	public DefaultListModel getListModel() {
		// TODO Auto-generated method stub
		return listModel;
	}

}
