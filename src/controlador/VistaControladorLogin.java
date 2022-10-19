package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class VistaControladorLogin implements Initializable {

    @FXML
    private VBox panelDatos;
    @FXML
    private Label lblUsuario;
    @FXML
    private TextField txtUsuario;
    @FXML
    private Label lblContrasenia;
    @FXML
    private PasswordField txtContrasenia;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnSalir;

    private String usuario;
    private String contrasenia;
    private Stage stage;
    private FXMLLoader loader;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnLogin.setDisable(true);
        txtContrasenia.setDisable(true);
    }
//se van habilitando botones y campos de texto según se van rellenando datos
    @FXML
    private void habilitarBoton(KeyEvent event) {
        usuario = txtUsuario.getText();
        contrasenia = txtContrasenia.getText();
        if (usuario.equals("")) {
            txtContrasenia.setDisable(true);
        } else {
            txtContrasenia.setDisable(false);
        }
        if (usuario.equals("") || contrasenia.equals("")) {
            btnLogin.setDisable(true);
        } else if (!(usuario.equals("") && contrasenia.equals(""))) {
            btnLogin.setDisable(false);
        }
    }

    @FXML
    private void entrar(ActionEvent event) {

        if (Usuario.obtenerUsuarioLogueado(txtUsuario.getText(), txtContrasenia.getText())) {
            //si se introduce un usuario y contraseña que coinciden en la bd 
            loader = new FXMLLoader(getClass().getResource("/vista/vistaPrincipal.fxml"));
            modelo.Utils.abrirVentana(loader, stage);
            modelo.Utils.cerrarVentana(event);

        } else {
            //si algún dato es erroneo 
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Comprobación usuario");
            dialogoAlert.setHeaderText("Datos erroneos");
            dialogoAlert.setContentText("Usuario o contraseña incorrecto");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
        }
    }

    @FXML
    private void salir(ActionEvent event) {
        //cierra la aplicacion
        System.exit(0);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
