package modelo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author maria.enriquez
 */
public class Utils {

    //metodo cerrar ventana
    public static void cerrarVentana(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

//metodo cerrar ventana

public static void abrirVentana(FXMLLoader loader ,Stage stage) {

        try {           
            
            Parent root;
            root = loader.load();
            Scene scene = new Scene(root);            
            stage = new Stage(StageStyle.DECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    
}
