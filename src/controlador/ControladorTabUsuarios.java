package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML
    private Button btnLimpiarForm;

    private ObservableList<Usuario> usuarios;
    private Usuario uSeleccionado;
    private ArrayList<String> errores;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTablaUsuarios();//se carga la tabla de usuarios        
        usuarioSeleccionado();//cuando se selecciona un usuario en la tabla        
        cargarCombo();//se carga el combobox de usuarios
        //array para los errores
        errores = new ArrayList<String>();
    }

    //método para insertar un usuario en la base de datos
    @FXML
    private void insertar(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String contrasenia = txtContrasenia.getText();
        String rolUsuario = (String) cmbRol.getValue();
        //se valida el formulario antes de insertarlo
        validarFormulario();

        if (!errores.isEmpty()) {
            String cadenaErrores = "";
            for (int i = 0; i < errores.size(); i++) {
                cadenaErrores += errores.get(i) + "\n";
            }
            Alert dialogoAlert = new Alert(Alert.AlertType.ERROR);
            dialogoAlert.setTitle("Informe errores");
            dialogoAlert.setHeaderText("Se encontraron los siguientes errores");
            dialogoAlert.setContentText(cadenaErrores);
            dialogoAlert.show();
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

    //método para actualizar los datos del usuario
    @FXML
    private void Actualizar(ActionEvent event) {
        String contrasenia = txtContrasenia.getText();
        String usuario = txtUsuario.getText();
        String rolUsuario = (String) cmbRol.getValue();
        if (uSeleccionado == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Seleccionar usuario");
            alert.setContentText("Debes seleccionar un usuario de la tabla para modificarlo.");
            alert.showAndWait();
        } else {
            Usuario uActualizado = new Usuario(uSeleccionado.getUsuarioId(), usuario, rolUsuario);
            if (!contrasenia.equals("")) {
                txtContrasenia.setDisable(true);
                //se valida el formulario
                validarFormulario();
                if (!errores.isEmpty()) {
                    String cadenaErrores = "";
                    for (int i = 0; i < errores.size(); i++) {
                        cadenaErrores += errores.get(i) + "\n";
                    }
                    Alert dialogoAlert = new Alert(Alert.AlertType.ERROR);
                    dialogoAlert.setTitle("Informe errores");
                    dialogoAlert.setHeaderText("Se encontraron los siguientes errores");
                    dialogoAlert.setContentText(cadenaErrores);
                    dialogoAlert.show();
                } else {
                    //se actualiza con contraseña cuando se modifica ese campo
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
                }

            } else {
                validarFormularioSinContrasenia();
                if (!errores.isEmpty()) {
                    String cadenaErrores = "";
                    for (int i = 0; i < errores.size(); i++) {
                        cadenaErrores += errores.get(i) + "\n";
                    }
                    Alert dialogoAlert = new Alert(Alert.AlertType.ERROR);
                    dialogoAlert.setTitle("Informe errores");
                    dialogoAlert.setHeaderText("Se encontraron los siguientes errores");
                    dialogoAlert.setContentText(cadenaErrores);
                    dialogoAlert.show();
                } else {
                    //se actualiza el usuario sin contraseña si el campo esta vacio
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
        }
    }

    //método para borrar un usuario seleccionado de la tabla
    @FXML
    private void borrar(ActionEvent event
    ) {

        Usuario uSeleccionado = tblUsuarios.getSelectionModel().getSelectedItem();

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

    //método para cargar los roles en el combobox
    private void cargarCombo() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Administrador", "Administrativo", "Operario");
        cmbRol.setPromptText("Rol");
        cmbRol.setItems(items);
    }

    //método para cargar los usuarios que hay en la base de datos en la tabla
    public void cargarTablaUsuarios() {
        colUsuario.setCellValueFactory(new PropertyValueFactory("nombreUsuario"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
        usuarios = Usuario.obtenerUsuarios();
        tblUsuarios.setItems(usuarios);
    }

    //método para limpiar el formulario de usuarios
    public void limpiar() {
        txtUsuario.clear();
        txtContrasenia.clear();
        cmbRol.getItems().clear();
        cargarCombo();//se carga el combobox
    }

    //método para escuchador de seleccionar un usuario de la tabla
    public void usuarioSeleccionado() {
        //funcion lambda para que seleccione de la tabla y rellene los campos
        tblUsuarios.setOnMouseClicked(event -> {
            uSeleccionado = tblUsuarios.getSelectionModel().getSelectedItem();
            txtUsuario.setText(uSeleccionado.getNombreUsuario());
            cmbRol.setValue(uSeleccionado.getRol());
        });
    }

    //metodo limpiar formulario al boton 
    @FXML
    private void limpiarForm(ActionEvent event) {
        limpiar();
    }

    //método para validar el formulario
    public void validarFormulario() {
        errores.clear();
        //valido que los campos no esten vacios
        if (txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty() || cmbRol.getValue() == null) {
            errores.add("Los campos tienen que rellenarse, es obligatorio.");
        }
        //pongo patron de contraseña
        String patronContrasenia = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
        if (!Pattern.matches(patronContrasenia, txtContrasenia.getText())) {
            errores.add("El campo contraseña tiene formato incorrecto,  la contraseña debe tener de 4 a 8 caracteres y debe contener números, letras minúsculas y mayúsculas..");
        }
        if (cmbRol.getValue() == null) {
            errores.add("Al usuario hay que asignarle un rol.");
        }
    }

    //método validar el formulario si la contraseña está vacia
    public void validarFormularioSinContrasenia() {
        errores.clear();
        //valido que los campos no esten vacios
        if (txtUsuario.getText().isEmpty() || cmbRol.getValue().equals("Rol")) {
            errores.add("Los campos tienen que rellenarse, es obligatorio.");
        }
    }
}
