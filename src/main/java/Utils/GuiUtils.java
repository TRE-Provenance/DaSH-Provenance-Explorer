package Utils;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuiUtils {
	
	public static  JPanel wrapTextWithLabel (String labelText, String value, Color color) {
    	JPanel panel = new JPanel (); 
    	FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
    	panel.setLayout(layout);
    	JLabel label = new JLabel (labelText);
    	JTextField textField = new JTextField(value);
    	
    	if (color != null) {
    		textField.setBackground(color);
    	}
    	
    	panel.add(label);
    	panel.add(textField);
    	
    	return panel;
    }
	
	
	public static  JPanel wrapTextWithLabelNoColor (String labelText, String value) {
    	JPanel panel = new JPanel (); 
    	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	JLabel label = new JLabel (labelText);
    	JTextField textField = new JTextField(value);

    	textField.setBackground(Color.gray);
    	panel.add(label);
    	panel.add(textField);
    	
    	return panel;
    }
	
	public static  JPanel wrapButtonWithLabelNoColor (String labelText, JButton button) {
    	JPanel panel = new JPanel (); 
    	panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    	JLabel label = new JLabel (labelText);
    	panel.add(label);
    	panel.add(button);
    	
    	return panel;
    }

}
