package Utils;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GuiUtils {
	
	public static  JPanel wrapValueWithLabel (String labelText, String value, Color color) {
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

}
