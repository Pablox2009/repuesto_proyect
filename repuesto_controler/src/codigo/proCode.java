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
public class proCode {
    public void marcas(JComboBox combo){    
        Map<String, Integer> mapMarcas = new HashMap<>();
        String mostrar = "SELECT * FROM marca ORDER BY nombre ASC";
        combo.addItem("Seleccionar");
        mapMarcas.put("Seleccionar",0);
        try{
            Connection con = conexion.conectar();
            PreparedStatement ps = con.prepareStatement(mostrar);
            ResultSet rs = ps.executeQuery(mostrar);
            while(rs.next()){
                String nombre = rs.getString("nombre");
                int idMarca = rs.getInt("id_marca");
                combo.addItem(nombre);
                mapMarcas.put(nombre, idMarca);
            }
        }
        catch(SQLException err){
            err.printStackTrace();
        }
        combo.putClientProperty("mapMarca", mapMarcas);
    }
}
