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
public class clienteCode {
    private String codigo;
    private String nombre;
    private String idC;
    
    public DefaultTableModel clientes(){
        String[] columnas = {"id","Código","Nombre"};
        String[] clientes = new String[3];
        DefaultTableModel model = new DefaultTableModel(null,columnas);
        String select = "SELECT * FROM cliente WHERE borrado = 1";
        try{
            Connection con = conexion.conectar();
            PreparedStatement pr = con.prepareStatement(select);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                clientes[0] = rs.getString("id");
                clientes[1] = rs.getString("codigo");
                clientes[2] = rs.getString("nombre");
                model.addRow(clientes);
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error de conexión");
        }
        
        return model;
    }
    
    public void agregar(String codigoC, String nombreC){
        this.nombre = nombreC;
        this.codigo = codigoC;
        String insert = "INSERT INTO cliente (codigo,nombre) VALUES (?,?)";
        String verificar = "SELECT * FROM cliente WHERE codigo = ? AND nombre = ?";
        try{
            Connection con = conexion.conectar();
            PreparedStatement ps = con.prepareStatement(verificar);
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Existen coincidencias con otro cliente","Error",JOptionPane.WARNING_MESSAGE);
            }
            else{
                PreparedStatement psg = con.prepareStatement(insert);
                psg.setString(1,codigo);
                psg.setString(2, nombre);
                psg.executeUpdate();
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void modificar(String id, String codigoC, String nombreC){
        this.idC = id;
        this.codigo = codigoC;
        this.nombre = nombreC;
        String update = "UPDATE cliente SET codigo = ?, nombre = ? WHERE id = ?";
        try{
            Connection con = conexion.conectar();
            PreparedStatement ps = con.prepareStatement(update);
            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setString(3, idC);
            ps.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al modificar");
        }
    }
    
    public void eliminar(String id){
        this.idC = id;
        String delete = "UPDATE cliente SET borrado = 0 WHERE id = ?";
        try{
            Connection con = conexion.conectar();
            PreparedStatement ps = con.prepareStatement(delete);
            ps.setString(1,idC);
            ps.executeUpdate();
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al eliminar");
        }
    }
}
