package guiComponentImpl;

import javax.swing.*;

import guiInterface.CommentListInterface;
import semantic.parser.Comment;
import semantic.parser.CommentsJsonLdProcessor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentListImpl implements CommentListInterface {
    private static DefaultListModel<Comment> commentListModel = new DefaultListModel<>();
    private static JList<Comment> commentList = new JList<>(commentListModel);
    JPanel mainPanel;
    private  CommentsJsonLdProcessor commentsJsonLdProcessor;
    private String resourceIRI;

    public CommentListImpl (String resourceIRI, CommentsJsonLdProcessor commentsJsonLdProcessor) {
    	commentList.setCellRenderer(new CommentRenderer());
        this.commentsJsonLdProcessor = commentsJsonLdProcessor;
        this.resourceIRI = resourceIRI;
    	
        JButton addButton = new JButton("+");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddCommentDialog();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        
        

        mainPanel = new JPanel(new BorderLayout());
        JScrollPane jscrollPane =  new JScrollPane(commentList);
        Dimension maxScrollPaneSize = new Dimension(300, 150);
        jscrollPane.getViewport().setPreferredSize(maxScrollPaneSize);
        mainPanel.add(jscrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        
        
        readComments ();
    }
    
    private void readComments () {
    	ArrayList <Comment> comments = commentsJsonLdProcessor.getComments(resourceIRI);
    	
    	commentListModel.removeAllElements();
    	
    	commentListModel.addAll(comments);
    	
    	
    }
    
    

    private  void showAddCommentDialog() {
        JTextField authorField = new JTextField();
        JTextArea commentArea = new JTextArea(4, 20);
        commentArea.setLineWrap(true);

        JPanel inputPanel = new JPanel(new GridLayout(0, 1));
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Comment:"));
        inputPanel.add(new JScrollPane(commentArea));

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Add Comment",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String author = authorField.getText().trim();
            String text = commentArea.getText().trim();

            if (!author.isEmpty() && !text.isEmpty()) {
            	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Comment newComment = new Comment(author, dateFormat.format(new Date()), text);
                commentsJsonLdProcessor.saveCommentToModel(resourceIRI, newComment);
                //commentListModel.addElement(newComment);
                readComments ();
            } else {
                JOptionPane.showMessageDialog(null, "Please provide both author and comment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    

    private static class CommentRenderer extends JPanel implements ListCellRenderer<Comment> {
        private JLabel authorTimeLabel = new JLabel();
        private JLabel textLabel = new JLabel();
        JLabel authorNameLabel = new JLabel();
        JLabel timeLabel = new JLabel();

        public CommentRenderer() {
            setLayout(new BorderLayout());
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Left-aligned flow layout with horizontal and vertical gaps

            // Create an author icon
            ImageIcon authorIcon = new ImageIcon(CommentListImpl.class.getClassLoader().getResource("author.png")); // Replace with your actual icon file
            JLabel authorLabel = new JLabel(authorIcon);

           
            // Create a clock icon
            ImageIcon clockIcon = new ImageIcon(CommentListImpl.class.getClassLoader().getResource("clock.png")); // Replace with your actual icon file
            JLabel clockLabel = new JLabel(clockIcon);

            // Add components to the panel
            panel.add(authorLabel);
            panel.add(authorNameLabel);
            panel.add(clockLabel);
            panel.add(timeLabel);
            add(panel, BorderLayout.NORTH);
            add(textLabel, BorderLayout.CENTER);

            Font boldFont = textLabel.getFont().deriveFont(Font.BOLD);
            authorTimeLabel.setFont(boldFont);
            textLabel.setFont(boldFont);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Comment> list, Comment value, int index, boolean isSelected, boolean cellHasFocus) {
            //authorTimeLabel.setText("<html><b>Author:</b> " + value.getAuthor() + " <b>Time:</b> " + value.getFormattedTimestamp() + "</html>");
        	authorNameLabel.setText(value.getAuthor());
        	timeLabel.setText(value.getFormattedTimestamp());
        	textLabel.setText("<html><body>" + value.getText() + "</body></html>");

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            setOpaque(true);
            return this;
        }
    }

	@Override
	public JPanel getCommentList() {
		
		return mainPanel;
	}
}
