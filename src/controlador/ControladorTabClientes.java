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

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class ControladorTabClientes implements Initializable {

    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn<Cliente, String> colNombre;
    @FXML
    private TableColumn<Cliente, String> colApellidos;
    @FXML
    private TableColumn<Cliente, String> colNif;
    @FXML
    private TableColumn<Cliente, String> colDireccion;
    @FXML
    private TableColumn<Cliente, String> colEmail;
    @FXML
    private TableColumn<Cliente, String> colTelefono;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtNif;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;

    private ObservableList<Cliente> clientes;
    private Cliente cSeleccionado;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTablaClientes();//se carga la tabla de usuarios  
        clienteSeleccionado();//metodo que añade escuchador a la tabla
    }
//método insertar cliente

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
            dialogoAlert.setTitle("Insertar cliente");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Rellene todos los campos por favor.");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaClientes();
        } else {
            Cliente.insertarCliente(nif, nombre, apellidos, direccion, email, telefono);
            // ventana de los datos se insertaron correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insercción de cliente");
            alert.setContentText("El cliente se introdujo en la tabla");
            alert.showAndWait();
            cargarTablaClientes();
            limpiar();
        }
    }
//método actualizar cliente

    @FXML
    private void Actualizar(ActionEvent event) {

        String nif = txtNif.getText();
        String nombre = txtNombre.getText();
        String apellidos = txtApellidos.getText();
        String direccion = txtDireccion.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();
        Cliente cActualizado = new Cliente(cSeleccionado.getId_cliente(), nif, nombre, apellidos, direccion, email, telefono);

        //se actualiza el cliente
        Cliente.modificarCliente(cActualizado);
        Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
        dialogoAlert.setTitle("Actualizar Cliente");
        dialogoAlert.setHeaderText("Informacion actualización");
        dialogoAlert.setContentText("Se actualizo el cliente correctamente.");
        dialogoAlert.initStyle(StageStyle.UTILITY);
        dialogoAlert.showAndWait();
        limpiar();
        cargarTablaClientes();
    }
//método borrar cliente

    @FXML
    private void borrar(ActionEvent event) {

        Cliente cSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

        if (cSeleccionado == null) {
            // ventana de hay que seleccionar usuario en la tabla si no no se puede borrar
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Borrar cliente");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Tiene que seleccionar un cliente de la tabla");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaClientes();
        } else {
            //Ventana de informar que el usuario se elimino correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Borrado de cliente");
            alert.setHeaderText("Estas seguro de borrar el cliente?");
            alert.setContentText("El cliente se eliminará");
            Optional<ButtonType> action = alert.showAndWait();
            //segun lo que respondas en el alert
            if (action.get() == ButtonType.OK) {
                Cliente.borrarCliente(cSeleccionado);
                Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlert.setTitle("Borrar cliente");
                dialogoAlert.setHeaderText(null);
                dialogoAlert.setContentText("El cliente se borró correctamente");
                dialogoAlert.initStyle(StageStyle.UTILITY);
                dialogoAlert.showAndWait();
                cargarTablaClientes();
            } else {

            }
            limpiar();
            cargarTablaClientes();
        }
    }
//metodo que actualiza la tabla de los clientes

    public void cargarTablaClientes() {

        colNif.setCellValueFactory(new PropertyValueFactory("nif"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
        colDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        clientes = Cliente.obtenerClientes();
        tblClientes.setItems(clientes);
    }
//método para limpiar el formulario de clientes

    public void limpiar() {

        txtNif.clear();
        txtNombre.clear();
        txtApellidos.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
    }

//método para que se rellenen los campos cuando se seleccione un cliente de la tabla
    public void clienteSeleccionado() {
        //funcion lambda para que seleccione de la tabla y rellene los textfield
        tblClientes.setOnMouseClicked((MouseEvent event) -> {
            cSeleccionado = tblClientes.getSelectionModel().getSelectedItem();

            txtNif.setText(cSeleccionado.getNif());
            txtNombre.setText(cSeleccionado.getNombre());
            txtApellidos.setText(cSeleccionado.getApellidos());
            txtDireccion.setText(cSeleccionado.getDireccion());
            txtEmail.setText(cSeleccionado.getEmail());
            txtTelefono.setText(cSeleccionado.getTelefono());

        });

    }

    @FXML
    private void nuevo(ActionEvent event) {
        limpiar();
    }
}
