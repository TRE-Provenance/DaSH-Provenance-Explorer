package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Utils.GuiUtils;
import semantic.parser.JsonLdProcessor;
import semantic.parser.LinkagePlan;

public class LinkagePlanFrame extends JFrame {
	
public 	LinkagePlanFrame () {
	setTitle ("Data Linkage Plan");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("Linkage Plan Details"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
  //  ArrayList<HashMap<String, String>> summaryStats = dataProcessor.getSummaryStatsForFile(dataset.getURI());
    
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Description: ", "not yet implemented",null));
   
    
    mainInfo.add(GuiUtils.addLabel ("Requested Variables"));
    panel.add(mainInfo, BorderLayout.NORTH);
    

    
    ArrayList<HashMap<String, String>> variables = dataProcessor.getVariablesInPlan();
    //keep the first column empty
    System.out.println(variables.size());
    //To do - load dynamically 
    Object[] columnNames = new Object [4];
    columnNames[0] = "Data Source";
    columnNames[1] = "Variable";
    columnNames[2] = "Min Value";
    columnNames[3] = "Max Value";
    
 
    int NUMB_STATS_DISPLAYED = 4;
   
   // Object[][] rowData = new Object[variables.size()][NUMB_STATS_DISPLAYED];
    
    Object[][] rowData = new Object[1][NUMB_STATS_DISPLAYED];
    
    System.out.println(rowData);
   
    	
    	for (int j = 0 ; j < variables.size(); j ++ ) {
    		
    		rowData [j][0] = variables.get(j).get("sourceL");
    		rowData [j][1] = variables.get(j).get("variableL");
    		rowData [0][2] = "not implemented";
    		rowData [0][3] = "not implemented";
    		
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
