package modelo;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class LineaOrdenFabricacion {
    private int idLineaOf;
    private int idOrdenFabricacion;
    private int idProducto;
    private double cantidad;
    private String nombre;

    public LineaOrdenFabricacion() {
    }

    public LineaOrdenFabricacion(int idLineaOf, int idOrdenFabricacion, int idProducto, double cantidad, String nombre) {
        this.idLineaOf = idLineaOf;
        this.idOrdenFabricacion = idOrdenFabricacion;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public LineaOrdenFabricacion(int idProducto, double cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public LineaOrdenFabricacion(int idOrdenFabricacion, int idProducto, double cantidad) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //obtener las lineas concretas de una orden de fabricación
    public static ObservableList obtenerLineasOfConcreta(OrdenFabricacion of) {
        ObservableList<LineaOrdenFabricacion> lineas = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.linea_of WHERE id_of =" + of.getIdOf())) {
            while (result.next()) {
                int idProducto = result.getInt("id_producto");
                Double cantidad = result.getDouble("cantidad");
                
                lineas.add(new LineaOrdenFabricacion(idProducto, cantidad));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener las lineas de la orden de fabricación");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return lineas;
    }

    //obtener lineas en la of
    public static ObservableList obtenerLineasOf() {
        ObservableList<LineaOrdenFabricacion> lineas = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.linea_of WHERE id_of IN(SELECT id_of FROM backup21_mayra.orden_fabricacion WHERE eliminado = 0)")) {
            while (result.next()) {
                int idOf = result.getInt("id_of");
                int idProducto = result.getInt("id_producto");                
                Double cantidad = result.getDouble("cantidad");
                lineas.add(new LineaOrdenFabricacion( idOf, idProducto,cantidad));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener las lineas de las ordenes de fabricación");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return lineas;
    }   
}
