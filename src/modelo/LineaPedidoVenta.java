package modelo;

/**
 *
 * @author Mayra
 */
public class LineaPedidoVenta {
    
    private int idLineaPedido;
    private int idPedido;
    private int idProducto;
    private double cantidad;
    private double importeTotalLinea;
    private String nombreProducto;
    private Double precioUnidad;

    public LineaPedidoVenta() {
    }

    public LineaPedidoVenta(int idLineaPedido, int idPedido, int idProducto, double cantidad, double importeTotalLinea) {
        this.idLineaPedido = idLineaPedido;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.importeTotalLinea = importeTotalLinea;
    }
     public LineaPedidoVenta(int idProducto, double cantidad, double importeTotalLinea, String nombreProducto, double precioUnidad) {
         
         this.idProducto = idProducto;
         this.cantidad = cantidad;
         this.importeTotalLinea = importeTotalLinea;
         this.nombreProducto = nombreProducto;
         this.precioUnidad = precioUnidad;
         
     }
    

    public int getIdLineaPedido() {
        return idLineaPedido;
    }

    public void setIdLineaPedido(int idLineaPedido) {
        this.idLineaPedido = idLineaPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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

    public double getImporteTotalLinea() {
        return importeTotalLinea;
    }

    public void setImporteTotalLinea(double importeTotalLinea) {
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
    public static void obtenerLineasPedidoConcreto(PedidoVenta pedido){
        
    }
    
    
    
    
}
