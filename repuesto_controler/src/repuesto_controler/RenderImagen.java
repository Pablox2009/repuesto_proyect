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
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row) {
        
        // Verifica si el valor es una instancia de ImageIcon
        if (value instanceof ImageIcon) {
            ImageIcon icon = (ImageIcon) value;
            setIcon(icon); // Establece el icono para la celda
            setText(null); // Asegúrate de que no se muestre texto
        } else {
            setText(value != null ? value.toString() : ""); // Para otras celdas
            setIcon(null); // No mostrar icono
        }
        
        // Cambiar el color de fondo si está seleccionado
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        return this;
    }
}
