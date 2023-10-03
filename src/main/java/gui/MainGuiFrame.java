package gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.HyperlinkEvent;

import Utils.GuiUtils;
import Utils.IconTextItem;
import Utils.ValidationUtils;
import guiComponentImpl.ActivityListImpl;
import guiInterface.ActivityListInterface;
import semantic.parser.Activity;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Constants;
import semantic.parser.Entity;
import semantic.parser.JsonLdProcessor;
import validation.CheckForSensitiveVariablesInFile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

public class MainGuiFrame extends JFrame {
	
	JsonLdProcessor dataProcessor;
	CommentsJsonLdProcessor commentsJsonLdProcessor; 

    public MainGuiFrame() {
    	setTitle("Provenance Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1000);
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
        topPanel.add(GuiUtils.wrapTextWithLabel("Project Details:","This is an example project with dummy data for testing.", Color.BLACK));
        JButton inspect = new JButton ("Inspect");
        inspect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LinkagePlanFrame ();
            }
        });
        
        JPanel activityViewer = new JPanel(new BorderLayout ());

       // activityViewer.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        commentsJsonLdProcessor = new CommentsJsonLdProcessor ();
        
        ActivityListInterface listComp = new ActivityListImpl (activityViewer, commentsJsonLdProcessor);
        JPanel listPanel = listComp.getActivityList();
       
        
  
        
        topPanel.add(GuiUtils.wrapButtonWithLabelNoColor("Variable Specification:", inspect ));
        
        JsonLdProcessor dataProcessor = new JsonLdProcessor ();     
        ArrayList<HashMap<String, String>> activityIRIs = dataProcessor.getReleasedFilesActivityIRI(); 
        
        if (activityIRIs.size()<1) {
        	 topPanel.add(GuiUtils.wrapTextWithLabel("Released Files:","No files released", Color.BLACK));
        }
        else {
        	
        	
        	ArrayList <Entity> allReleased = new ArrayList <Entity> ();
        	
        	Enumeration <IconTextItem> activities = listComp.getListModel().elements();
        	while (activities.hasMoreElements()) {
                Activity activity = activities.nextElement().getActivity();
                
                String activityIRI = activity.getURI();
                
                for (HashMap<String, String> activityIRIMap : activityIRIs) {
                    String iriFromHashMap = activityIRIMap.get("activity");
                    if (activityIRI.equals(iriFromHashMap)) {
                        
                        allReleased.addAll(activity.getOutputs());
                    }
            }
               
        }
        	 topPanel.add(ValidationUtils.entityResultNoColor("Released Files ("+allReleased.size()+")", allReleased));
         	
        }
        
       
        topPanel.add(new JSeparator(JSeparator.HORIZONTAL),
	            BorderLayout.LINE_START);
        appPanel.add(topPanel, BorderLayout.NORTH);
        
        
            
        
       
        
        JPanel activityListPanel = new JPanel();
       
      
        activityListPanel.setLayout(new BoxLayout(activityListPanel, BoxLayout.Y_AXIS));
        
        
        appPanel.add(listPanel, BorderLayout.WEST);
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
        	Constants.PROVENANCE_DIRECTORY = directory;
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
