package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
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
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;

public class DatasetFrame extends JFrame {
	
public 	DatasetFrame (Dataset dataset) {
	setTitle (dataset.getURI());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("Dataset Details"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
    ArrayList<HashMap<String, String>> summaryStats = dataProcessor.getSummaryStatsForFile(dataset.getURI());
    
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Description: ", summaryStats.get(0).get("description"),null));
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Row Count: ", summaryStats.get(0).get("rowCount"),null));
  
    mainInfo.add(new JSeparator(JSeparator.HORIZONTAL),
            BorderLayout.LINE_START);
    mainInfo.add(GuiUtils.addLabel ("Validations"));
    

    
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
    
   
    mainInfo.add(new JSeparator(JSeparator.HORIZONTAL),
            BorderLayout.LINE_START);
    
    mainInfo.add(GuiUtils.addLabel ("Dataset Variables Statistics"));
    panel.add(mainInfo, BorderLayout.NORTH);
    
    
    
   
    
    
    
    
    
    ArrayList<HashMap<String, String>> variables = dataProcessor.getVariablesInFile(dataset.getURI());
    //keep the first column empty
    
    //To do - load dynamically 
    Object[] columnNames = new Object [3];
    columnNames[0] = "";
    columnNames[1] = "minValue";
    columnNames[2] = "maxValue";
    
    
    
    
 
    
    
    ArrayList<HashMap<String, String>> variableStats = dataProcessor.getVariableStatsForFile(dataset.getURI());
    
    int NUMB_STATS_DISPLAYED = 2;
   
    Object[][] rowData = new Object[variables.size()][NUMB_STATS_DISPLAYED+1];
    
    
    
    rowData [0][1] = "minValue";
    rowData [0][2] = "maxValue";
     	
    	for (int j = 0 ; j < variableStats.size(); j ++ ) {
    		
    		rowData [j][0] = variableStats.get(j).get("variableL");
    		rowData [j][1] = variableStats.get(j).get("minValue");
    		rowData [j][2] = variableStats.get(j).get("maxValue");
    	}	
    	   

    JTable table = new JTable(rowData, columnNames);
    table.setEnabled(false);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(600, 200));
    
    
    
    panel.add(scrollPane, BorderLayout.CENTER);

    add(panel);
    setVisible(true);
    
}

}
