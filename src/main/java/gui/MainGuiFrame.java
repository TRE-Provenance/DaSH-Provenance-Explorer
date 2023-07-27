package gui;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

import Utils.GuiUtils;
import Utils.ValidationUtils;
import guiComponentImpl.ActivityListImpl;
import guiInterface.ActivityListInterface;
import semantic.parser.Constants;
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

public class MainGuiFrame extends JFrame {
	
	JsonLdProcessor dataProcessor;

    public MainGuiFrame() {
    	setTitle("Provenance Report");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        openFile();
        
        JPanel appPanel = new JPanel(new BorderLayout());
         
      
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("App");
        menuBar.add(menu);
        
        JMenuItem aboutItem = new JMenuItem("About");
        menu.add(aboutItem);
        
        setJMenuBar(menuBar);
        topPanel.add(GuiUtils.wrapTextWithLabelNoColor("Project Details:","Project description goes here"));
        JButton inspect = new JButton ("Inspect");
        inspect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LinkagePlanFrame ();
            }
        });
        topPanel.add(GuiUtils.wrapButtonWithLabelNoColor("Variable Specification:", inspect ));
        topPanel.add(GuiUtils.wrapTextWithLabel("Released Files:","List of file names goes here?", Color.magenta));
        topPanel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
        appPanel.add(topPanel, BorderLayout.NORTH);
        
        JPanel activityViewer = new JPanel();
        activityViewer.setLayout(new FlowLayout(FlowLayout.LEFT));
            
        
        ActivityListInterface listComp = new ActivityListImpl (activityViewer);
        
        JPanel activityListPanel = new JPanel();
       
      
        activityListPanel.setLayout(new BoxLayout(activityListPanel, BoxLayout.Y_AXIS));
        //activityListPanel.add(activityViewer);
       // appPanel.add(activityListPanel, BorderLayout.CENTER);
        
        /*
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
		   
		    
		    
		    
		    activityPanel.add(GuiUtils.wrapTextWithLabel("Activity: ",list.get(i).get("activityL"),null));
		    activityPanel.add(GuiUtils.wrapTextWithLabel("Responsible Person: ",list.get(i).get("agent"),null));
		    
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
		appCenter.add(activityListScrolPane, BorderLayout.CENTER);
		appPanel.add(appCenter,BorderLayout.CENTER);
		*/
        
        appPanel.add(listComp.getActivityList(), BorderLayout.WEST);
        appPanel.add(activityViewer, BorderLayout.CENTER);

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

    private void openFile() {
    	FileDialog fileDialog = new FileDialog(this, "Open File", FileDialog.LOAD);
        fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".jsonld")); // Optional: Filter only .txt files
        
        fileDialog.setVisible(true);
        
        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();
        
        if (directory != null && filename != null) {
        	Constants.PROVENANCE_FILE = directory + filename;
        	//Constants.PROVENANCE_FILE = fileChooser.getSelectedFile().getPath();
            
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	MainGuiFrame frame = new MainGuiFrame();
            frame.setVisible(true);
        });
    }
}
