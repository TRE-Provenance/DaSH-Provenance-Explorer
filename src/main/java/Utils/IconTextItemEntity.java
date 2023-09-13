package Utils;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import guiComponentImpl.ActivityListImpl;
import semantic.parser.Activity;
import semantic.parser.Database;
import semantic.parser.Dataset;
import semantic.parser.Entity;
import semantic.parser.LinkagePlan;
import semantic.parser.Variable;

public class IconTextItemEntity {
	 private final String text;
	    private Icon icon;
	    private Entity en;

	    public IconTextItemEntity(Entity en, boolean hasIcon) {
	    	this.en = en;
	        this.text = en.getEntityL();
	        
	       
	        if (hasIcon) {
	        	
	        	if (en instanceof Database) {
	            this.icon = new ImageIcon(IconTextItemEntity.class.getClassLoader().getResource("database.png")); // Replace with your own icon path
	        	}
	        	
	        	
	        	
	        	if (en instanceof Variable) {
		            this.icon = new ImageIcon(IconTextItemEntity.class.getClassLoader().getResource("variable.png")); // Replace with your own icon path
		        	}
	        	
	        	if (en instanceof Dataset) {
		            this.icon = new ImageIcon(IconTextItemEntity.class.getClassLoader().getResource("dataset.png")); // Replace with your own icon path
		        	}
	        	if (en instanceof LinkagePlan) {
		            this.icon = new ImageIcon(IconTextItemEntity.class.getClassLoader().getResource("linkage.png")); // Replace with your own icon path
		        	}
	        }
	    }
	    
	    public Entity getEntity() {
	    	return en;
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
