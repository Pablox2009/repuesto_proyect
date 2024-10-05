/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario
 */
public class menuModel {
    private String id;
    private int id_marca;
    private String marca;
    private String nombre;
    private String precio;

    public void setId(String id) {
        this.id = id;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public int getId_marca() {
        return id_marca;
    }

    public String getMarca() {
        return marca;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }
}
