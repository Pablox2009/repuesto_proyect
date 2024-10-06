/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repuesto_controler;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class RenderImagen extends DefaultTableCellRenderer {
    private static final int IMG_SIZE = 100; // Tamaño cuadrado de la imagen (500x500 píxeles)

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel();

        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;

            // Redimensionar la imagen a un tamaño cuadrado de 500x500
            Image img = icon.getImage();
            Image imgScaled = img.getScaledInstance(IMG_SIZE, IMG_SIZE, Image.SCALE_SMOOTH);

            // Aplicar la imagen redimensionada al JLabel
            label.setIcon(new ImageIcon(imgScaled));
            label.setText(null); // Sin texto
        } else {
            label.setText(value != null ? value.toString() : ""); // Mostrar texto si no es una imagen
            label.setIcon(null); // No mostrar icono
        }

        label.setHorizontalAlignment(SwingConstants.CENTER); // Centrar la imagen
        label.setVerticalAlignment(SwingConstants.CENTER);

        return label;
    }
}
