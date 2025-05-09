package controlador;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import conexion.Conexion;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author migue
 */
public class Reportes {

    /* ********************************************************************
    * metodo para crear reportes de los clientes registrados en el sistema o baso de datos.
    *********************************************************************** */
    public void ReportesClientes() {
        Document documento = new Document();// Crear un nuevo documento PDF
        try {
            //String ruta = System.getProperty("user.home");// Obtener la ruta del directorio del usuario (C:\\Users\\migue), en el metodo ReportesProductos si uso esta variable para ver como funcionaria con esta, de igual forma toca escapar los \
            // Crear un escritor PDF y especificar la ubicación del archivo de salida (donde se va a guardar)
            PdfWriter.getInstance(documento, new FileOutputStream("C:\\Users\\migue\\OneDrive\\Documents\\CURSOS\\PROYECTOS\\PROYECTOS PROPIOS\\SISTEMA DE VENTAS (JAVA,POO,MVC,Mysql)\\Reportes\\Reporte_Clientes.pdf"));//en esta parte toca escapar los guiones \ (es decir poner antes otro \) para poder que el compilador interprete correctamente
            Image header = Image.getInstance("src/img/header1.jpg"); // Cargar una imagen de encabezado
            header.scaleToFit(650, 1000);// Escalar la imagen para que se ajuste al documento
            header.setAlignment(Chunk.ALIGN_CENTER);// Alinear la imagen al centro
            
            //formato al texto, Crear un párrafo con información del autor y título del reporte
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);// Alinear el párrafo al centro
            parrafo.add("Reporte creado por \nEdison Zambrano © Programador Fantasma\n\n");// Información del autor
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));// Establecer fuente y estilo del texto
            parrafo.add("Reporte de Clientes \n\n");// Título del reporte

            documento.open();// Abrir el documento PDF para agregar contenido
            //agregamos los datos
            documento.add(header);
            documento.add(parrafo);

            PdfPTable tabla = new PdfPTable(5);// Crear una tabla con 5 columnas
            // Agregar encabezados de las columnas a la tabla
            tabla.addCell("Codigo");
            tabla.addCell("Nombres");
            tabla.addCell("Cedula");
            tabla.addCell("Telefono");
            tabla.addCell("Direccion");

            try {
                Connection cn = Conexion.conectar();// Establecer conexión a la base de datos
                // Preparar una consulta SQL para obtener datos de clientes
                PreparedStatement pst = cn.prepareStatement(
                        "select idCliente, concat(nombre, ' ', apellido) as nombres, cedula, telefono, direccion from tb_cliente");
                ResultSet rs = pst.executeQuery();// Ejecutar la consulta SQL
                if (rs.next()) {// Verificar si hay resultados en el conjunto de resultados
                    do {
                        // Agregar datos de cada cliente como celdas a la tabla
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                    } while (rs.next());// Continuar mientras haya más filas en el conjunto de resultados
                    documento.add(tabla);// Agregar la tabla al documento
                }

            } catch (SQLException e) {// Manejar excepciones de SQL
                System.out.println("Error 4 en: " + e);
            }
            documento.close();// Cerrar el documento PDF
            
            JOptionPane.showMessageDialog(null, "Reporte creado");

        } catch (DocumentException e) {// Manejar excepciones de DocumentException
            System.out.println("Error 1 en: " + e);
        } catch (FileNotFoundException ex) {// Manejar excepciones de FileNotFoundException
            System.out.println("Error 2 en: " + ex);
        } catch (IOException ex) {// Manejar excepciones de IOException
            System.out.println("Error 3 en: " + ex);
        }
    }
    
    /* ********************************************************************
    * metodo para crear reportes de los productos registrados en el sistema o base de datos
    *********************************************************************** */
    public void ReportesProductos() {
        Document documento = new Document();
        try {
          //String ruta = System.getProperty("user.home");// Obtener la ruta del directorio del usuario
            // Especificar la ubicación del archivo de salida del PDF (donde se va a guardar)
            PdfWriter.getInstance(documento, new FileOutputStream("C:\\Users\\migue\\OneDrive\\Documents\\CURSOS\\PROYECTOS\\PROYECTOS PROPIOS\\SISTEMA DE VENTAS (JAVA,POO,MVC,Mysql)\\Reportes\\Reporte_Productos.pdf"));
            Image header = Image.getInstance("src/img/header1.jpg");// Cargar una imagen de encabezado
            header.scaleToFit(650, 1000);// Escalar la imagen para que se ajuste al documento
            header.setAlignment(Chunk.ALIGN_CENTER);// Alinear la imagen al centro

            //formato al texto
            Paragraph parrafo = new Paragraph();//parrafo para la información del autor y título del reporte
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);// Alinear el párrafo al centro
            parrafo.add("Reporte creado por \nEdison Zambrano © Programador Fantasma\n\n");// Información del autor
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY)); // Establecer fuente y estilo del texto
            parrafo.add("Reporte de Productos \n\n");// Título del reporte

            documento.open();// Abrir el documento PDF para agregar contenido
            //agregamos los datos
            documento.add(header);
            documento.add(parrafo);
            
            float[] columnsWidths = {3, 5, 4, 5, 7, 5, 6};// Especificar el ancho de las columnas de la tabla

            // Crear una tabla con el ancho de columnas especificado
            PdfPTable tabla = new PdfPTable(columnsWidths);
             // Agregar encabezados de las columnas a la tabla
            tabla.addCell("Codigo");
            tabla.addCell("Nombre");
            tabla.addCell("Cant.");
            tabla.addCell("Precio");
            tabla.addCell("Descripcion");
            tabla.addCell("Por. Iva");
            tabla.addCell("Categoria");

            try {
                Connection cn = Conexion.conectar();// Establecer conexión a la base de datos
                // Preparar una consulta SQL para obtener datos de productos
                PreparedStatement pst = cn.prepareStatement(
                        "select p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion, "
                                + "p.porcentajeIva, c.descripcion as categoria, p.estado "
                                + "from tb_producto as p, tb_categoria as c "
                                + "where p.idCategoria = c.idCategoria;");
                ResultSet rs = pst.executeQuery();// Ejecutar la consulta SQL
                if (rs.next()) {// Verificar si hay resultados en el conjunto de resultados
                    do {
                         // Agregar datos de cada producto como celdas a la tabla
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                        tabla.addCell(rs.getString(7));
                    } while (rs.next());// Continuar mientras haya más filas en el conjunto de resultados
                    documento.add(tabla);// Agregar la tabla al documento
                }

            } catch (SQLException e) {// Manejar excepciones de SQL
                System.out.println("Error 4 en: " + e);
            }
            documento.close();// Cerrar el documento PDF
            
            JOptionPane.showMessageDialog(null, "Reporte creado");

        } catch (DocumentException e) {
            System.out.println("Error 1 en: " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("Error 2 en: " + ex);
        } catch (IOException ex) {
            System.out.println("Error 3 en: " + ex);
        }
    }
    
        /* ********************************************************************
    * metodo para crear reportes de los categorias registrados en el sistema
    *********************************************************************** */
    public void ReportesCategorias() {
        Document documento = new Document(); // Crea un nuevo documento PDF
        try {
            // String ruta = System.getProperty("user.home");  //Obtiene la ruta del usuario actual (directorio "home") 
            PdfWriter.getInstance(documento, new FileOutputStream("C:\\Users\\migue\\OneDrive\\Documents\\CURSOS\\PROYECTOS\\PROYECTOS PROPIOS\\SISTEMA DE VENTAS (JAVA,POO,MVC,Mysql)\\Reportes\\Reporte_Categorias.pdf"));
            // Carga una imagen (cabecera) desde el directorio especificado
            Image header = Image.getInstance("src/img/header1.jpg");
            header.scaleToFit(650, 1000);// Ajusta el tamaño de la imagen
            header.setAlignment(Chunk.ALIGN_CENTER); // Centra la imagen en el PDF
            // Crea un párrafo para incluir texto en el documento
            //formato al texto
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER); // Alinea el texto al centro
            parrafo.add("Reporte creado por \nEdison Zambrano © Programador Fantasma\n\n"); // Agrega texto inicial
             // Configura la fuente, tamaño y estilo del texto
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Categorias \n\n");

            documento.open(); // Abre el documento para escribir
            
            //agregamos los datos
            documento.add(header);
            documento.add(parrafo);
            
            // Crea una tabla con 3 columnas
            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Codigo");
            tabla.addCell("Descripcion");
            tabla.addCell("Estado");

            try {
                Connection cn = Conexion.conectar();   // Conecta con la base de datos
                PreparedStatement pst = cn.prepareStatement(  // Prepara la consulta SQL para obtener datos de la tabla "tb_categoria"
                        "select * from tb_categoria");
                ResultSet rs = pst.executeQuery(); // Ejecuta la consulta y obtiene los resultados
                // Si existen datos en la consulta, itera sobre ellos
                if (rs.next()) {
                    do {
                        // Agrega cada valor del registro como una celda en la tabla
                        tabla.addCell(rs.getString(1)); // Primera columna (Código)
                        tabla.addCell(rs.getString(2)); // Segunda columna (Descripción)
                        tabla.addCell(rs.getString(3)); // Tercera columna (Estado)
                    } while (rs.next());// Continua mientras haya más filas
                    documento.add(tabla);// Agrega la tabla completa al documento PDF
                }

            } catch (SQLException e) {
                System.out.println("Error 4 en: " + e);// Captura y muestra errores de SQL
            }
            documento.close(); // Cierra el documento después de agregar todos los elementos
            
            JOptionPane.showMessageDialog(null, "Reporte creado"); // Muestra un mensaje de confirmación al usuario

        } catch (DocumentException e) {
            System.out.println("Error 1 en: " + e); // Captura y muestra errores relacionados con la creación del documento
        } catch (FileNotFoundException ex) {
            System.out.println("Error 2 en: " + ex);// Captura y muestra errores si no se encuentra el archivo de salida
        } catch (IOException ex) {
            System.out.println("Error 3 en: " + ex); // Captura y muestra errores relacionados con la imagen o I/O
        }
    }
    
        /* ********************************************************************
    * metodo para crear reportes de las ventas registrados en el sistema
    *********************************************************************** */
    public void ReportesVentas() {
        Document documento = new Document();
        try {
            //String ruta = System.getProperty("user.home"); Crea un nuevo documento PDF
            PdfWriter.getInstance(documento, new FileOutputStream("C:\\Users\\migue\\OneDrive\\Documents\\CURSOS\\PROYECTOS\\PROYECTOS PROPIOS\\SISTEMA DE VENTAS (JAVA,POO,MVC,Mysql)\\Reportes\\Reporte_Ventas.pdf"));
            Image header = Image.getInstance("src/img/header1.jpg");
            header.scaleToFit(650, 1000);
            header.setAlignment(Chunk.ALIGN_CENTER);
            //formato al texto
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Reporte creado por \nEdison Zambrano © Programador Fantasma\n\n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.DARK_GRAY));
            parrafo.add("Reporte de Ventas \n\n");

            documento.open();
            //agregamos los datos
            documento.add(header);
            documento.add(parrafo);
            
            float[] columnsWidths = {3, 9, 4, 5, 3};

            PdfPTable tabla = new PdfPTable(columnsWidths);
            tabla.addCell("Codigo");
            tabla.addCell("Cliente");
            tabla.addCell("Tot. Pagar");
            tabla.addCell("Fecha Venta");
            tabla.addCell("Estado");

            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement(
                        "select cv.idCabeceraVenta as id, concat(c.nombre, ' ', c.apellido) as cliente, "
                                + "cv.valorPagar as total, cv.fechaVenta as fecha, cv.estado "
                                + "from tb_cabecera_venta as cv, tb_cliente as c "
                                + "where cv.idCliente = c.idCliente;");
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));

                    } while (rs.next());
                    documento.add(tabla);
                }

            } catch (SQLException e) {
                System.out.println("Error 4 en: " + e);
            }
            documento.close();
            
            JOptionPane.showMessageDialog(null, "Reporte creado");

        } catch (DocumentException e) {
            System.out.println("Error 1 en: " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("Error 2 en: " + ex);
        } catch (IOException ex) {
            System.out.println("Error 3 en: " + ex);
        }
    }

}
