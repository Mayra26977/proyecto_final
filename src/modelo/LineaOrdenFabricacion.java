package modelo;

/**
 *
 * @author maria.enriquez
 */
public class LineaOrdenFabricacion {

    private int idLineaOf;
    private int idOrdenFabricacion;
    private int idProducto;
    private double cantidad;

    public LineaOrdenFabricacion() {
    }
    
    

    public LineaOrdenFabricacion(int idLineaOf, int idOrdenFabricacion, int idProducto, double cantidad) {
        this.idLineaOf = idLineaOf;
        this.idOrdenFabricacion = idOrdenFabricacion;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdLineaOf() {
        return idLineaOf;
    }

    public void setIdLineaOf(int idLineaOf) {
        this.idLineaOf = idLineaOf;
    }

    public int getIdOrdenFabricacion() {
        return idOrdenFabricacion;
    }

    public void setIdOrdenFabricacion(int idOrdenFabricacion) {
        this.idOrdenFabricacion = idOrdenFabricacion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
    
   
    
}
