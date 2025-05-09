
package modelo;

/**
 * @author ediso
 */
public class Categoria {
    //ATRIBUTOS
    private int idCategoria;
    private String descripcion;
    private int estado;
   
    //METODOS
    //constructor
   public Categoria(){
   this.idCategoria = 0;
   this.descripcion = "";
   this.estado = 0;
   }
   //constructor con argumentos
    public Categoria(int idCategoria, String descripcion, int estado) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.estado = estado;
    }
     
   //metodos getter y setter
    public int getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
   
      
}
