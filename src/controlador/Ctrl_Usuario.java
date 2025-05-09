/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import conexion.Conexion;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import modelo.Usuario;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Ctrl_Usuario {
     /**
     *  **********************************************************************
     * metodo para registrar el Usuario, o guardarlo en tb_usuario de la base de
     * datos
     * **********************************************************************
     */
    public boolean guardar(Usuario objeto) {
        boolean respuesta = false; //variable para saber si el usuario fue guardada o no con exito
        Connection cn = conexion.Conexion.conectar();//establecemos conexion a la base de datos

        try {

            PreparedStatement consulta = cn.prepareStatement("insert into tb_usuario values(?,?,?,?,?,?,?)");//preparamos consulta

            //configuramos o damos los valores a los parametros de la consulta
            consulta.setInt(1, 0);//id
            consulta.setString(2, objeto.getNombre());//indicamos que en el segundo ? ponga lo que este dentro de objeto.getNombre
            consulta.setString(3, objeto.getApellido());
            consulta.setString(4, objeto.getUsuario());
            consulta.setString(5, objeto.getPassword());
            consulta.setString(6, objeto.getTelefono());            
            consulta.setInt(7, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo insertar a la base de datos
                respuesta = true;//fue un exito
            }

            cn.close();// con este metodo cerramos la conexion a la base de datos

        } catch (SQLException e) {
            System.out.println("Error al guardar el usuario: " + e);//no se pudo insertar 
        }

        return respuesta;//retornamos si fue un exito o no el insertar el usuario a la base de datos
    }

    /**
     *  ******************************************************************************************************************************************************
     * metodo para consultar si el usuario se repite en la base de datos de la
     * tabla tb_usuario, es decir para saber si ya hay uno con el mismo nombre
     * ******************************************************************************************************************************************************
     */
    public boolean existeUsuario(String usuario) {
        boolean respuesta = false; //para saber si existe o no otro usuario o no con exito
        String sql = "select cedula from tb_usuario where usuario = '" + usuario + "' ";
        Statement st;

        try {
            Connection cn = conexion.Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {//el next() pasa al siguiente registro de la base de datos
                respuesta = true;//si la consulta encuentra el valor del usuario igual al usuario que entrego la persona, da como respuesta true
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar el usuario: " + e);//no se pudo consultar 
        }

        return respuesta;//retornamos si habia o no un usuario igual
    }

    /**
     * *******************************************************************************************************************************************************
     * metodo para iniciar sesion, recibe los datos entregados por la vista, realiza la logica con esos datos y devuelve el resultado para que la vista los muestre
     * ******************************************************************************************************************************************************
     */
      
    public boolean loginUser(Usuario objeto) {
        boolean respuesta = false;

        Connection cn = Conexion.conectar();//iniciamos la conexion
        String sql = "select usuario, password from tb_usuario where usuario = '" + objeto.getUsuario() + "' and password = '" + objeto.getPassword() + "'"; // Construye una consulta SQL para buscar un usuario con un nombre de usuario y contraseña específicos.
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql); //Ejecuta la consulta SQL y obtiene un conjunto de resultados.


            while (rs.next()) {//este metodo next()permite avanzar al siguiente registro de la base de datos
                respuesta = true;// Si hay al menos un resultado en el conjunto, establece la respuesta como verdadera.
            }

        } catch (SQLException e) {
            System.out.println("Error al iniciar sesion: ");
            JOptionPane.showMessageDialog(null, "Error al iniciar sesion");
        }
        
        return respuesta;//retorna false si no cumple con el where de la consulta, y si si cumple, entonces retorna verdadero
    }
    
     /**
     * **************************************************
     * metodo para actualizar(modificar o update) el usuario en la base de datos
     * en la tabla tb_usuario
     * **************************************************
     */
    public boolean actualizar(Usuario objeto, int idUsuario) {
        boolean respuesta = false;//variable para saber si el usuario fue actualizada o no con exito
        Connection cn = Conexion.conectar();
        try {

            PreparedStatement consulta = cn.prepareStatement("update tb_usuario set nombre=?, apellido = ?, usuario = ?, password= ?, telefono = ?, estado = ? where idUsuario ='" + idUsuario + "'");
            //configuramos o damos los valores a los parametros de la consulta sql que esta arriba 
            consulta.setString(1, objeto.getNombre());
            consulta.setString(2, objeto.getApellido());
            consulta.setString(3, objeto.getUsuario());
            consulta.setString(4, objeto.getPassword());
            consulta.setString(5, objeto.getTelefono());
            consulta.setInt(6, objeto.getEstado());

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo actualizar esos datos a la base de datos
                respuesta = true;//fue un exito
            }
            cn.close(); //cerramos la conexion a la base de datos
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e);
        }
        return respuesta;
    }
       
    /**
     * **************************************************
     * metodo para eliminar(Delete) el usuario en la base de datos en la tabla
     * tb_usuario
     * **************************************************
     */
    public boolean eliminar(int idUsuario) {
        boolean respuesta = false;//variable para saber si el usuario fue eliminado o no con exito
        Connection cn = Conexion.conectar();//establecemos conexion a la base de datos
        try {
            PreparedStatement consulta = cn.prepareStatement(
                    "delete from tb_usuario where idUsuario ='" + idUsuario + "'");
            consulta.executeUpdate();

            if (consulta.executeUpdate() > 0) {//el executeUpdate retorna las filas afectadas en la base de datos, entonces si retorna 1, significa que si se pudo elimnar de la base de datos
                respuesta = true;
            }
            cn.close();//cerramos conexion con la base de datos
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e);
        }
        return respuesta;
    }
}
