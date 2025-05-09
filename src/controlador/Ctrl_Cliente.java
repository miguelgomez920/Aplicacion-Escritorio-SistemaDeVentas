package controlador;

import modelo.Cliente;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author migue
 */
public class Ctrl_Cliente {

    /**
     *  **********************************************************************
     * metodo para registrar el Cliente, o guardarlo en tb_cliente de la base de
     * datos
     * **********************************************************************
     */
    public boolean guardar(Cliente objeto) {
        boolean respuesta = false; // variable para saber si el cliente fue guardado o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("insert into tb_cliente values(?,?,?,?,?,?,?)");//preparamos consulta

            //configuramos o damos los valores a los parametros de la consulta
            consulta.setInt(1, 0);//id
            consulta.setString(2, objeto.getNombre());//indicamos que en el segundo ? ponga lo que este dentro de objeto.getNombre
            consulta.setString(3, objeto.getApellido());
            consulta.setString(4, objeto.getCedula());
            consulta.setString(5, objeto.getTelefono());
            consulta.setString(6, objeto.getDireccion());
            consulta.setInt(7, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo insertar a la base de datos
                respuesta = true;//fue un exito
            }

            cn.close(); //cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al guardar cliente: " + e);//no se pudo insertar 
        }

        return respuesta;//retornamos si fue un exito o no el insertar el cliente a la base de datos
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para consultar si el cliente se repite en la base de datos
     * ******************************************************************************************************************************************************
     */
    public boolean existeCliente(String cedula) {
        boolean respuesta = false; //variable para saber si la categoria o no con exito
        String sql = "select cedula from tb_cliente where cedula = '" + cedula + "' ";
        Statement st;

        try {
            Connection cn = conexion.Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//el next() pasa al siguiente registro de la base de datos
                respuesta = true;//si la consulta encuentra el valor de una cedula igual a la cedula que entrego el usuario, da como respuesta true
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar cliente: " + e);//no se pudo consultar 
        }

        return respuesta;//retornamos si habia o no un cliente igual
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para actualizar(modificar o update) el cliente en la base de datos
     * en la tabla tb_cliente
     * ******************************************************************************************************************************************************
     */
    public boolean actualizar(Cliente objeto, int idCliente) {
        boolean respuesta = false; //variable para saber si el cliente fue actualizado o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_cliente set nombre=?, apellido=?, cedula= ?, telefono=?, direccion=?, estado=?  where idCliente = '" + idCliente + "'");//preparamos consulta

            //configuramos o damos los valores a los parametros de la consulta
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getCedula());
            consulta.setString(4, objeto.getTelefono());
            consulta.setString(5, objeto.getDireccion());            
            consulta.setInt(6, objeto.getEstado());

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
     * metodo para eliminar(Delete) el cliente en la base de datos en la tabla
     * tb_cliente
     * ******************************************************************************************************************************************************
     */
    public boolean eliminar(int idCliente) {
        boolean respuesta = false; //variable para saber si el cliente fue eliminado o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("delete from tb_cliente where idCliente = '" + idCliente + "'");//preparamos consulta          
            consulta.executeUpdate();

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo elimnar de la base de datos
                respuesta = true;//fue un exito
            }

            cn.close();// con este metodo cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente: " + e);//no se pudo eliminar 
        }

        return respuesta;//retornamos si se elimino(true) o no (false)
    }

}
