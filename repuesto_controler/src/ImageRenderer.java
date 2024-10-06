//import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

public class ImageRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row) {
        if (value instanceof ImageIcon) {
            JLabel label = new JLabel((ImageIcon) value);
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        } /*else {
           // return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row);
        }*/
        return null;
    }
}
