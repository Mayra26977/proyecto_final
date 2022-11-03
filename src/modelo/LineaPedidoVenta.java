package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    private LineaPedidoVenta(int idLineaPedido, String nombre, double precio, Double precioTotalLinea, Double unidades) {
        this.idLineaPedido = idLineaPedido;
        this.nombreProducto = nombre;
        this.idPedido = idPedido;
        this.precioUnidad = precio;
        this.importeTotalLinea = precioTotalLinea;
        this.cantidad = unidades;
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

    public static ObservableList obtenerLineasPedidoConcreto(PedidoVenta pedido) {
         
        ObservableList<LineaPedidoVenta> lineas = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.linea_pedido_venta WHERE id_pedido_venta =" + pedido.getIdPedido())) {
            while (result.next()) {

                int idLinea = result.getInt("id_linea_pedido_venta");
                int idProducto = result.getInt("id_producto");
                int idPedido = result.getInt("id_pedido_venta");
                Double importeTotalLinea = result.getDouble("precio_total_linea_pedido_venta");
                Double cantidad = result.getDouble("unidades");

                Producto prod = Producto.obtenerProductoPorId(idProducto);                  
                //LineaPedidoVenta linea = new LineaPedidoVenta(idProducto, cantidad, importeTotalLinea, prod.getNombre(), prod.getPrecio());
                lineas.add(new LineaPedidoVenta(idProducto, cantidad, importeTotalLinea, prod.getNombre(), prod.getPrecio()));  
                
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener las lineas del pedido");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return lineas;
    }
}
