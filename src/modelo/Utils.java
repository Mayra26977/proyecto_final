package modelo;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class Utils {

    //metodo cerrar ventana
    public static void cerrarVentana(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        //lanza el evento de que la ventana se va a cerrar lo hice para recargar la tabla cuando se guada un pedido de compra o de venta o una of y se cierra la ventana
        //stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }

    //método cerrar ventana
    public static void abrirVentana(FXMLLoader loader, Stage stage) {
        try {
            Parent root;
            root = loader.load();
            Scene scene = new Scene(root);
            stage = new Stage(StageStyle.DECORATED);
            stage.setScene(scene);
            //favicon en las ventanas de la aplicación con el logo de la empresa
            stage.getIcons().add(new Image("imagenes/4.png"));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
