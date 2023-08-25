package guiInterface;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;



public interface ActivityListInterface {
	
	public JPanel getActivityList ();
	
	public DefaultListModel getListModel ();
	
	public void loadActivities ();

}
