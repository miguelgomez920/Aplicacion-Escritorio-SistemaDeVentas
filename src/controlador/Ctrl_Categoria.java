package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Categoria;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author migue
 */
public class Ctrl_Categoria {

    /**
     *  **********************************************************************
     * metodo para registrar categoria, insertarla o guardarla en la base de datos
     * **********************************************************************
     */
    public boolean guardar(Categoria objeto) {
        boolean respuesta = false; //para saber si la categoria fue guardada o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("insert into tb_categoria values(?,?,?)");//preparamos consulta

            //configuramos o damos los valores a los parametros de la consulta
            consulta.setInt(1, 0);
            consulta.setString(2, objeto.getDescripcion());//indicamos que en el segundo signo de pregunta ? de la consulta, ponga lo que este dentro de objeto.getDescripcion 
            consulta.setInt(3, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo insertar a la base de datos
                respuesta = true;//fue un exito
            }

            cn.close();// cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al guardar categoria: " + e);//no se pudo insertar 
        }

        return respuesta;//retornamos si fue un exito o no el insertar la categoria a la base de datos
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para consultar si la categoria se repite en la base de datos de la
     * tabla tb_categoria, es decir para saber si ya hay una con el mismo nombre
     * ******************************************************************************************************************************************************
     */
    public boolean existeCategoria(String categoria) {
        boolean respuesta = false; //para saber si la categoria existe o no con exito
        String sql = "select descripcion from tb_categoria where descripcion = '" + categoria + "' ";
        Statement st;

        try {
            Connection cn = conexion.Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//el next() pasa al siguiente registro de la base de datos
                respuesta = true;//si la consulta encuentra el valor de una descripcion igual a la descripcion que entrego el usuario, da como respuesta true
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar categoria: " + e);//no se pudo consultar 
        }

        return respuesta;//retornamos si fue un exito o no el encontrar otra categoria con el mismo nombre en la base de datos
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para actualizar(modificar o update) la categoria en la base de datos en la tabla tb_categoria
     * ******************************************************************************************************************************************************
     */
    public boolean actualizar(Categoria objeto, int idCategoria) {
        boolean respuesta = false; //para saber si la categoria fue actualizada o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_categoria set descripcion=? where idCategoria = '"+ idCategoria +"'");//preparamos consulta

            //configuramos o damos los valores a los parametros de la consulta
            consulta.setString(1, objeto.getDescripcion());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo actualizar esos datos a la base de datos
                respuesta = true;//fue un exito
            }

            cn.close();//cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al actualizar categoria: " + e);//no se pudo insertar 
        }

        return respuesta;//retornamos si fue un exito o no el actualizar la categoria a la base de datos
    }
    
    /**
     *  ******************************************************************************************************************************************************
     * metodo para eliminar(Delete) la categoria en la base de datos en la tabla tb_categoria
     * ******************************************************************************************************************************************************
     */
    public boolean eliminar( int idCategoria) {
        boolean respuesta = false; //para saber si la categoria fue eliminada o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("delete from tb_categoria where idCategoria = '"+ idCategoria +"'");//preparamos consulta          
            consulta.executeUpdate();

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo elimnar de la base de datos
                respuesta = true;//fue un exito
            }

            cn.close();// cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al eliminar categoria: " + e);//no se pudo insertar 
        }

        return respuesta;//retornamos si se pudo o no eliminar de la base de datos
    }
    
    

}
