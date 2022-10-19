package controlador;

import com.mysql.cj.jdbc.Blob;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import modelo.Cliente;
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
    private TableColumn<Producto, Blob> colImagen;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TextField txtCantidad;

    private ObservableList<Producto> productos;
    @FXML
    private ImageView imagen;

    private Producto pSeleccionado;
    private FileChooser filechooser;
    private File rutaArchivo;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        cargarTablaProductos();
        productoSeleccionado();

    }

    @FXML
    private void insertar() {
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        Double precio = Double.parseDouble(txtPrecio.getText());
        Double cantidad = Double.parseDouble(txtCantidad.getText());
        Image imagenRecogida = imagen.getImage();
        

        if (txtNombre.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty()
                || txtCantidad.getText().isEmpty()) {
            // ventana de los datos no en blanco
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Insertar Producto");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Rellene todos los campos por favor.");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaProductos();
        } else {
            Producto.insertarProducto(nombre, descripcion, precio, cantidad, imagenRecogida);
            // ventana de los datos se insertaron correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insercción de producto");
            alert.setContentText("El producto se introdujo en la tabla");
            alert.showAndWait();
            cargarTablaProductos();
            limpiar();
        }
    }

    @FXML
    private void Actualizar() {
    }

    @FXML
    private void borrar() {
    }

    public void cargarTablaProductos() {
        productos = Producto.obtenerProductos();
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        //no muestro la columna de la imagen aunque si esta para cargarla de la bd
        colImagen.setCellValueFactory(new PropertyValueFactory<Producto, Blob>("imagen"));
        tblProductos.setItems(productos);
    }

    //método para que se rellenen los campos cuando se seleccione un producto de la tabla
    public void productoSeleccionado() {
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

    @FXML
    private void filechooser() {
        
                //abre dialogo de busqueda
                filechooser = new FileChooser();
                filechooser.setTitle("Selecciona una imagen");
                //null para que selecciones la carpeta donde tengas las imagenes de los productos 
                rutaArchivo = filechooser.showOpenDialog(null);
                //cuando selecciones la imagen se cambiará en el ImageView estará lista para guardarse
                if(rutaArchivo != null){
                    imagen.setImage(new Image(rutaArchivo.toURI().toString()));
                }
        
    }
    //método para limpiar el formulario de productos

    public void limpiar() {

        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        imagen.setImage(null);
        
    }

}
