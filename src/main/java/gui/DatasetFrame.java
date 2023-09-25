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
import semantic.parser.Dataset;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;
import validation.ValidationEngine;
import validation.ValidationRuleInterface;

public class DatasetFrame extends JFrame {
	
	private CommentsJsonLdProcessor commentsJsonLdProcessor;
	
public 	DatasetFrame (Dataset dataset,CommentsJsonLdProcessor commentsJsonLdProcessor) {
	
	this.commentsJsonLdProcessor  = commentsJsonLdProcessor; 
	
	setTitle (dataset.getURI());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(1200, 600);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("Dataset Details"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
    ArrayList<HashMap<String, String>> summaryStats = dataProcessor.getSummaryStatsForFile(dataset.getURI());
    
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Description: ", summaryStats.get(0).get("description"),null));
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Path: ", dataset.getPath(),null));
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Row Count: ", summaryStats.get(0).get("rowCount"),null));
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Unique CHIs: ", summaryStats.get(0).get("uniqueChi"),null));
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Male/Female ratio: ", summaryStats.get(0).get("ratio"),null));
    
   
  
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
    
    
    String [] args = {dataset.getURI()};
	
	ValidationEngine engine = new ValidationEngine () ; 
	
	ArrayList<ValidationRuleInterface> validations = engine.getSettings().get("https://w3id.org/shp#DataSet");
	
	
    if (validations!=null) {	
	for (int i=0; i<validations.size();i++) {
		
		mainInfo.add( validations.get(i).getSimpleResult (args, dataProcessor.getModel()));
		
	}
    }
   
    mainInfo.add(new JSeparator(JSeparator.HORIZONTAL),
            BorderLayout.LINE_START);
    
   
    panel.add(mainInfo, BorderLayout.NORTH);
    
    
    
   
    
    
    
    
    
    ArrayList<HashMap<String, String>> variables = dataProcessor.getVariablesInFile(dataset.getURI());
    //keep the first column empty
    
    //To do - load dynamically 
    Object[] columnNames = new Object [7];
    columnNames[0] = "";
    columnNames[1] = "Data Type";
    columnNames[2] = "Min Value";
    columnNames[3] = "Max Value";
    columnNames[4] = "Smallest Distinct Numb";
    columnNames[5] = "Complete";
    columnNames[6] = "Complete (%)";
    
    
    
    
 
    
    
    ArrayList<HashMap<String, String>> variableStats = dataProcessor.getVariableStatsForFile(dataset.getURI());
    
    int NUMB_STATS_DISPLAYED = 6;
   
    Object[][] rowData = new Object[variables.size()][NUMB_STATS_DISPLAYED+1];
    
    
    
     	
    	for (int j = 0 ; j < variableStats.size(); j ++ ) {
    		
    		rowData [j][0] = GuiUtils.formatDataType(variableStats.get(j).get("variableL"));
    		rowData [j][1] = GuiUtils.formatDataType(variableStats.get(j).get("dataType"));
    		rowData [j][2] = GuiUtils.formatDataType(variableStats.get(j).get("minValue"));
    		rowData [j][3] = GuiUtils.formatDataType(variableStats.get(j).get("maxValue"));
    		rowData [j][4] = GuiUtils.formatDataType(variableStats.get(j).get("sdn"));
    		rowData [j][5] = GuiUtils.formatDataType( variableStats.get(j).get("complete"));
    		rowData [j][6] = GuiUtils.formatDataType(variableStats.get(j).get("completePct"));
    		
    		
    	}	
    	   

    JTable table = new JTable(rowData, columnNames);
    table.setEnabled(false);
    
    
 // Apply the custom renderer only to the "Percentage" column
    String columnName = "Complete (%)";
    int columnIndex = getColumnIndexByName(table, columnName);
    
    System.out.println("Column Index: " + columnIndex);
    
    if (columnIndex != -1) {
        table.getColumnModel().getColumn(columnIndex).setCellRenderer(new PercentageColumnRenderer());
    }
    
    
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(600, 200));
    
    
    JPanel scrollPanelWrapper = new JPanel ();
    scrollPanelWrapper.setLayout(new BoxLayout(scrollPanelWrapper, BoxLayout.PAGE_AXIS));
    
   
    scrollPanelWrapper.add(GuiUtils.addLabel ("Dataset Variables Statistics"));
    
    scrollPanelWrapper.add(scrollPane);
    
    
    CommentListInterface comments = new CommentListImpl (dataset.getURI(),new CommentsJsonLdProcessor());
	
    JPanel panelWrapper = new JPanel ();
    panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.PAGE_AXIS));
	
    panelWrapper.add(new JLabel ("Comments"));
    
    panelWrapper.add(comments.getCommentList());
    
    panel.add(panelWrapper, BorderLayout.EAST);
   
    
    panel.add(scrollPanelWrapper, BorderLayout.CENTER);
    
    
    
    add(panel);
    setVisible(true);
    
}

static class PercentageColumnRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        try {
        double valueInt = Double.parseDouble(value.toString());
        JLabel label = new JLabel (value.toString());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(getColorForPercentage (valueInt));
        
        return label;
        
        }
        catch (NumberFormatException ex) {
        	return component;
        }

        
    }

    private Color getColorForPercentage(double percentage) {
        // Customize the color based on the percentage as needed
        if (percentage < 50) {
            return Color.RED;
        } else if (percentage < 100) {
            return Color.YELLOW;
        } else {
            return Color.GREEN;
        }
    }
}

private static int getColumnIndexByName(JTable table, String columnName) {
    for (int i = 0; i < table.getColumnCount(); i++) {
        if (table.getColumnName(i).equals(columnName)) {
            return i;
        }
    }
    return -1; // Column not found
}







}
