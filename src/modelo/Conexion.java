package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Mayra
 */
public class Conexion {

    private static Connection conexion;

    private static final String USUARIO = "backup21_mayrasql";
    //private static final String USUARIO = "root";
    private static final String CONTRASENIA = "kW]o?M0V]xb@Al}#(4";
    //private static final String CONTRASENIA = "";
    private static final String SERVIDOR = "backup21.web21.com.es";
    //private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306";
    private static final String NOMBRE_DB = "backup21_mayra";
    //private static final String NOMBRE_DB = "proyecto";

    private static final String url = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + NOMBRE_DB;

    //método para conectarte a la base de datos
    public static Connection obtenerConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(url, USUARIO, CONTRASENIA);
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setHeaderText(null);
//                alert.setTitle("Conexion");
//                alert.setContentText("Conexión establecidad");
//                alert.show();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conexion;
    }

    //método para cerrar la conexión con la base de datos
    public void cerrarConexion() throws SQLException {
        conexion.close();
        conexion = null;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Conexion");
        alert.setContentText("Conexión cerrada");
        alert.show();
    }
}
