/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import conexion.Conexion;
import java.sql.ResultSet;
import modelo.Usuario;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Ctr_Usuario {

    //metodo para iniciar sesion 
    public boolean loginUser(Usuario objeto) {
        boolean respuesta = false;

        Connection cn = Conexion.conectar();
        String sql = "select usuario, password from tb_usuario where usuario = '" + objeto.getUsuario() + "' and password = '" + objeto.getPassword() + "'";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                respuesta = true;
            }

        } catch (SQLException e) {
            System.out.println("Error al iniciar sesion: ");
            JOptionPane.showMessageDialog(null, "Error al iniciar sesion");
        }
        
        return respuesta;
    }
}
