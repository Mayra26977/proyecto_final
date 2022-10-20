package controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import modelo.Cliente;
import modelo.Proveedor;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class ControladorTabProveedores implements Initializable {

    @FXML
    private TableView<Proveedor> tblProveedores;
    @FXML
    private TableColumn<Proveedor, String> colNif;
    @FXML
    private TableColumn<Proveedor, String> colNombre;
    @FXML
    private TableColumn<Proveedor, String> colApellidos;
    @FXML
    private TableColumn<Proveedor, String> colDireccion;
    @FXML
    private TableColumn<Proveedor, String> colEmail;
    @FXML
    private TableColumn<Proveedor, String> colTelefono;
    @FXML
    private TextField txtNif;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;

    private ObservableList<Proveedor> proveedores;
    private Proveedor pSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTablaProveedores();//se carga la tabla de usuarios  
        proveedorSeleccionado();//metodo que añade escuchador a la tabla
    }

    @FXML
    private void nuevo(ActionEvent event) {
        limpiar();
    }

    //método insertar proveedor
    @FXML
    private void insertar(ActionEvent event) {
        String nif = txtNif.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String direccion = txtDireccion.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();

        if (txtNif.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty()
                || txtDireccion.getText().isEmpty() || txtEmail.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
            // ventana de los datos no en blanco
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Insertar proveedor");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Rellene todos los campos por favor.");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaProveedores();
        } else {
            Proveedor.insertarProveedor(nif, nombre, apellidos, direccion, email, telefono);
            // ventana de los datos se insertaron correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insercción de proveedor");
            alert.setContentText("El proveedor se introdujo en la tabla");
            alert.showAndWait();
            cargarTablaProveedores();
            limpiar();
        }
    }

   //método actualizar proveedor

    @FXML
    private void Actualizar(ActionEvent event) {

        String nif = txtNif.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String direccion = txtDireccion.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        Proveedor pActualizado = new Proveedor(pSeleccionado.getId_proveedor(), nif, nombre, apellidos, direccion, email, telefono);

        //se actualiza el proveedor
        Proveedor.modificarProveedor(pActualizado);
        Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
        dialogoAlert.setTitle("Actualizar proveedor");
        dialogoAlert.setHeaderText("Informacion actualización");
        dialogoAlert.setContentText("Se actualizo el proveedor correctamente.");
        dialogoAlert.initStyle(StageStyle.UTILITY);
        dialogoAlert.showAndWait();
        limpiar();
        cargarTablaProveedores();
    }

//método borrar proveedor

    @FXML
    private void borrar(ActionEvent event) {

        Proveedor pSeleccionado = tblProveedores.getSelectionModel().getSelectedItem();

        if (pSeleccionado == null) {
            // ventana de hay que seleccionar usuario en la tabla si no no se puede borrar
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Borrar proveedor");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Tiene que seleccionar un proveedor de la tabla");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaProveedores();
        } else {
            //Ventana de informar que el usuario se elimino correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Borrado de proveedor");
            alert.setHeaderText("Estas seguro de borrar el proveedor?");
            alert.setContentText("El proveedor se eliminará");
            Optional<ButtonType> action = alert.showAndWait();
            //segun lo que respondas en el alert
            if (action.get() == ButtonType.OK) {
                Proveedor.borrarProveedor(pSeleccionado);
                Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlert.setTitle("Borrar proveedor");
                dialogoAlert.setHeaderText(null);
                dialogoAlert.setContentText("El proveedor se borró correctamente");
                dialogoAlert.initStyle(StageStyle.UTILITY);
                dialogoAlert.showAndWait();
                cargarTablaProveedores();
            } else {

            }
            limpiar();
            cargarTablaProveedores();
        }
    }
    //metodo que actualiza la tabla de los proveedores

    public void cargarTablaProveedores() {

        colNif.setCellValueFactory(new PropertyValueFactory("nif"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        proveedores = Proveedor.obtenerProveedores();
        tblProveedores.setItems(proveedores);
    }
//método para limpiar el formulario de proveedores

    public void limpiar() {

        txtNif.clear();
        txtNombre.clear();
        txtApellidos.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
    }

//método para que se rellenen los campos cuando se seleccione un proveedor de la tabla
    public void proveedorSeleccionado() {
        //funcion lambda para que seleccione de la tabla y rellene los textfield
        tblProveedores.setOnMouseClicked((MouseEvent event) -> {
            pSeleccionado = tblProveedores.getSelectionModel().getSelectedItem();

            txtNif.setText(pSeleccionado.getNif());
            txtNombre.setText(pSeleccionado.getNombre());
            txtApellidos.setText(pSeleccionado.getApellidos());
            txtDireccion.setText(pSeleccionado.getDireccion());
            txtEmail.setText(pSeleccionado.getEmail());
            txtTelefono.setText(pSeleccionado.getTelefono());

        });

    }

}
