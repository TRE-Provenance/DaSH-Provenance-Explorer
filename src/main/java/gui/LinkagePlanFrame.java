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
import javax.swing.JTable;

import Utils.GuiUtils;
import guiComponentImpl.CommentListImpl;
import guiInterface.CommentListInterface;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.JsonLdProcessor;
import semantic.parser.LinkagePlan;
import validation.ValidationEngine;
import validation.ValidationRuleInterface;

public class LinkagePlanFrame extends JFrame {
	
public 	LinkagePlanFrame () {
	setTitle ("Data Linkage Plan");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(800, 800);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("Linkage Plan Details"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
  //  ArrayList<HashMap<String, String>> summaryStats = dataProcessor.getSummaryStatsForFile(dataset.getURI());
    
	 ArrayList<HashMap<String, String>> result = dataProcessor.getLinkagePlanDetails ();
	 
	 LinkagePlan plan = new LinkagePlan (result.get(0).get("linkagePlan"));
	 plan.setDescription(result.get(0).get("description"));
	
    mainInfo.add(GuiUtils.wrapTextWithLabel ("Description: ", plan.getDescription(),null));
   
    mainInfo.add(GuiUtils.addLabel ("Validations"));
String [] args = {};
	
	ValidationEngine engine = new ValidationEngine () ; 
	
	ArrayList<ValidationRuleInterface> validations = engine.getSettings().get("https://w3id.org/shp#DataLinkagePlan");
	
	
    if (validations!=null) {	
	for (int i=0; i<validations.size();i++) {
		
		mainInfo.add( validations.get(i).getSimpleResult (args, dataProcessor.getModel()));
		
	}
    }
    
    
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
    
    Object[][] rowData = new Object[variables.size()][NUMB_STATS_DISPLAYED];
    
    System.out.println(rowData);
   
    	
    	for (int j = 0 ; j < variables.size(); j ++ ) {
    		
    		rowData [j][0] = GuiUtils.formatDataType(variables.get(j).get("sourceL"));
    		rowData [j][1] = GuiUtils.formatDataType(variables.get(j).get("variableL"));
    		rowData [j][2] = (variables.get(j).get("minValue") != null) ? GuiUtils.formatDataType(variables.get(j).get("minValue")) : "no constraint set";
    		rowData [j][3] = (variables.get(j).get("maxValue") != null) ? GuiUtils.formatDataType(variables.get(j).get("maxValue")) : "no constraint set";
    		
    	}	
   	   
    
	
    JTable table = new JTable(rowData, columnNames);
    table.setEnabled(false);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(600, 200));
    
CommentListInterface comments = new CommentListImpl (plan.getURI(),new CommentsJsonLdProcessor());
	
    JPanel panelWrapper = new JPanel ();
    panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.PAGE_AXIS));
	
    panelWrapper.add(GuiUtils.addLabel("Comments"));
    
    panelWrapper.add(comments.getCommentList());
    
    panel.add(panelWrapper, BorderLayout.SOUTH);
    
    panel.add(scrollPane, BorderLayout.CENTER);

    add(panel);
    setVisible(true);
    
}

}
