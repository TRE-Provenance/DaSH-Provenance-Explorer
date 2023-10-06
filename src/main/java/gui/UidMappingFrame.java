package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import Utils.GuiUtils;
import Utils.ValidationUtils;
import guiComponentImpl.CommentListImpl;
import guiInterface.CommentListInterface;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Constants;
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import semantic.parser.UidMapping;
import validation.CheckForSensitiveVariablesInFile;
import validation.ValidationEngine;
import validation.ValidationRuleInterface;

public class UidMappingFrame extends JFrame {
	
	private CommentsJsonLdProcessor commentsJsonLdProcessor;
	
public 	UidMappingFrame (UidMapping mappingFile,CommentsJsonLdProcessor commentsJsonLdProcessor) {
	
	this.commentsJsonLdProcessor  = commentsJsonLdProcessor; 
	
	setTitle (mappingFile.getURI());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(1200, 600);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("UidMapping Details"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
    ArrayList<HashMap<String, String>> summaryStats = dataProcessor.getSummaryStatsForFile(mappingFile.getURI());
    
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Description: ", summaryStats.get(0).get("description"),null));
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Path: ", mappingFile.getPath(),null));
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Row Count: ", summaryStats.get(0).get("rowCount"),null));
    
   
  
    mainInfo.add(new JSeparator(JSeparator.HORIZONTAL),
            BorderLayout.LINE_START);
    mainInfo.add(GuiUtils.addLabel ("Validations"));
    

    /*
    ArrayList<HashMap<String, String>> sensitiveVariablesFound =  CheckForSensitiveVariablesInFile.checkFile(dataset.getURI(), dataProcessor.getModel());
    String result = "";

    if (sensitiveVariablesFound.size()>0) {
    	
    	for (int i =0; i <sensitiveVariablesFound.size();i++ ) {
    		result = result + sensitiveVariablesFound.get(i).get("variableL");
    		
    		if (i+1!=sensitiveVariablesFound.size()) {
    			result = result +",";
        	}
    	}
    	
    }
    
    mainInfo.add ( ValidationUtils.simpleResult("Sensitive Variables", result));
    mainInfo.add (ValidationUtils.simpleResult("Variable range  exceeds Spec", "not implemented"));
    */
    
    
    String [] args = {mappingFile.getURI()};
	
	ValidationEngine engine = new ValidationEngine () ; 
	
	ArrayList<ValidationRuleInterface> validations = engine.getSettings().get("https://w3id.org/shp#UidMapping");
	
	
    if (validations!=null) {	
	for (int i=0; i<validations.size();i++) {
		
		mainInfo.add( validations.get(i).getSimpleResult (args, dataProcessor.getModel()));
		
	}
    }
   
    mainInfo.add(new JSeparator(JSeparator.HORIZONTAL),
            BorderLayout.LINE_START);
    
   
    panel.add(mainInfo, BorderLayout.NORTH);
    
    
    
   
    
    
    
    
    
    ArrayList<HashMap<String, String>> variables = dataProcessor.getVariablesInFile(mappingFile.getURI());
    //keep the first column empty
    
    //To do - load dynamically 
    Object[] columnNames = new Object [2];
    columnNames[0] = "";
    columnNames[1] = "Unique CHI's";
   
    
    
    
    
 
    
    
    ArrayList<HashMap<String, String>> variableStats = dataProcessor.getVariableStatsForFile(mappingFile.getURI());
    
    int NUMB_STATS_DISPLAYED = 1;
   
    Object[][] rowData = new Object[variables.size()][NUMB_STATS_DISPLAYED+1];
    
    
    System.out.println ( variableStats.size());
     	
    	for (int j = 0 ; j < variableStats.size(); j ++ ) {
    		
    		rowData [j][0] = GuiUtils.formatDataType(variableStats.get(j).get("variableL"));
    		rowData [j][1] = GuiUtils.formatDataType(variableStats.get(j).get("uniqueChi"));
    		
    		
    	}	
    	   

    JTable table = new JTable(rowData, columnNames);
    table.setEnabled(false);
    
    
    
    
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(600, 200));
    
    
    JPanel scrollPanelWrapper = new JPanel ();
    scrollPanelWrapper.setLayout(new BoxLayout(scrollPanelWrapper, BoxLayout.PAGE_AXIS));
    
   
    scrollPanelWrapper.add(GuiUtils.addLabel ("UID Mapping File Variables Statistics"));
    
    scrollPanelWrapper.add(scrollPane);
    
    
    CommentListInterface comments = new CommentListImpl (mappingFile.getURI(),new CommentsJsonLdProcessor());
	
    JPanel panelWrapper = new JPanel ();
    panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.PAGE_AXIS));
	
    panelWrapper.add(new JLabel ("Comments"));
    
    panelWrapper.add(comments.getCommentList());
    
    panel.add(panelWrapper, BorderLayout.EAST);
   
    
    panel.add(scrollPanelWrapper, BorderLayout.CENTER);
    
    
    
    add(panel);
    setVisible(true);
    
}


}
