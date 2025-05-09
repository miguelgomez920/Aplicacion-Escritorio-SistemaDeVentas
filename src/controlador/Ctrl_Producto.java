package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Producto;

/**
 *
 * @author migue
 */
public class Ctrl_Producto {

    /**
     *  **********************************************************************
     * metodo para registrar Producto, o guardarlo en tb_producto de la base de
     * datos
     * **********************************************************************
     */
    public boolean guardar(Producto objeto) {
        boolean respuesta = false; //variable para saber si el producto fue guardada o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("insert into tb_producto values(?,?,?,?,?,?,?,?)");//preparamos consulta

            //configuramos o damos los valores a los parametros de la consulta
            consulta.setInt(1, 0);//id
            consulta.setString(2, objeto.getNombre());//indicamos que en el segundo ? ponga lo que este dentro de objeto.getNombre
            consulta.setInt(3, objeto.getCantidad());
            consulta.setDouble(4, objeto.getPrecio());
            consulta.setString(5, objeto.getDescripcion());
            consulta.setInt(6, objeto.getPorcentajeIva());
            consulta.setInt(7, objeto.getIdCategoria());
            consulta.setInt(8, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo insertar a la base de datos
                respuesta = true;//fue un exito
            }

            cn.close(); //cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al guardar producto: " + e);//no se pudo insertar 
        }

        return respuesta;//retornamos si fue un exito o no el insertar el producto a la base de datos
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para consultar si el producto se repite en la base de datos de la
     * tabla tb_productos, es decir para saber si ya hay una con el mismo nombre
     * ******************************************************************************************************************************************************
     */
    public boolean existeProducto(String producto) {
        boolean respuesta = false;
        String sql = "select nombre from tb_producto where nombre = '" + producto + "' ";
        Statement st;

        try {
            Connection cn = conexion.Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//el next() pasa al siguiente registro de la base de datos
                respuesta = true;//si la consulta encuentra el valor de un nombre igual al nombre que entrego el usuario, da como respuesta true
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar producto: " + e);//no se pudo consultar 
        }

        return respuesta;//retornamos si habia o no un producto igual
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para actualizar(modificar o update) el producto en la base de
     * datos en la tabla tb_producto
     * ******************************************************************************************************************************************************
     */
    public boolean actualizar(Producto objeto, int idProducto) {
        boolean respuesta = false; //variable para saber si el producto fue actualizada o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_producto set nombre=?, cantidad=?, precio= ?, descripcion=?, porcentajeIva=?, idCategoria=?, estado=?  where idProducto = '" + idProducto + "'");//preparamos consulta

            //configuramos o damos los valores a los parametros de la consulta
            consulta.setString(1, objeto.getNombre());
            consulta.setInt(2, objeto.getCantidad());
            consulta.setDouble(3, objeto.getPrecio());
            consulta.setString(4, objeto.getDescripcion());
            consulta.setInt(5, objeto.getPorcentajeIva());
            consulta.setInt(6, objeto.getIdCategoria());
            consulta.setInt(7, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo actualizar esos datos a la base de datos
                respuesta = true;//fue un exito
            }

            cn.close(); //cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e);//no se pudo insertar 
        }

        return respuesta;
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para eliminar(Delete) el producto en la base de datos en la tabla
     * tb_producto
     * ******************************************************************************************************************************************************
     */
    public boolean eliminar(int idProducto) {
        boolean respuesta = false; //variable para saber si el producto fue eliminado o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("delete from tb_producto where idProducto = '" + idProducto + "'");//preparamos consulta          
            consulta.executeUpdate();

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo elimnar de la base de datos
                respuesta = true;//fue un exito
            }

            cn.close(); //cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e);//no se pudo eliminar 
        }

        return respuesta;//retornamos si se elimino(true) o no (false)
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para actualizar el stock del producto
     * ******************************************************************************************************************************************************
     */
    public boolean actualizarStock(Producto object, int idProducto) {
        boolean respuesta = false; //variable para saber si el stock del producto fue actualizado o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_producto set cantidad=? where idProducto = '" + idProducto + "'");//preparamos consulta          
            consulta.setInt(1, object.getCantidad());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo actualizar de la base de datos
                respuesta = true;//fue un exito
            }

            cn.close(); //cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al actualizar el stock del producto: " + e);//no se pudo eliminar 
        }

        return respuesta;//retornamos si se elimino(true) o no (false)
    }

}
