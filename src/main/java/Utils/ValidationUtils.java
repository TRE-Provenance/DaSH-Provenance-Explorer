package Utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import guiComponentImpl.EntityListImpl;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Constants;
import semantic.parser.Entity;

public class ValidationUtils {

	
	public static JPanel simpleResult (String validationName,  String result) {
		 if (result.equals("")) {
			 return GuiUtils.wrapTextWithLabel (validationName+": ", "OK", Color.BLACK);
         }
         else {
        	 return GuiUtils.wrapTextWithLabel (validationName+": ", result, Color.RED);
         }
	}
	
	public static JPanel entityResult (String validationName,  ArrayList <Entity> result) {
		 if (result.size()==0) {
			 return GuiUtils.wrapTextWithLabel (validationName+": ", "OK", Color.BLACK);
        }
        else {
        	JPanel panel = new JPanel (); 
        	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    	    
    	    panel.add(GuiUtils.addLabel (validationName));
    	    panel.add(new EntityListImpl (result,Constants.light_orange , new CommentsJsonLdProcessor()).getEntityList());
        	
       	 return panel;
        }
	}
	
	public static JPanel entityResultNoColor (String validationName,  ArrayList <Entity> result) {
		 if (result.size()==0) {
			 return GuiUtils.wrapTextWithLabel (validationName+": ", "OK", Color.BLACK);
       }
       else {
       	JPanel panel = new JPanel (); 
       	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
   	    
   	    panel.add(GuiUtils.addLabel (validationName));
   	    panel.add(new EntityListImpl (result,Color.WHITE , new CommentsJsonLdProcessor()).getEntityList());
       	
      	 return panel;
       }
	}
}
