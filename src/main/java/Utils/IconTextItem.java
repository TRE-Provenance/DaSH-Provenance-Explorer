package Utils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import guiComponentImpl.ActivityListImpl;

import semantic.parser.Activity;

//Custom class to hold the icon and text for an item
public   class IconTextItem {
    private final String text;
    private Icon icon;
    private Activity activity;

    public IconTextItem(Activity act, boolean hasIcon) {
    	this.activity = act;
    	
    	SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

        
            // Parse the input date and time string into a Date object
        String formattedDateTime="N/A";
			try {
				Date date = inputFormat.parse(act.setActivityEndDate());
				  formattedDateTime = outputFormat.format(date);
			} catch (ParseException e) {
				
				e.printStackTrace();
			}

            // Format the date object to the desired format
           
    	
       
		this.text = "("+formattedDateTime+") - " + act.getActivityL();
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
