package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Producto;
import modelo.Proveedor;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class ControladorPedidoCompra implements Initializable {

    @FXML
    private DatePicker fecha;
    @FXML
    private TextField txtIDPedido;
    @FXML
    private TextField txtTotal;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAniadirProd;
    @FXML
    private ComboBox<Proveedor> cmbProveedores;
    @FXML
    private TableColumn<?, ?> tblLineas;
    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TextField txtUnidades;
    @FXML
    private Button btnEliminarLinea;

    private ObservableList<Proveedor> proveedores;
    private ObservableList<Producto> productos;
    private Proveedor proveedorSelec;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Hola");
        cargarProveedores();
        txtUnidades.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Adios");
            //comprueba que solo puedas escribir numeros enteros
            if (!newValue.matches("\\d*")) {
                txtUnidades.setText(newValue.replaceAll("[^\\d*]", ""));
            }
            btnAniadirProd.setDisable(txtUnidades.getText().equals(""));

        });

    }

    private void cargarProveedores() {
        
        proveedores = Proveedor.obtenerProveedores();
        cmbProveedores.setItems(proveedores);

    }

    @FXML
    private void seleccionar(ActionEvent event) {
        this.proveedorSelec = cmbProveedores.getSelectionModel().getSelectedItem();
        cargarProductos();

    }

    private void cargarProductos() {
        productos = Producto.obtenerProductosProveedor(this.proveedorSelec.getId_proveedor());
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tblProductos.setItems(productos);
    }

    @FXML
    private void aniadirLinea(ActionEvent event) {
    }

    private void comprobarUnidades() {

    }

}
