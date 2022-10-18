package controlador;

import com.mysql.cj.jdbc.Blob;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Producto;

/**
 * FXML Controller class
 *
 * @author maria.enriquez
 */
public class ControladorTabProductos implements Initializable {

    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn<Producto, String> colNombre;
    @FXML
    private TableColumn<Producto, String> colDescripcion;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableColumn<Producto, Double> colCantidad;
    @FXML
    private TableColumn<Producto, Image> colImagen;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtCantidad;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;

    private ObservableList<Producto> productos;
    @FXML
    private ImageView imagen;
    
    Producto pSeleccionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            // TODO
            cargarTablaProductos();
            clienteSeleccionado();
            
    }

    @FXML
    private void insertar(ActionEvent event) {
    }

    @FXML
    private void Actualizar(ActionEvent event) {
    }

    @FXML
    private void borrar(ActionEvent event) {
    }

    public void cargarTablaProductos() {
        productos = Producto.obtenerProductos();
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colImagen.setCellValueFactory(new PropertyValueFactory<Producto, Image>("imagen"));
        
        //productos = Producto.obtenerProductos();
        tblProductos.setItems(productos);
    }
    //método para que se rellenen los campos cuando se seleccione un cliente de la tabla
    public void clienteSeleccionado() {
        //funcion lambda para que seleccione de la tabla y rellene los textfield
        tblProductos.setOnMouseClicked((MouseEvent event) -> {
            pSeleccionado = tblProductos.getSelectionModel().getSelectedItem();

            txtNombre.setText(pSeleccionado.getNombre());
            txtDescripcion.setText(pSeleccionado.getDescripcion());
            txtPrecio.setText(String.valueOf(pSeleccionado.getPrecio()));
            txtCantidad.setText(String.valueOf(pSeleccionado.getCantidad()));
            imagen.setImage(pSeleccionado.getImagenProducto());

        });

    }
  
}
