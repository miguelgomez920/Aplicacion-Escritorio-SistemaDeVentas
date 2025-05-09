package controlador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import vista.InterFacturacion;

/**
 *
 * @author migue
 */
public class VentaPDF {

    private String nombreCliente;
    private String cedulaCliente;
    private String telefonoCliente;
    private String direccionCliente;

    private String fechaActual = "";
    private String nombreArchivoPDFVenta = "";
    
        /* ********************************************************************
    * metodo para obtener datos del cliente de la base de datos
    *********************************************************************** */

    public void DatosCliente(int idCliente) {
        Connection cn = Conexion.conectar(); //conexion con la base de datos
        String sql = "select * from tb_cliente where idCliente = '" + idCliente + "'";
        Statement st;
        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);//ejecutamos la consulta
            while (rs.next()) {//vamos iterando cada fila del resultado de la consulta, hasta que no hayan mas filas
                //guardamos los datos en las varibles
                nombreCliente = rs.getString("nombre") + " " + rs.getString("apellido");
                cedulaCliente = rs.getString("cedula");
                telefonoCliente = rs.getString("telefono");
                direccionCliente = rs.getString("direccion");
            }
            cn.close();
        } catch (SQLException e) {//para manejar exepciones, en este caso de tipo sql
            System.out.println("Error al obtener datos del cliente: " + e);
        }
    }
    
        /* ********************************************************************
    * metodo para generar la factura de venta
    *********************************************************************** */

    public void generarFacturaPDF() {
        try {

            //cargar la fecha actual
            Date date = new Date();
            fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
            //cambiar el formato de la fecha de / a _ (en vez de tener los slashes, que tenga los guiones bajos), por que al poner la fecha en el nombre del archivo no deja con los slashes
            String fechaNueva = "";
            for (int i = 0; i < fechaActual.length(); i++) {//se sabe que un String es un vector de chars, entonces podemos iterar cada char de ese string y asi remplazar lo que queramos
                if (fechaActual.charAt(i) == '/') {
                    fechaNueva = fechaActual.replace("/", "_");
                }
            }

            nombreArchivoPDFVenta = "Venta_" + nombreCliente + "_" + fechaNueva + ".pdf";//concatenamos para obtener el nombre del archivo pdf

            // Definición de variables y objetos necesarios para crear el PDF
            FileOutputStream archivo;// Objeto para escribir en un archivo
            File file = new File("src/pdf/" + nombreArchivoPDFVenta);// Objeto que representa el archivo PDF, guardamos la ruta del archivo en la variable file
            archivo = new FileOutputStream(file);// Inicialización del archivo
           
            Document doc = new Document();// Creación de un nuevo documento PDF
            PdfWriter.getInstance(doc, archivo);// Creación de un escritor PDF y asociación con el archivo
            doc.open();//abrimos el documento

            // Encabezado del documento
            Image img = Image.getInstance("src/img/ventas.png");// Imagen del encabezado
            Paragraph fecha = new Paragraph();// creamos un Párrafo para la fecha
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);// Fuente 
            fecha.add(Chunk.NEWLINE); //agregar nueva linea
            fecha.add("Factura: 001" + "\nFecha: " + fechaActual + "\n\n");// Información de la factura y fecha

            // Tabla para el encabezado
            PdfPTable Encabezado = new PdfPTable(4);// Tabla con 4 columnas
            // Configuración de la tabla
            Encabezado.setWidthPercentage(100);// Ancho de la tabla al 100%
            Encabezado.getDefaultCell().setBorder(0);//quitar el borde de la tabla
            //tamaño de las celdas
            float[] ColumnaEncabezado = new float[]{20f, 30f, 70f, 40f};//arreglo o array de tipo flotante, para cuadrar el tamaño de las columnas
            Encabezado.setWidths(ColumnaEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_LEFT);// Alineación a la izquierda
            //agregar celdas a la tabla
            Encabezado.addCell(img);// Celda con la imagen

            //datos de la empresa que seran imprimidos en el recibo
            String ruc = "0987654321001";
            String nombre = "Miguel Cooporation";
            String telefono = "0987654321";
            String direccion = "Tamarindo City";
            String razon = "La magia de la programacion, esta en el poder de tu imaginacion";

            Encabezado.addCell("");//celda vacia
            Encabezado.addCell("RUC: " + ruc + "\nNOMBRE: " + nombre + "\nTELEFONO: " + telefono + "\nDIRECCION: " + direccion + "\nRAZON SOCIAL: " + razon);
            Encabezado.addCell(fecha);// Información de la fecha
            doc.add(Encabezado);// Agregar el encabezado al documento

            //CUERPO DEL DOCUMENTO
            Paragraph cliente = new Paragraph();//creamos nuevo parrafo
            cliente.add(Chunk.NEWLINE);//nueva linea
            cliente.add("Datos del cliente: " + "\n\n");// Título
            doc.add(cliente);// Agregar al documento

            //DATOS DEL CLIENTE
            PdfPTable tablaCliente = new PdfPTable(4);// Tabla con 4 columnas
            // Configuración de la tabla
            tablaCliente.setWidthPercentage(100);// Ancho de la tabla al 100%
            tablaCliente.getDefaultCell().setBorder(0);//quitar bordes
            //tamaño de las celdas
            float[] ColumnaCliente = new float[]{25f, 45f, 30f, 40f};// Tamaño de las columnas
            tablaCliente.setWidths(ColumnaCliente);
            tablaCliente.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliente1 = new PdfPCell(new Phrase("Cedula/RUC: ", negrita));
            PdfPCell cliente2 = new PdfPCell(new Phrase("Nombre: ", negrita));
            PdfPCell cliente3 = new PdfPCell(new Phrase("Telefono: ", negrita));
            PdfPCell cliente4 = new PdfPCell(new Phrase("Direccion: ", negrita));
            //quitar bordes 
            cliente1.setBorder(0);
            cliente2.setBorder(0);
            cliente3.setBorder(0);
            cliente4.setBorder(0);
            //agg celda a la tabla
            tablaCliente.addCell(cliente1);
            tablaCliente.addCell(cliente2);
            tablaCliente.addCell(cliente3);
            tablaCliente.addCell(cliente4);
            tablaCliente.addCell(cedulaCliente);
            tablaCliente.addCell(nombreCliente);
            tablaCliente.addCell(telefonoCliente);
            tablaCliente.addCell(direccionCliente);
            //agregar al documento
            doc.add(tablaCliente);// Agregar la tabla de datos del cliente al documento
            
            //ESPACIO EN BLANCO
            Paragraph espacio = new Paragraph();
            espacio.add(Chunk.NEWLINE);
            espacio.add("");
            espacio.setAlignment(Element.ALIGN_CENTER);
            doc.add(espacio);//lo agregamos al documento
            
            //AGREGAR LOS PRODUCTOS
            PdfPTable tablaProducto = new PdfPTable(4);//creamos otra tabla con 4 columnas
            tablaProducto.setWidthPercentage(100);
            tablaProducto.getDefaultCell().setBorder(0);
            //tamaño de celdas
             float[] ColumnaProducto = new float[]{15f, 50f, 15f, 20f};//tamaño de cada celda
             tablaProducto.setWidths(ColumnaProducto);
             tablaProducto.setHorizontalAlignment(Element.ALIGN_LEFT);
             PdfPCell producto1 = new PdfPCell(new Phrase("Cantidad: ", negrita));
             PdfPCell producto2 = new PdfPCell(new Phrase("Descripcion: ", negrita));
             PdfPCell producto3 = new PdfPCell(new Phrase("Precio Unitario: ", negrita));
             PdfPCell producto4 = new PdfPCell(new Phrase("Precio Total: ", negrita));
             //quitar bordes
             producto1.setBorder(0);
             producto2.setBorder(0);
             producto3.setBorder(0);
             producto4.setBorder(0);
             //agregar color al encabezadi del producto
             producto1.setBackgroundColor(BaseColor.LIGHT_GRAY);
             producto2.setBackgroundColor(BaseColor.LIGHT_GRAY);
             producto3.setBackgroundColor(BaseColor.LIGHT_GRAY);
             producto4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            //agg celda a la tabla
            tablaProducto.addCell(producto1);
            tablaProducto.addCell(producto2);
            tablaProducto.addCell(producto3);
            tablaProducto.addCell(producto4);
            
            //usamos este for para obtener los datos de la tabla jTable_productos y añadirlos a las celdas
            for(int i = 0; i < InterFacturacion.jTable_productos.getRowCount(); i++){
                String producto = InterFacturacion.jTable_productos.getValueAt(i, 1).toString();
                String cantidad = InterFacturacion.jTable_productos.getValueAt(i, 2).toString();
                String precio = InterFacturacion.jTable_productos.getValueAt(i, 3).toString();
                String total = InterFacturacion.jTable_productos.getValueAt(i, 7).toString();
                
                tablaProducto.addCell(cantidad);
                tablaProducto.addCell(producto);
                tablaProducto.addCell(precio);
                tablaProducto.addCell(total);
            }
            
            //agregar al documento
            doc.add(tablaProducto);
            
            //Total pagar
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a pagar: " + InterFacturacion.txt_total_pagar.getText());
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            
            //Firma
           Paragraph firma = new Paragraph();
           firma.add(Chunk.NEWLINE);
           firma.add("Cancelacion y firma\n\n");
           firma.add("_______________________");
           firma.setAlignment(Element.ALIGN_CENTER);
           doc.add(firma);
           
            //Mensaje
           Paragraph mensaje = new Paragraph();
           mensaje.add(Chunk.NEWLINE);
           mensaje.add("¡Gracias por su compra!");
           mensaje.setAlignment(Element.ALIGN_CENTER);
           doc.add(mensaje);
           
           //cerrar el documento y el archivo
           doc.close();
           archivo.close();
           
           //abrir el documento(pdf) al ser generado automaticamente
            Desktop.getDesktop().open(file);
            
            
        } catch (DocumentException | IOException e) {//para manejar errores
            System.out.println("Error en: " + e);
        }
    }

}
