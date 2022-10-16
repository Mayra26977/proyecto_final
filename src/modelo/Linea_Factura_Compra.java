package modelo;

/**
 *
 * @author maria.enriquez
 */
public class Linea_Factura_Compra {
    
   private int id_linea;
   private int id_factura;
   private int id_producto;
   private double cantidad;
   private double importe_linea;

    public Linea_Factura_Compra() {
    }

    public Linea_Factura_Compra(int id_linea, int id_factura, int id_producto, double cantidad, double importe_linea) {
        this.id_linea = id_linea;
        this.id_factura = id_factura;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.importe_linea = importe_linea;
    }

    public int getId_linea() {
        return id_linea;
    }

    public void setId_linea(int id_linea) {
        this.id_linea = id_linea;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
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

    public double getImporte_linea() {
        return importe_linea;
    }

    public void setImporte_linea(double importe_linea) {
        this.importe_linea = importe_linea;
    }
   
   
}
