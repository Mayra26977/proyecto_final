package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.LineaOrdenFabricacion;
import modelo.ProductoOrdenFabricacion;

/**
 * FXML Controller class
 *
 * @author maria.enriquez
 */
public class ControladorOrdenFabricacion implements Initializable {

    @FXML
    private TableView<ProductoOrdenFabricacion> tblOfs;
    @FXML
    private TableColumn<ProductoOrdenFabricacion, ?> colOf;
    @FXML
    private TableColumn<ProductoOrdenFabricacion, ?> colFechaInicio;
    @FXML
    private TableColumn<ProductoOrdenFabricacion, ?> colFechaFin;
    @FXML
    private TableColumn<ProductoOrdenFabricacion, ?> colOperario;
    @FXML
    private TableView<LineaOrdenFabricacion> tblLineasOf;
    @FXML
    private TableColumn<LineaOrdenFabricacion, ?> colProducto;
    @FXML
    private TableColumn<LineaOrdenFabricacion, ?> colCantidad;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnAniadirLinea;
    @FXML
    private Button btnEliminarLinea;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnModi;
    @FXML
    private Button btnBorrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void aniadirLinea(ActionEvent event) {
    }

    @FXML
    private void eliminarLinea(ActionEvent event) {
    }

    @FXML
    private void insertar(ActionEvent event) {
    }

    @FXML
    private void modificar(ActionEvent event) {
    }

    @FXML
    private void borrar(ActionEvent event) {
    }

}
