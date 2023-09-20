package Utils;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.DatabaseFrame;
import gui.DatasetFrame;
import gui.LinkagePlanFrame;
import semantic.parser.Agent;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Database;
import semantic.parser.Dataset;
import semantic.parser.Entity;
import semantic.parser.LinkagePlan;

public class GuiUtils {
	
	public static  JPanel wrapTextWithLabel (String labelText, String value, Color color) {
    	JPanel panel = new JPanel (); 
    	FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
    	panel.setLayout(layout);
    	JLabel label = new JLabel (labelText);
    	Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());
        label.setFont(boldFont);
        
    	JLabel textField = new JLabel(value);
    	
    	if (color != null) {
    		textField.setForeground(color);
    	}
    	
    	panel.add(label);
    	panel.add(textField);
    	
    	return panel;
    }
	
	
	public static  JPanel wrapTextWithLabelNoColor (String labelText, String value) {
    	JPanel panel = new JPanel (); 
    	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	JLabel label = new JLabel (labelText);
    	Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());
        label.setFont(boldFont);
        
    	JTextField textField = new JTextField(value);

    	textField.setEditable(false);
    	textField.setBackground(Color.white);
    	panel.add(label);
    	panel.add(textField);
    	
    	return panel;
    }
	
	public static  JPanel wrapButtonWithLabelNoColor (String labelText, JButton button) {
    	JPanel panel = new JPanel (); 
    	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	JLabel label = new JLabel (labelText);
    	Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());
        label.setFont(boldFont);
        
    	panel.add(label);
    	panel.add(button);
    	
    	return panel;
    }
	
	public static  JPanel wrapParameterListWithLabelNoColor (String labelText, ArrayList <Entity> list,CommentsJsonLdProcessor commentsJsonLdProcessor) {
    	JPanel panel = new JPanel (); 
    	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	JLabel label = new JLabel (labelText);
    	panel.add(label);
    	Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());
        label.setFont(boldFont);
    	
    	for (int i=0;i<list.size();i++) {
    		panel.add(createLinkLabel(list.get(i),commentsJsonLdProcessor));
    	}
    	
    	
    	return panel;
    }
	
	public static  JPanel wrapAgentListWithLabelNoColor (String labelText, ArrayList <Agent> list) {
    	JPanel panel = new JPanel (); 
    	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	JLabel label = new JLabel (labelText);
    	panel.add(label);
    	Font boldFont = new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize());
        label.setFont(boldFont);
    	
    	for (int i=0;i<list.size();i++) {
    		panel.add(new JLabel (list.get(i).getURI()));
    	}
    	
    	
    	return panel;
    }
	
	private static JLabel createLinkLabel( Entity obj, CommentsJsonLdProcessor commentsJsonLdProcessor) {
		JLabel label=  new JLabel(obj.getEntityL());
		
		System.out.println("Class"+obj.getClass());
		
		if (obj.getClass() == Dataset.class) {
		 
       
        label.setForeground(Color.BLUE);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add a MouseListener to handle click events
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new DatasetFrame ((Dataset) obj,commentsJsonLdProcessor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.BLUE);
            }
        });
        
		}
		
		if (obj.getClass() == LinkagePlan.class) {
			 
		       
	        label.setForeground(Color.DARK_GRAY);
	        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	        // Add a MouseListener to handle click events
	        label.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	 new LinkagePlanFrame ();
	            }

	            @Override
	            public void mouseEntered(MouseEvent e) {
	                label.setForeground(Color.RED);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                label.setForeground(Color.DARK_GRAY);
	            }
	        });
	        
			}
		
		if (obj.getClass() == Database.class) {
			 
		       
	        label.setForeground(Color.BLACK);
	        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	        // Add a MouseListener to handle click events
	        label.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	 new DatabaseFrame ((Database) obj,commentsJsonLdProcessor);
	            }

	            @Override
	            public void mouseEntered(MouseEvent e) {
	                label.setForeground(Color.RED);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                label.setForeground(Color.BLACK);
	            }
	        });
	        
			}

       
		return label;
    }
	
	
	private static JLabel createLinkLabel( LinkagePlan plan) {
        JLabel label = new JLabel(plan.getDatasetL());
       
        label.setForeground(Color.DARK_GRAY);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add a MouseListener to handle click events
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               new LinkagePlanFrame ();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.DARK_GRAY);
            }
        });

        return label;
    }
	
	
	public static JLabel addLabel(String text) {
	    JLabel label = new JLabel(text, JLabel.CENTER);
	    
	    return (label);
	}

}
