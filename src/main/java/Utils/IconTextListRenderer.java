package Utils;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class IconTextListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
    	JPanel panel = new JPanel(new BorderLayout());

        if (value instanceof IconTextItem) {
            IconTextItem item = (IconTextItem) value;

            // Create a label for the text
            JLabel textLabel = new JLabel(item.getText());

            // Set the icon on the left side of the text
            if (item.getIcon() != null) {
                JLabel iconLabel = new JLabel(item.getIcon());
                iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                panel.add(iconLabel, BorderLayout.EAST);
            }
            
            panel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
            panel.add(textLabel, BorderLayout.CENTER);

            // Set the background and selection color
            if (isSelected) {
                panel.setBackground(list.getSelectionBackground());
                panel.setForeground(list.getSelectionForeground());
            } else {
                panel.setBackground(list.getBackground());
                panel.setForeground(list.getForeground());
            }

            panel.setEnabled(list.isEnabled());
            panel.setFont(list.getFont());
            panel.setOpaque(true);
        }

        return panel;
    }
}
