package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static ObservableList obtenerLineasPedidoConcreto(PedidoCompra pedido) {

        ObservableList<LineaPedidoCompra> lineas = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.linea_pedido_compra WHERE id_pedido_compra =" + pedido.getIdPedido())) {
            while (result.next()) {

                int idLinea = result.getInt("id_linea_pedido_compra");
                int idProducto = result.getInt("id_producto");
                int idPedido = result.getInt("id_pedido_compra");
                Double importeTotalLinea = result.getDouble("precio_total_linea_pedido_compra");
                Double cantidad = result.getDouble("unidades");

                Producto prod = Producto.obtenerProductoPorId(idProducto);
                //LineaPedidoVenta linea = new LineaPedidoVenta(idProducto, cantidad, importeTotalLinea, prod.getNombre(), prod.getPrecio());
                lineas.add(new LineaPedidoCompra(idProducto, cantidad, importeTotalLinea, prod.getNombre(), prod.getPrecio()));

            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener las lineas del pedido");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return lineas;
    }

    public static ObservableList obtenerLineasPedido() {

        ObservableList<LineaPedidoCompra> lineas = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.linea_pedido_compra WHERE id_pedido_compra IN(SELECT id_pedido_compra FROM pedidos_compras WHERE eliminado = 0)" )) {
            while (result.next()) {
                
                int idLinea = result.getInt("id_linea_pedido_compra");
                int idProducto = result.getInt("id_producto");
                int idPedido = result.getInt("id_pedido_compra");
                Double importeTotalLinea = result.getDouble("precio_total_linea_pedido_compra");
                Double cantidad = result.getDouble("unidades");

                Producto prod = Producto.obtenerProductoPorId(idProducto);
                //LineaPedidoVenta linea = new LineaPedidoVenta(idProducto, cantidad, importeTotalLinea, prod.getNombre(), prod.getPrecio());
                lineas.add(new LineaPedidoCompra(idProducto, cantidad, importeTotalLinea, prod.getNombre(), prod.getPrecio()));

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
