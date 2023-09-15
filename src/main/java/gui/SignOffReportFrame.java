package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box.Filler;

import Utils.GuiUtils;
import guiComponentImpl.CommentListImpl;
import guiInterface.CommentListInterface;
import semantic.parser.CommentsJsonLdProcessor;
import semantic.parser.Database;
import semantic.parser.JsonLdProcessor;
import semantic.parser.SignOffReport;

public class SignOffReportFrame extends JFrame {
	
	
public 	SignOffReportFrame (SignOffReport report) {
	
	
	
	setTitle (report.getURI());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(800, 500);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel(new BorderLayout());

    
    JPanel mainInfo = new JPanel();
 
    
    mainInfo.setLayout(new BoxLayout(mainInfo, BoxLayout.PAGE_AXIS));
    mainInfo.add(GuiUtils.addLabel ("SignOff Report"));
	JsonLdProcessor dataProcessor = new JsonLdProcessor ();
    ArrayList<HashMap<String, String>> signOffDetails = dataProcessor.getDatabaseDetails(report.getURI());
   
    if (signOffDetails.size()>1) {
    	 mainInfo.add(GuiUtils.wrapTextWithLabel ("Error: ", "Provenance trace includes duplicate entries for the same signoffReport",Color.red));
    }
    else {
    report.setResult(signOffDetails.get(0).get("result"));
   
    mainInfo.add(GuiUtils.wrapTextWithLabelNoColor ("Result: ", report.getResult() ));
    
   
    
    Box.Filler glue = (Filler) Box.createVerticalGlue();
    glue.changeShape(glue.getMinimumSize(), 
                    new Dimension(0, Short.MAX_VALUE), // make glue greedy
                    glue.getMaximumSize());
    
    
    
    mainInfo.add(glue);
    
    }
    
   
    

    
    
   
    
   
    panel.add(mainInfo, BorderLayout.CENTER);
    
    
    
    
    CommentListInterface comments = new CommentListImpl (report.getURI(),new CommentsJsonLdProcessor());
	
    JPanel panelWrapper = new JPanel ();
    panelWrapper.setLayout(new BoxLayout(panelWrapper, BoxLayout.PAGE_AXIS));
	
    panelWrapper.add(new JLabel ("Comments"));
    
    panelWrapper.add(comments.getCommentList());
    
    panel.add(panelWrapper, BorderLayout.EAST);
   
    
  
    
    
    
    add(panel);
    setVisible(true);
    
}

}
