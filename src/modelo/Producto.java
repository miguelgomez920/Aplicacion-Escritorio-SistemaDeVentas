
package modelo;

/**
 *
 * @author migue
 */
public class Producto {
    
    //ATRIBUTOS
    private int idProducto;
    private String nombre;
    private int cantidad;
    private double precio;
    private String descripcion;
    private int porcentajeIva;
    private int idCategoria;
    private int estado;
    
    //METODOS
    //constructor vacio
    public Producto(){
    this.idProducto = 0;
    this.nombre = "";
    this.cantidad = 0;
    this.precio = 0.0;
    this.descripcion = "";
    this.porcentajeIva = 0;
    this.idCategoria = 0;
    this.estado = 0;            
    }
    
    //constructor con argumentos
    public Producto(int idProducto, String nombre, int cantidad, double precio, String descripcion, int porcentajeIva, int idCategoria, int estado) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.porcentajeIva = porcentajeIva;
        this.idCategoria = idCategoria;
        this.estado = estado;
    }
    
    //metodos getter y setter

    public int getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorcentajeIva() {
        return this.porcentajeIva;
    }

    public void setPorcentajeIva(int porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public int getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
      
    
}
