package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author maria.enriquez
 */
public class Conexion {

    private static Connection conexion;

    private static final String USUARIO = "RFJ3qnBgMz";
    private static final String CONTRASENIA = "NalD47cqVk";
    private static final String SERVIDOR = "remotemysql.com";
    private static final String PUERTO = "3306";
    private static final String NOMBRE_DB = "RFJ3qnBgMz";

    private static final String url = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + NOMBRE_DB;

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
