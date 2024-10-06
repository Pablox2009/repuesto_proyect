/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repuesto_controler;

import codigo.proCode;
import java.awt.Image;
import java.io.File;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.proModel;
import Conexion.conexion;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
/**
 *
 * @author coron
 */
public class Pro extends javax.swing.JFrame {
    private byte[] imagenBytes;
    conexion a = new conexion();
    Connection conect;
    
    proCode pc = new proCode();
    proModel pm = new proModel();
    private String rutaImagenSeleccionada;
    public Pro() {
        this.conect = a.conectar();
        initComponents();
       

        pc.marcas(marca_combo);
        marca_combo.addActionListener((actionEvt) -> {
            JComboBox comboBox = (JComboBox) actionEvt.getSource();
            Map<String, Integer> mapMarca = (Map<String, Integer>) comboBox.getClientProperty("mapMarca");
            String marca = (String) comboBox.getSelectedItem();
            pm.setId_marca(mapMarca.get(marca));
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        productos = new javax.swing.JTextField();
        precio = new javax.swing.JTextField();
        foto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        añadir_marca = new javax.swing.JButton();
        añadir_foto = new javax.swing.JButton();
        marca_combo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productosActionPerformed(evt);
            }
        });

        precio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioActionPerformed(evt);
            }
        });

        foto.setBackground(new java.awt.Color(153, 0, 153));
        foto.setText("           No selecciono una foto");

        jLabel2.setText("Producto:");

        jLabel3.setText("Marca:");

        jLabel4.setText("Precio:");

        jLabel5.setText("Foto: ");

        añadir_marca.setText("Añadir");
        añadir_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadir_marcaActionPerformed(evt);
            }
        });

        añadir_foto.setText("Añadir");
        añadir_foto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadir_fotoActionPerformed(evt);
            }
        });

        jButton1.setText("subir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(productos, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                                    .addComponent(precio))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(añadir_foto))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(marca_combo, 0, 237, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(añadir_marca)))
                .addGap(59, 59, 59))
            .addGroup(layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(añadir_marca)
                    .addComponent(marca_combo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(añadir_foto)
                    .addComponent(foto, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(42, 42, 42))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void precioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioActionPerformed

    private void productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productosActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_productosActionPerformed

   
    private void añadir_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_añadir_marcaActionPerformed
        // TODO add your handling code here:
        Marca a= new Marca();
        a.setVisible(true);
        dispose();
    }//GEN-LAST:event_añadir_marcaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Obtener los datos de los JTextField
        String nombreProducto = productos.getText().trim(); // Usar trim() para eliminar espacios en blanco
        String precioProducto = precio.getText().trim();

        // Verificar si los campos están vacíos
        if (nombreProducto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete el campo Nombre del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (precioProducto.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete el campo Precio.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (imagenBytes == null) {
        JOptionPane.showMessageDialog(null, "Por favor, seleccione una imagen para el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
        }

        try {

        if (conect != null) {
            String sql = "INSERT INTO productos (nombre_producto, precio, foto) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conect.prepareStatement(sql)) {
                ps.setString(1, nombreProducto);
                ps.setBigDecimal(2, new BigDecimal(precioProducto)); 
                if (imagenBytes != null) {
                    ps.setBytes(3, imagenBytes); // Establecer la imagen
                } else {
                    ps.setNull(3, java.sql.Types.BLOB); // Establecer NULL si no hay imagen
                }

                // Ejecutar la consulta
                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("¡Un nuevo producto fue insertado exitosamente!");
                }
            }
        } else {
            System.out.println("Error: La conexión a la base de datos no está establecida.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Error: El precio debe ser un número válido.");
  }
    
    
/*    
String nombreProducto = productos.getText().trim();
    String precioProducto = precio.getText().trim();

    // Validaciones...
    if (nombreProducto.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, complete el campo Nombre del producto.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (precioProducto.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, complete el campo Precio.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Guardar la ruta de la imagen en la base de datos
    try {
        if (conect != null) {
            String sql = "INSERT INTO productos (nombre_producto, precio, foto) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conect.prepareStatement(sql)) {
                ps.setString(1, nombreProducto);
                ps.setBigDecimal(2, new BigDecimal(precioProducto));
                ps.setString(3, rutaImagenSeleccionada); // Guardar la ruta de la imagen

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("¡Un nuevo producto fue insertado exitosamente!");
                }
            }
        } else {
            System.out.println("Error: La conexión a la base de datos no está establecida.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Error: El precio debe ser un número válido.");
    }

    */
    }//GEN-LAST:event_jButton1ActionPerformed
    
     
    private void añadir_fotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_añadir_fotoActionPerformed
                                         
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes JPG & PNG", "jpg", "png");
    fileChooser.setFileFilter(filter);

    int result = fileChooser.showOpenDialog(this); // Mostrar el diálogo para elegir imagen

    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String rutaImagen = selectedFile.getAbsolutePath();

        Path path = Paths.get(rutaImagen);
        try {
            imagenBytes = Files.readAllBytes(path); // Convertir la imagen a un array de bytes

            // Mostrar la imagen en el JLabel llamado "foto"
            ImageIcon icon = new ImageIcon(imagenBytes);
            
            // Redimensionar la imagen
            Image img = icon.getImage(); // Obtener la imagen
            Image resizedImg = img.getScaledInstance(foto.getWidth(), foto.getHeight(), Image.SCALE_SMOOTH); // Redimensionar
            icon = new ImageIcon(resizedImg); // Crear un nuevo ImageIcon con la imagen redimensionada

            foto.setText(""); // Limpiar texto si es necesario
            foto.setIcon(icon); // Asignar la imagen redimensionada al JLabel
        } catch (IOException ex) {
            Logger.getLogger(Pro.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        JOptionPane.showMessageDialog(null, "No se seleccionó ninguna imagen", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    if (imagenBytes != null) {
        System.out.println("Imagen cargada correctamente.");
    } else {
        System.out.println("No se cargó ninguna imagen.");
    }

/*
JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes JPG & PNG", "jpg", "png");
    fileChooser.setFileFilter(filter);

    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String rutaImagen = selectedFile.getAbsolutePath(); // Guardar la ruta de la imagen

        // Mostrar la imagen en el JLabel llamado "foto"
        ImageIcon icon = new ImageIcon(rutaImagen);
        foto.setText("");
        foto.setIcon(icon); // Asignar la imagen seleccionada al JLabel

        // Aquí puedes almacenar la ruta en una variable para usarla más tarde
        this.rutaImagenSeleccionada = rutaImagen; // Suponiendo que tienes una variable para almacenar la ruta
    } else {
        JOptionPane.showMessageDialog(null, "No se seleccionó ninguna imagen", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

*/
    }//GEN-LAST:event_añadir_fotoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton añadir_foto;
    private javax.swing.JButton añadir_marca;
    private javax.swing.JLabel foto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox<String> marca_combo;
    private javax.swing.JTextField precio;
    private javax.swing.JTextField productos;
    // End of variables declaration//GEN-END:variables
}
