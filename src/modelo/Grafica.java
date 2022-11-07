package modelo;

import javafx.collections.ObservableList;

/**
 *
 * @author Mayra
 */
public class Grafica {
    
    public static double obtenerTotalVentas(){
         
        ObservableList<LineaPedidoVenta>lineas = LineaPedidoVenta.obtenerLineasPedido();
        double total = 0;
        
        for (int i = 0; i < lineas.size(); i++) {
            total += lineas.get(i).getImporteTotalLinea();
        }
        
        return total;
        
        
    }
    
    public static double obtenerTotalCompras(){
        
        ObservableList<LineaPedidoCompra>lineas = LineaPedidoCompra.obtenerLineasPedido();
        double total = 0;
        
        for (int i = 0; i < lineas.size(); i++) {
            total += lineas.get(i).getImporteTotalLinea();
        }
        
        return total;
    }
}
