package modelo;
import javafx.collections.ObservableList;

/**
 *
 * @author Mayra
 */
public class Grafica {
//métodos que seran llamados para rellenar los datos de la gráfica
    //obtener el total de ventas sumando el total de todas las lineas de venta que hay en la base de datos
    public static double obtenerTotalVentas() {
        ObservableList<LineaPedidoVenta> lineas = LineaPedidoVenta.obtenerLineasPedido();
        double total = 0;
        for (int i = 0; i < lineas.size(); i++) {
            total += lineas.get(i).getImporteTotalLinea();
        }
        return total;
    }

    //obtener el total de las compras de todas las lineas de compras que hay en la base de datos
    public static double obtenerTotalCompras() {
        ObservableList<LineaPedidoCompra> lineas = LineaPedidoCompra.obtenerLineasPedido();
        double total = 0;
        for (int i = 0; i < lineas.size(); i++) {
            total += lineas.get(i).getImporteTotalLinea();
        }
        return total;
    }
}
