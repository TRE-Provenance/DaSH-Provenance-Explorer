package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import Utils.GuiUtils;
import Utils.ValidationUtils;
import guiComponentImpl.CommentListImpl;
import guiInterface.CommentListInterface;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Database;
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;

public class DatabaseFrame extends JFrame {
	
	private CommentsJsonLdProcessor commentsJsonLdProcessor;
	
public 	DatabaseFrame (Database database,CommentsJsonLdProcessor commentsJsonLdProcessor) {
	
	this.commentsJsonLdProcessor  = commentsJsonLdProcessor; 
	
	setTitle (database.getURI());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(700, 400);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("Database Details"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
    ArrayList<HashMap<String, String>> summaryStats = dataProcessor.getSummaryStatsForFile(database.getURI());
    
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Description: ", summaryStats.get(0).get("description"),null));
   
    

    
    
   
    
   
    panel.add(mainInfo, BorderLayout.CENTER);
    
    
    
    
    CommentListInterface comments = new CommentListImpl (database.getURI(),commentsJsonLdProcessor);
	
    JPanel panelWrapper = new JPanel ();
    panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.PAGE_AXIS));
	
    panelWrapper.add(new JLabel ("Comments"));
    
    panelWrapper.add(comments.getCommentList());
    
    panel.add(panelWrapper, BorderLayout.EAST);
   
    
  
    
    
    
    add(panel);
    setVisible(true);
    
}

}
