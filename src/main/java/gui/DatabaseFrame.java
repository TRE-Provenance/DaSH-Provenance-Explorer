package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.Box.Filler;
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
    setSize(800, 500);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
 
    
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("Database Details"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
    ArrayList<HashMap<String, String>> databaseDetails = dataProcessor.getDatabaseDetails(database.getURI());
   
    if (databaseDetails.size()>1) {
    	 mainInfo.add(GuiUtils.wrapTextWithLabel ("Error: ", "Provenance trace includes duplicate entries for the same database",Color.red));
    }
    else {
    database.setAbbreviation(databaseDetails.get(0).get("abbreviation"));
    database.setVersion(databaseDetails.get(0).get("version"));
    database.setDataCustodian(databaseDetails.get(0).get("dataCustodian"));
    database.setDescription(databaseDetails.get(0).get("description"));
    database.setContact(databaseDetails.get(0).get("contact"));
    database.setLastKnownUpdate(databaseDetails.get(0).get("lastKnownUpdate"));
    database.setMostRecentRecordDate(databaseDetails.get(0).get("mostRecentRecordDate"));
    database.setOldestRecordDate(databaseDetails.get(0).get("oldestRecordDate"));
    database.setContextualInformationLink(databaseDetails.get(0).get("contextualInformationLink"));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Description: ", database.getDescription() ));
    mainInfo.add(Box.createVerticalStrut(5));
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Abbreviation: ", database.getAbbreviation() ));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Version: ", database.getVersion() ));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Data Custodian: ", database.getDataCustodian() ));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Contact: ", database.getContact() ));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Last Known Update: ", database.getLastKnownUpdate() ));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Most Recent Record Date: ", database.getMostRecentRecordDate() ));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Oldest Record Date: ", database.getOldestRecordDate()));
    
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("More Info: ", database.getContextualInformationLink()));
    
    Box.Filler glue = (Filler) Box.createVerticalGlue();
    glue.changeShape(glue.getMinimumSize(), 
                    new Dimension(0, Short.MAX_VALUE), // make glue greedy
                    glue.getMaximumSize());
    
    
    
    mainInfo.add(glue);
    
    }
    
   
    

    
    
   
    
   
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
