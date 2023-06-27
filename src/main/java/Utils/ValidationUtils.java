package Utils;

import java.awt.Color;

import javax.swing.JPanel;

public class ValidationUtils {

	
	public static void simpleResult (String validationName, JPanel panel, String result) {
		 if (result.equals("")) {
			 panel.add(GuiUtils.wrapTextWithLabel (validationName+": ", "OK", Color.GREEN));
         }
         else {
        	 panel.add(GuiUtils.wrapTextWithLabel (validationName+": ", result, Color.RED));
         }
	}
}
