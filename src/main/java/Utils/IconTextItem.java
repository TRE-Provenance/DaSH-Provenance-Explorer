package Utils;

import java.awt.BorderLayout;
import java.awt.Component;

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
        this.text = "("+act.setActivityEndDate()+") - " + act.getActivityType();
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
