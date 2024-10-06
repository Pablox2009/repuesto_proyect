/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

import Conexion.conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Image;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class menuCode {
    
    private String id;
    
    public DefaultTableModel repuestos(){
        String []columnas = {"id","id_marca","Marca", "Nombre", "Precio", "Imagen"};
        Object []registros = new Object[6];
        String select = "SELECT *,ma.nombre FROM productos as pr inner join marca as ma on pr.marca = ma.id_marca WHERE pr.borrado = 1";
        DefaultTableModel model = new DefaultTableModel(null,columnas);
        try{
            Connection con = conexion.conectar();
            PreparedStatement ps = con.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("ma.id_marca");
                registros[2] = rs.getString("ma.nombre");
                registros[3] = rs.getString("nombre_producto");
                registros[4] = rs.getString("precio");
                
                byte[] fotoBytes = rs.getBytes("foto");
                ImageIcon imageIcon = new ImageIcon(fotoBytes);
                Image img = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                registros[5]= new ImageIcon(img); 
                
                model.addRow(registros);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }
}
