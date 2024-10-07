/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repuesto_controler;

import Conexion.conexion;
import codigo.Imprimir;
import codigo.menuCode;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import modelo.menuModel;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class Menu_principal extends javax.swing.JFrame {

    menuCode mc = new menuCode();
    menuModel mm = new menuModel();
    Imprimir imp = new Imprimir();
    
    private String id = "";
    conexion a = new conexion();
    Connection conect;
    
    public Menu_principal() {
        initComponents();
        this.conect = a.conectar();
        repuestosCargados();
        centrar();
    }
    
    private void centrar(){
        Dimension tamaño = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (tamaño.width - this.getWidth()) / 2;
        int y = (tamaño.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }
    
    public void repuestosCargados(){
        DefaultTableModel model = mc.repuestos();
        tabla_repuestos.setModel(model);
        TableColumnModel columna= tabla_repuestos.getColumnModel();
        columna.getColumn(0).setMinWidth(0);
        columna.getColumn(0).setMaxWidth(0);
        columna.getColumn(1).setMinWidth(0);
        columna.getColumn(1).setMaxWidth(0);
        tabla_repuestos.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabla_repuestos.getColumnModel().getColumn(2).setPreferredWidth(103);
        tabla_repuestos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla_repuestos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla_repuestos.getColumnModel().getColumn(5).setCellRenderer(new RenderImagen());

        int fotoWidth = 200;
        tabla_repuestos.getColumnModel().getColumn(5).setPreferredWidth(fotoWidth);
        tabla_repuestos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla_repuestos.setRowHeight(100);
        tabla_repuestos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tabla_repuestos.getSelectedRow();
                if (fila != -1) {
                    mm.setId(tabla_repuestos.getValueAt(fila, 0).toString());
                    mm.setId_marca(Integer.parseInt(tabla_repuestos.getValueAt(fila,1).toString()));
                    mm.setMarca(tabla_repuestos.getValueAt(fila, 2).toString());
                    mm.setNombre(tabla_repuestos.getValueAt(fila, 3).toString());
                    mm.setPrecio(tabla_repuestos.getValueAt(fila, 4).toString());
                    id = mm.getId();
                }
            }
        });
    }
    
    public void buscarProducto(String buscador){
        DefaultTableModel model = mc.repuestosBuscar(buscador);
        tabla_repuestos.setModel(model);
        TableColumnModel columna= tabla_repuestos.getColumnModel();
        columna.getColumn(0).setMinWidth(0);
        columna.getColumn(0).setMaxWidth(0);
        columna.getColumn(1).setMinWidth(0);
        columna.getColumn(1).setMaxWidth(0);
        tabla_repuestos.getColumnModel().getColumn(0).setPreferredWidth(200);
        tabla_repuestos.getColumnModel().getColumn(2).setPreferredWidth(103);
        tabla_repuestos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla_repuestos.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla_repuestos.getColumnModel().getColumn(5).setCellRenderer(new RenderImagen());

        int fotoWidth = 200;
        tabla_repuestos.getColumnModel().getColumn(5).setPreferredWidth(fotoWidth);
        tabla_repuestos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla_repuestos.setRowHeight(100);
    }
    
    private void eliminarProducto() {
        int fila = tabla_repuestos.getSelectedRow();
        if (fila != -1) {
            String idProducto = tabla_repuestos.getValueAt(fila, 0).toString();

            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar este producto?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    String sql = "DELETE FROM productos WHERE id = ?";
                    PreparedStatement ps = conect.prepareStatement(sql);
                    ps.setString(1, idProducto);

                    int resultado = ps.executeUpdate();

                    if (resultado > 0) {
                        JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.");
                        repuestosCargados();

                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al eliminar el producto: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto para eliminar.");
        }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buscador = new javax.swing.JTextField();
        bot_añadir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_repuestos = new javax.swing.JTable();
        imprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        mod = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        boton_marca = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menú Principal");
        setResizable(false);

        buscador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscadorActionPerformed(evt);
            }
        });
        buscador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscadorKeyReleased(evt);
            }
        });

        bot_añadir.setBackground(new java.awt.Color(51, 255, 51));
        bot_añadir.setText("Añadir");
        bot_añadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bot_añadirActionPerformed(evt);
            }
        });

        tabla_repuestos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tabla_repuestos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_repuestosMouseClicked(evt);
            }
        });
        tabla_repuestos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tabla_repuestosKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_repuestos);

        imprimir.setBackground(new java.awt.Color(51, 255, 51));
        imprimir.setText("Imprimir");
        imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imprimirActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar");

        mod.setBackground(new java.awt.Color(255, 255, 102));
        mod.setText("Modificar");
        mod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 51, 51));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Eliminar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        boton_marca.setBackground(new java.awt.Color(51, 255, 51));
        boton_marca.setText("Marcas");
        boton_marca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_marcaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(imprimir))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bot_añadir)
                        .addGap(18, 18, 18)
                        .addComponent(mod)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(boton_marca)
                        .addGap(83, 83, 83)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bot_añadir)
                    .addComponent(mod)
                    .addComponent(jButton2)
                    .addComponent(boton_marca))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(imprimir)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void buscadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscadorActionPerformed
        
    }//GEN-LAST:event_buscadorActionPerformed

    private void bot_añadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bot_añadirActionPerformed
        Pro pr = new Pro(this);
        pr.setVisible(true);
        pr.modos(0);
    }//GEN-LAST:event_bot_añadirActionPerformed

    private void modActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modActionPerformed
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null,"No seleccionó nada","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            Pro pr = new Pro(this);
            String nombre = mm.getNombre();
            String idp = mm.getId();
            String marca = mm.getMarca();
            String precio = mm.getPrecio();
            int idM = mm.getId_marca();
            pr.setVisible(true);
            pr.modos(1);
            pr.datos(idp, nombre, marca, idM, precio);
            id = "";
        }
    }//GEN-LAST:event_modActionPerformed

    private void tabla_repuestosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_repuestosKeyTyped
        // TODO add your handling code here:
      

    }//GEN-LAST:event_tabla_repuestosKeyTyped

    private void tabla_repuestosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_repuestosMouseClicked
      // tabla_repuestos.setModel(modelo); // Actualizar el modelo de la tabla

    }//GEN-LAST:event_tabla_repuestosMouseClicked

    private void imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imprimirActionPerformed
        if(id.isEmpty()){
            JOptionPane.showMessageDialog(null,"No seleccionó nada","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            int[] selectedRows = tabla_repuestos.getSelectedRows();
            List<String> idsSeleccionados = new ArrayList<>();
            for (int rowIndex : selectedRows) {
                String id = tabla_repuestos.getValueAt(rowIndex, 0).toString();
                idsSeleccionados.add(id);
            }
            imp.imprimirP(idsSeleccionados);
            id = "";
        }
    }//GEN-LAST:event_imprimirActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        eliminarProducto();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void buscadorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buscadorKeyReleased
        buscarProducto(buscador.getText());
    }//GEN-LAST:event_buscadorKeyReleased

    private void boton_marcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_marcaActionPerformed
        Marca ma = new Marca();
        ma.setVisible(true);
    }//GEN-LAST:event_boton_marcaActionPerformed



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
            java.util.logging.Logger.getLogger(Menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bot_añadir;
    private javax.swing.JButton boton_marca;
    private javax.swing.JTextField buscador;
    private javax.swing.JButton imprimir;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton mod;
    private javax.swing.JTable tabla_repuestos;
    // End of variables declaration//GEN-END:variables
}
