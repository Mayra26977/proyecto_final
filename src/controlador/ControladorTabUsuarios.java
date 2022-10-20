package controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.StageStyle;
import modelo.Global;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class ControladorTabUsuarios implements Initializable {

    @FXML
    private TableView<Usuario> tblUsuarios;
    @FXML
    private TableColumn<Usuario, String> colUsuario;
    @FXML
    private TableColumn<Usuario, String> colRol;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtContrasenia;
    @FXML
    private ComboBox<String> cmbRol;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;

    private int posicionUsuarioTabla;
    private FXMLLoader loader = new FXMLLoader();
    private ObservableList<Usuario> usuarios;
    private Usuario uSeleccionado;
    @FXML
    private Button btnNuevo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTablaUsuarios();//se carga la tabla de usuarios        
        usuarioSeleccionado();//cuando se selecciona un usuario en la tabla
        cargarCombo();//se carga el combobox

    }

    @FXML
    private void insertar(ActionEvent event) {
        System.out.println("que cree y guarde en usuario");
        System.out.println(Global.usuarioLogueadoId);
        String usuario = txtUsuario.getText();
        String contrasenia = txtContrasenia.getText();
        String rolUsuario = (String) cmbRol.getValue();

        if (txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty() || cmbRol.getValue().equals("")) {
            // ventana de los datos no en blanco
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Insertar Usuario");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Rellene todos los campos por favor.");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaUsuarios();
        } else {
            Usuario.insertarUsuario(usuario, contrasenia, rolUsuario);
            // ventana de los datos se insertaron correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insercción de usuario");
            alert.setContentText("El usuario se introdujo en la tabla");
            alert.showAndWait();
            cargarTablaUsuarios();
            limpiar();
        }
    }

    @FXML
    private void Actualizar(ActionEvent event) {

        String contrasenia = txtContrasenia.getText();
        String usuario = txtUsuario.getText();
        String rolUsuario = (String) cmbRol.getValue();

        Usuario uActualizado = new Usuario(uSeleccionado.getUsuario_id(), usuario, rolUsuario);
        if (!contrasenia.equals("")) {
            //se actualiza el usuario y la contraseña
            Usuario.modificarUsuario(uActualizado, contrasenia);
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Actualizar Usuario");
            dialogoAlert.setHeaderText("Informacion actualización");
            dialogoAlert.setContentText("Se actualizo el usuario y la contraseña.");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarCombo();
            limpiar();
            cargarTablaUsuarios();

        } else {
            //se actualiza el usuario sin contraseña
            Usuario.modificarUsuario(uActualizado);
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Actualizar Usuario");
            dialogoAlert.setHeaderText("Informacion actualización");
            dialogoAlert.setContentText("Se actualizo el usuario.");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarCombo();
            limpiar();
            cargarTablaUsuarios();
        }
    }

    @FXML
    private void borrar(ActionEvent event) {

        Usuario uSeleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
//                posicionUsuarioTabla = tblUsuarios.getSelectionModel().getSelectedIndex();
//                System.out.println(posicionUsuarioTabla);
        System.out.println("que borren usuarios");
        System.out.println(Global.usuarioLogueadoId);

        if (uSeleccionado == null) {
            // ventana de hay que seleccionar usuario en la tabla si no no se puede borrar
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Borrar usuario");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Tiene que seleccionar un usuario");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaUsuarios();
        } else {
            //Ventana de informar que el usuario se elimino correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Borrado de usuario");
            alert.setHeaderText("Estas seguro de borrar el usuario?");
            alert.setContentText("El usuario se borrara");
            Optional<ButtonType> action = alert.showAndWait();
            //segun lo que respondas en el alert
            if (action.get() == ButtonType.OK) {
                Usuario.borrarUsuario(uSeleccionado);
                Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlert.setTitle("Borrar usuario");
                dialogoAlert.setHeaderText(null);
                dialogoAlert.setContentText("El usuario se borró correctamente");
                dialogoAlert.initStyle(StageStyle.UTILITY);
                dialogoAlert.showAndWait();
                cargarTablaUsuarios();
            } else {

            }
            limpiar();
            cargarTablaUsuarios();
        }
    }

    private void cargarCombo() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Administrador", "Administrativo", "Operario");
        cmbRol.setItems(items);
    }

    public void cargarTablaUsuarios() {

        colUsuario.setCellValueFactory(new PropertyValueFactory("nombre_usuario"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
        usuarios = Usuario.obtenerUsuarios();
        tblUsuarios.setItems(usuarios);
    }

    public void limpiar() {
        txtUsuario.clear();
        txtContrasenia.clear();
        cmbRol.getItems().clear();
        cargarCombo();//se carga el combobox
    }

    public void usuarioSeleccionado() {
        //funcion lambda para que seleccione de la tabla y rellene los textfield
        tblUsuarios.setOnMouseClicked(event -> {
            uSeleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
            txtUsuario.setText(uSeleccionado.getNombre_usuario());
            cmbRol.setValue(uSeleccionado.getRol());
        });
    }

    @FXML
    private void nuevo(ActionEvent event) {
        limpiar();
    }

}
