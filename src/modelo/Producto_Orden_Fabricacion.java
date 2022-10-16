package modelo;

/**
 *
 * @author maria.enriquez
 */
public class Producto_Orden_Fabricacion {
    private int id_of;
    private int id_producto;

    public Producto_Orden_Fabricacion() {
    }

    public Producto_Orden_Fabricacion(int id_of, int id_producto) {
        this.id_of = id_of;
        this.id_producto = id_producto;
    }

    public int getId_of() {
        return id_of;
    }

    public void setId_of(int id_of) {
        this.id_of = id_of;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
    
    
}
