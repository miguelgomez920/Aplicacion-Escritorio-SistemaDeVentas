package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.CabeceraVenta;
import modelo.DetalleVenta;

/**
 * @author edison
 */
public class Ctrl_RegistrarVenta {
    
    //ultimo id de la cabecera
    public static int idCabeceraRegistrada;
    java.math.BigDecimal iDColVar;
    /**
     * **************************************************
     * metodo para guardar la cabecera de venta
     * **************************************************
     */
    public boolean guardar(CabeceraVenta objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();// Establecer conexión a la base de datos
        try {
            // Preparar la consulta SQL para insertar una nueva cabecera de venta
            PreparedStatement consulta = cn.prepareStatement("insert into tb_cabecera_venta values(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            // Establecer los parámetros de la consulta con los valores del objeto CabeceraVenta
            consulta.setInt(1, 0);//id
            consulta.setInt(2, objeto.getIdCliente());
            consulta.setDouble(3, objeto.getValorPagar());
            consulta.setString(4, objeto.getFechaVenta());
            consulta.setInt(5, objeto.getEstado());
            
            if (consulta.executeUpdate() > 0) {// Ejecutar la consulta SQL y verificar si se insertó correctamente
                respuesta = true;
            }
            // Obtener las claves generadas automáticamente (en este caso, el ID)
            ResultSet rs = consulta.getGeneratedKeys();
            while(rs.next()){
                iDColVar = rs.getBigDecimal(1);//se accede al valor de la clave generada automáticamente mediante rs.getBigDecimal(1), que obtiene el valor de la primera columna del resultado (el ID en este caso). 
                idCabeceraRegistrada = iDColVar.intValue();// se convierte este valor en un entero y se asigna a la variable idCabeceraRegistrada
            }
            
            cn.close();   // Cerrar la conexión a la base de datos
        } catch (SQLException e) {
            System.out.println("Error al guardar cabecera de venta: " + e);
        }
        return respuesta;
    }
    
     /**
     * **************************************************
     * metodo para guardar el detalle de venta
     * **************************************************
     */
    public boolean guardarDetalle(DetalleVenta objeto) {
        boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("insert into tb_detalle_venta values(?,?,?,?,?,?,?,?,?,?)");
            //asignamos los valores a los argumentos de la consulta sql
            consulta.setInt(1, 0);//id
            consulta.setInt(2, idCabeceraRegistrada);
            consulta.setInt(3, objeto.getIdProducto());
            consulta.setInt(4, objeto.getCantidad());
            consulta.setDouble(5, objeto.getPrecioUnitario());
            consulta.setDouble(6, objeto.getSubTotal());
            consulta.setDouble(7, objeto.getDescuento());
            consulta.setDouble(8, objeto.getIva());
            consulta.setDouble(9, objeto.getTotalPagar());
            consulta.setInt(10, objeto.getEstado());
            
            if (consulta.executeUpdate() > 0) {//como executeUpdate nos entrega el numero de filas afectadas en la base de datos, entonces si hay mas de 0 filas afectadas es porque si se pudo guardar el detalle de la venta dentro de tb_detalle_venta
                respuesta = true;
            }
            cn.close();//cerramos conexion con la base de datos
        } catch (SQLException e) {
            System.out.println("Error al guardar detalle de venta: " + e);
        }
        return respuesta;
    }
    
          /**
     * **************************************************
     * metodo para actualizar un producto
     * **************************************************
     */
    public boolean actualizar(CabeceraVenta objeto, int idCabeceraVenta) {
        boolean respuesta = false; //variable para saber si se actualizo de forma correcta o no
        Connection cn = Conexion.conectar();// Establecer conexión a la base de datos
        try {
             // Preparar la consulta SQL para actualizar la cabecera de venta
            PreparedStatement consulta = cn.prepareStatement(
                    "update tb_cabecera_venta set idCliente = ?, estado = ? "
                            + "where idCabeceraVenta ='" + idCabeceraVenta + "'");
            consulta.setInt(1, objeto.getIdCliente());// Establecer el nuevo ID del cliente en la consulta
            consulta.setInt(2, objeto.getEstado());// Establecer el nuevo estado en la consulta
           
            // Ejecutar la consulta de actualización y verificar si se realizó con éxito
            if (consulta.executeUpdate() > 0) {
                respuesta = true;// Actualización exitosa, cambiar la respuesta a verdadero
            }
            cn.close();// Cerrar la conexión a la base de datos
        } catch (SQLException e) {
            System.out.println("Error al actualizar cabecera de venta: " + e);
        }
        return respuesta;// Devolver la respuesta de la operación de actualización
    }
}
