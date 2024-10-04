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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;

/**
 *
 * @author Usuario
 */
public class fomularioCode {
    public void clientes(HashMap<String, String> codigoc){    
        String mostrar = "SELECT * FROM cliente WHERE borrado = 1";
        try{
            Connection con = conexion.conectar();
            PreparedStatement ps = con.prepareStatement(mostrar);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                codigoc.put(codigo, nombre);
            }
        }
        catch(SQLException err){
            err.printStackTrace();
        }
    }
}
