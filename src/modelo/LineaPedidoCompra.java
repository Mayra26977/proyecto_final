package modelo;

/**
 *
 * @author Mayra
 */
public class LineaPedidoCompra {
    
    private int idLinea_pedido;
    private int id_pedido;
    private int id_producto;
    private Double cantidad;
    private Double importeTotalLinea;
    private String nombreProducto;
    private Double precioUnidad;

    public LineaPedidoCompra() {
    }

    public LineaPedidoCompra(int id_producto, Double cantidad, Double importeTotalLinea, String nombreProducto, Double precioUnidad) {
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.importeTotalLinea = importeTotalLinea;
        this.nombreProducto = nombreProducto;
        this.precioUnidad = precioUnidad;
    }
    

    public LineaPedidoCompra(int idLinea_pedido, int id_pedido, int id_producto, double cantidad, double importe_total_linea) {
        this.idLinea_pedido = idLinea_pedido;
        this.id_pedido = id_pedido;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.importeTotalLinea = importe_total_linea;
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

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporteTotalLinea() {
        return importeTotalLinea;
    }

    public void setImporteTotalLinea(Double importeTotalLinea) {
        this.importeTotalLinea = importeTotalLinea;
    }

   

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(Double precioUnidad) {
        this.precioUnidad = precioUnidad;
    }
    
    
    
    
    
}
