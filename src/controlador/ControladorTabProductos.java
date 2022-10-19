package controlador;

import com.mysql.cj.jdbc.Blob;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import static javax.swing.Spring.width;
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
    @FXML
    private Button btnCargar;
    @FXML
    private HBox btnCargarImagen;
    @FXML
    private Button btnInsertar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnBorrar;

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
    private void insertar(ActionEvent event) {
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
            Producto producto = new Producto(nombre, descripcion, precio, cantidad, imagenRecogida);
            Producto.insertarProducto(producto);
            // ventana de los datos se insertaron correctamente
            if(Producto.insertarProducto(producto)){
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insercción de producto");
            alert.setContentText("El producto se introdujo en la tabla");
            alert.showAndWait();
            cargarTablaProductos();
            limpiar();
            }else {
                 Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insercción de producto");
            alert.setContentText("El producto no se introdujo en la base de datos");
            alert.showAndWait();
            }
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
        if (rutaArchivo != null) {
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
