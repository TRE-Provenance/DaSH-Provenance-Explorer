package gui;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

import Utils.GuiUtils;
import Utils.ValidationUtils;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ClickableIFrame extends JFrame {
	
	JsonLdProcessor dataProcessor;

    public ClickableIFrame() {
    	setTitle("Provenance Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);

        
        JPanel appPanel = new JPanel(new BorderLayout());
        appPanel.add(new JLabel("Project Details:"), BorderLayout.NORTH);
        
      
        
        
        JPanel activityListPanel = new JPanel();
       
      
        activityListPanel.setLayout(new BoxLayout(activityListPanel, BoxLayout.Y_AXIS));
       
         dataProcessor = new JsonLdProcessor ();
        
        ArrayList<HashMap<String, String>> list = dataProcessor.getActivityData();

        HashMap <String,JPanel> panelMap = new HashMap <String,JPanel> ();
       
		for (int i=0;i< list.size();i++) {
			if (panelMap.containsKey(list.get(i).get("activity"))) {
				//complete adding new buttons if more than one output
			}
			else {
		    JPanel main = new JPanel(new BorderLayout());
		    main.setBackground(Color.WHITE);
		    main.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		    
		    System.out.println ("adding");
		    JPanel activityPanel = new JPanel ();
		    activityPanel.setLayout(new BoxLayout(activityPanel, BoxLayout.Y_AXIS));
		   
		    
		    
		    
		    activityPanel.add(GuiUtils.wrapValueWithLabel("Activity: ",list.get(i).get("activityL"),null));
		    activityPanel.add(GuiUtils.wrapValueWithLabel("Responsible Person: ",list.get(i).get("agent"),null));
		    
		    activityPanel.add(new JSeparator(JSeparator.HORIZONTAL),
		            BorderLayout.LINE_START);

		    main.add(activityPanel, BorderLayout.CENTER);
		    
		    
		    //validations 
		    
		    
		    JPanel fileValidation = new JPanel();
            fileValidation.setLayout(new BoxLayout(fileValidation, BoxLayout.Y_AXIS));
            fileValidation.add (new JLabel ("Validation Checks"));
            JPanel fileValidationWrapper = new JPanel();
            fileValidationWrapper.add(new JSeparator(JSeparator.VERTICAL),
		            BorderLayout.BEFORE_FIRST_LINE);
            fileValidationWrapper.add(fileValidation);
            main.add(fileValidationWrapper, BorderLayout.EAST);
            
            
            ArrayList<HashMap<String, String>> sensitiveVariablesFound =  CheckForSensitiveVariablesInFile.checkFile(list.get(i).get("activity"), dataProcessor.getModel());
        

         
            ValidationUtils.simpleResult("Number of Rows input/output", fileValidation, "not implemented");
            ValidationUtils.simpleResult("What Else", fileValidation, "not implemented");
		    
		    //inputs/outputs
		    
		    
		    
		    
		    GridLayout layoutButtons = new GridLayout(2, 2); 
		    layoutButtons.setHgap(2);
		    layoutButtons.setVgap(2);
		    
		    JPanel buttonsPanel = new JPanel();
		    buttonsPanel.setLayout(layoutButtons);
		    
		    JLabel inputLabel = new JLabel ("inputs:");
		   
		    
		    buttonsPanel.add(inputLabel);
		    
		    
		    JButton  input = createClickableElement(list.get(i).get("inputL"));
            input.addMouseListener(new ClickListener(list.get(i).get("input")));
            buttonsPanel.add(input);
            
            JLabel outputLabel = new JLabel ("outputs:");
            
            buttonsPanel.add(outputLabel);
            
            JButton  output = createClickableElement(list.get(i).get("outputL"));
            output.addMouseListener(new ClickListener(list.get(i).get("output")));
            buttonsPanel.add(output);
            
            main.add(buttonsPanel, BorderLayout.SOUTH);
            
            panelMap.put(list.get(i).get("activity"), main);
			}
            
        }
        
		for (String key : panelMap.keySet()) {
        System.out.println ("adding");
            activityListPanel.add(panelMap.get(key));
        }
        
		

        
      
		JScrollPane activityListScrolPane = new JScrollPane (activityListPanel);
		 // activityListScrolPane.add(activityListPanel);
		appPanel.add(activityListScrolPane,BorderLayout.CENTER);

        getContentPane().add(appPanel);
    }
    
    
    private JButton createClickableElement(String text) {
    	JButton panel = new JButton(text);
        //panel.setBackground(Color.WHITE);
        //panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.setPreferredSize(new Dimension(120, 40));

        //JLabel label = new JLabel(text);
        //panel.add(label);

        return panel;
    }

    private class ClickListener extends MouseAdapter {
        private String fileIRI;
        

        public ClickListener(String fileIRI) {
        	
            this.fileIRI = fileIRI;
		}

		@Override
        public void mouseClicked(MouseEvent e) {
            showValueFrame(fileIRI);
        }

        private void showValueFrame(String fileIRI) {
            JFrame valueFrame = new JFrame(fileIRI);
            valueFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            valueFrame.setSize(600, 400);
            valueFrame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new BorderLayout());

            
            JPanel mainInfo = new JPanel();
            mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.Y_AXIS));
            mainInfo.setPreferredSize(new Dimension(800, 400));
            
            ArrayList<HashMap<String, String>> summaryStats = dataProcessor.getSummaryStatsForFile(fileIRI);
            
            mainInfo.add(GuiUtils.wrapValueWithLabel ("Description: ", summaryStats.get(0).get("description"),null));
            mainInfo.add(GuiUtils.wrapValueWithLabel ("Row Count: ", summaryStats.get(0).get("rowCount"),null));
            mainInfo.add (new JLabel ("Dataset Variables"));
            panel.add(mainInfo, BorderLayout.CENTER);
            
            
            //validations 
            
            JPanel fileValidation = new JPanel();
            fileValidation.setLayout(new BoxLayout(fileValidation, BoxLayout.Y_AXIS));
            fileValidation.add (new JLabel ("Validation Checks"));
            JPanel fileValidationWrapper = new JPanel();
            fileValidationWrapper.add(new JSeparator(JSeparator.VERTICAL),
		            BorderLayout.BEFORE_FIRST_LINE);
            fileValidationWrapper.add(fileValidation);
            panel.add(fileValidationWrapper, BorderLayout.EAST);
            
            
            ArrayList<HashMap<String, String>> sensitiveVariablesFound =  CheckForSensitiveVariablesInFile.checkFile(fileIRI, dataProcessor.getModel());
            String result = "";

            if (sensitiveVariablesFound.size()>0) {
            	
            	for (int i =0; i <sensitiveVariablesFound.size();i++ ) {
            		result = result + sensitiveVariablesFound.get(i).get("variableL");
            		
            		if (i+1!=sensitiveVariablesFound.size()) {
            			result = result +",";
                	}
            	}
            	
            }
            
            ValidationUtils.simpleResult("Sensitive Variables", fileValidation, result);
            ValidationUtils.simpleResult("Variable range  exceeds Spec", fileValidation, "not implemented");
            ValidationUtils.simpleResult("What Else", fileValidation, "not implemented");
           
            
            
            
            
            
            ArrayList<HashMap<String, String>> variables = dataProcessor.getVariablesInFile(fileIRI);
            //keep the first column empty
            Object[] columnNames = new Object [variables.size()+1];
            columnNames[0] = "";
            
            for (int i =0; i<variables.size();i++ ) {
            	columnNames[i+1] = variables.get(i).get("variableL");
            }
            
            
            ArrayList<HashMap<String, String>> variableStats = dataProcessor.getVariableStatsForFile(fileIRI);
            
            int NUMB_STATS_DISPLAYED = 2;
           
            Object[][] rowData = new Object[NUMB_STATS_DISPLAYED][variables.size()+1];
            
            
            
            rowData [0][0] = "minValue";
            rowData [1][0] = "maxValue";
             	
            	for (int j = 0 ; j < variableStats.size(); j ++ ) {
            		String variableLabel = variableStats.get(j).get("variableL");
            		System.out.println (variableLabel);
            		System.out.println (variableLabel);
            		int columnIndex = Arrays.asList(columnNames).indexOf(variableLabel) ;
            		rowData [0][columnIndex] = variableStats.get(j).get("minValue");
            		rowData [1][columnIndex] = variableStats.get(j).get("maxValue");
            	}	
            	   

            JTable table = new JTable(rowData, columnNames);
            table.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(600, 200));
            
            
            
            panel.add(scrollPane, BorderLayout.SOUTH);

            valueFrame.add(panel);
            valueFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	ClickableIFrame frame = new ClickableIFrame();
            frame.setVisible(true);
        });
    }
}
