/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

import Conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class menuCode {
    public  DefaultTableModel repuestos(){
        String []columnas = {"id", "Marca", "Nombre", "Precio"};
        String []registros = new String[5];
        String select = "SELECT * FROM repuestos WHERE borrado = 1";
        DefaultTableModel model = new DefaultTableModel(null,columnas);
        try{
            Connection con = conexion.conectar();
            PreparedStatement ps = con.prepareStatement(select);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                registros[0] = rs.getString("id");
                registros[1] = rs.getString("ma.id_marca");
                registros[2] = rs.getString("ma.nombre");
                registros[3] = rs.getString("nombre");
                registros[4] = rs.getString("precio");
                model.addRow(registros);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }
}
