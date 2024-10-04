/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class clienteModelo {
    private String codigo_cliente;
    private String nombre_cliente;
    private String id_cliente;
    
    /*public clienteModelo(String codigo_cliente, String nombre_cliente) {
        this.codigo_cliente = codigo_cliente;
        this.nombre_cliente = nombre_cliente;
    }*/

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }
    
    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }
    
    public String getId_cliente() {
        return id_cliente;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }
    
}
