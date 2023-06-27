package Utils;

import java.awt.Color;

import javax.swing.JPanel;

public class ValidationUtils {

	
	public static JPanel simpleResult (String validationName,  String result) {
		 if (result.equals("")) {
			 return GuiUtils.wrapTextWithLabel (validationName+": ", "OK", Color.BLACK);
         }
         else {
        	 return GuiUtils.wrapTextWithLabel (validationName+": ", result, Color.RED);
         }
	}
}
