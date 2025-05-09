
package modelo;

/**
 *
 * @author migue
 */
public class Cliente {
    //ATRIBUTOS
    private int idCliente;
    private String nombre;
    private String apellido;
    private String cedula;
    private String telefono;
    private String direccion;
    private int estado;
    
    //METODOS
    //constructor vacio
    public Cliente(){
    this.idCliente = 0;
    this.nombre = "";
    this.apellido = "";
    this.cedula = "";
    this.telefono = "";
    this.direccion = "";
    this.estado = 0;
    
    }
    
    //constructor con argumentos (sobrecargado)
    public Cliente(int idCliente, String nombre, String apellido, String cedula, String telefono, String direccion, int estado) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
    }
    
    //metodos getter y setter
    public int getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return this.cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int  getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
      
}
