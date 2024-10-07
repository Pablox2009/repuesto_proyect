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
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import com.itextpdf.text.Image;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Imprimir {
    private String id;
    
    public void imprimirP(List<String> productos) {
        Document documento = new Document();
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + timestamp + ".pdf"));
            documento.open();

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidths(new float[]{1, 1, 1, 2});

            PdfPCell celdaMarca = new PdfPCell(new Phrase("Marca"));
            celdaMarca.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celdaMarca.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaMarca.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(celdaMarca);
            celdaMarca.setFixedHeight(60);

            PdfPCell celdaDescripcion = new PdfPCell(new Phrase("Descripción"));
            celdaDescripcion.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celdaDescripcion.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaDescripcion.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(celdaDescripcion);
            celdaDescripcion.setFixedHeight(60);

            PdfPCell celdaPrecio = new PdfPCell(new Phrase("Precio"));
            celdaPrecio.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celdaPrecio.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaPrecio.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(celdaPrecio);
            celdaPrecio.setFixedHeight(60);

            PdfPCell celdaImagen = new PdfPCell(new Phrase("Imagen"));
            celdaImagen.setBackgroundColor(BaseColor.LIGHT_GRAY);
            celdaImagen.setHorizontalAlignment(Element.ALIGN_CENTER);
            celdaImagen.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tabla.addCell(celdaImagen);
            celdaImagen.setFixedHeight(60);

            // Construimos la consulta SQL para seleccionar varios productos
            String placeholders = String.join(",", Collections.nCopies(productos.size(), "?")); // Crea tantos '?' como IDs
            String select = "SELECT pr.nombre_producto, pr.precio, pr.foto, ma.nombre AS marca_nombre " +
                            "FROM productos pr " +
                            "INNER JOIN marca ma ON pr.marca = ma.id_marca " +
                            "WHERE pr.id IN (" + placeholders + ")";

            try (Connection con = conexion.conectar();
                 PreparedStatement ps = con.prepareStatement(select)) {

                // Establecemos los valores de los parámetros
                for (int i = 0; i < productos.size(); i++) {
                    ps.setString(i + 1, productos.get(i));
                }

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    PdfPCell celdaMarcaNombres = new PdfPCell(new Phrase(rs.getString("marca_nombre")));
                    celdaMarcaNombres.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaMarcaNombres.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    celdaMarcaNombres.setFixedHeight(30);
                    tabla.addCell(celdaMarcaNombres);

                    PdfPCell celdaNombreProductos = new PdfPCell(new Phrase(rs.getString("nombre_producto")));
                    celdaNombreProductos.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaNombreProductos.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    celdaNombreProductos.setFixedHeight(30);
                    tabla.addCell(celdaNombreProductos);

                    PdfPCell celdaPrecios = new PdfPCell(new Phrase("$ " + rs.getString("precio")));
                    celdaPrecios.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaPrecios.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    celdaPrecios.setFixedHeight(30);
                    tabla.addCell(celdaPrecios);

                    byte[] fotoBytes = rs.getBytes("foto");
                    if (fotoBytes != null && fotoBytes.length > 0) {
                        Image imagen = Image.getInstance(fotoBytes);
                        imagen.scaleToFit(140, 140);
                        PdfPCell imagenCell = new PdfPCell(imagen);
                        imagenCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        imagenCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        imagenCell.setFixedHeight(150);
                        tabla.addCell(imagenCell);
                    } else {
                        PdfPCell celdaSinImagen = new PdfPCell(new Phrase("Imagen no disponible"));
                        celdaSinImagen.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celdaSinImagen.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        celdaSinImagen.setFixedHeight(30);
                        tabla.addCell(celdaSinImagen);
                    }
                }

                documento.add(tabla);
            } catch (Exception e) {
                e.printStackTrace();
            }

            documento.close();
            JOptionPane.showMessageDialog(null, "Listooo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
