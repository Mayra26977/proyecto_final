package modelo;

/**
 *
 * @author maria.enriquez
 */
public class Linea_Pedido_Venta {
    
    private int idLinea_pedido;
    private int id_pedido;
    private int id_producto;
    private double cantidad;
    private double importe_total_linea;

    public Linea_Pedido_Venta() {
    }

    public Linea_Pedido_Venta(int idLinea_pedido, int id_pedido, int id_producto, double cantidad, double importe_total_linea) {
        this.idLinea_pedido = idLinea_pedido;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.importe_total_linea = importe_total_linea;
    }

    public int getIdLinea_pedido() {
        return idLinea_pedido;
    }

    public void setIdLinea_pedido(int idLinea_pedido) {
        this.idLinea_pedido = idLinea_pedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getImporte_total_linea() {
        return importe_total_linea;
    }

    public void setImporte_total_linea(double importe_total_linea) {
        this.importe_total_linea = importe_total_linea;
    }
    
    
    
}
