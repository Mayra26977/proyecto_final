package controlador;

import com.mysql.cj.jdbc.Blob;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import modelo.Cliente;
import modelo.Conexion;
import modelo.Global;
import modelo.Producto;

/**
 * FXML Controller class
 *
 * @author Mayra
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
    @FXML
    private Button btnNuevo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        limpiar();
        cargarTablaProductos();
        productoSeleccionado();

    }

    @FXML
    private void insertar(ActionEvent event) throws IOException {
        Double precio = null;
        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        if (txtPrecio.getText().isEmpty()) {
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Campo obligatorio");
            dialogoAlert.setHeaderText("Informacion");
            dialogoAlert.setContentText("El campo precio tiene que estar relleno");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
        } else {
            precio = Double.parseDouble(txtPrecio.getText());
        }
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

            Producto.insertarProducto(new Producto(nombre, descripcion, precio, cantidad, imagenRecogida));
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
        Double precio = null, cantidad;

        String nombre = txtNombre.getText();
        if (txtNombre.getText().isEmpty()) {
            nombre = "";
        }
        String descripcion = txtDescripcion.getText();
        if (txtDescripcion.getText().isEmpty()) {
            descripcion = "";
        }
        //en caso de al actualizar este el campo del formulario vacio
        if (txtPrecio.getText().isEmpty()) {
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Campo obligatorio");
            dialogoAlert.setHeaderText("Informacion");
            dialogoAlert.setContentText("El campo precio tiene que estar relleno");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
        } else {
            precio = Double.parseDouble(txtPrecio.getText());
        }
        if (txtCantidad.getText().isEmpty()) {
            txtCantidad.setText("0.0");
            cantidad = Double.parseDouble(txtCantidad.getText());
        } else {
            cantidad = Double.parseDouble(txtCantidad.getText());
        }

        Image imagen = this.imagen.getImage();
        if (this.imagen.getImage().equals(null)) {
            this.imagen.setImage(null);
        }
        if (precio != null) {
            Producto pActualizado = new Producto(pSeleccionado.getId_producto(), nombre, descripcion, precio, cantidad, imagen);
            try {
                //se actualiza el cliente
                Producto.modificarProducto(pActualizado);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                if (Producto.modificarProducto(pActualizado) == 1) {
                    Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
                    dialogoAlert.setTitle("Actualizar Producto");
                    dialogoAlert.setHeaderText("Informacion actualización");
                    dialogoAlert.setContentText("Se actualizo el producto correctamente.");
                    dialogoAlert.initStyle(StageStyle.UTILITY);
                    dialogoAlert.showAndWait();
                    limpiar();
                    cargarTablaProductos();
                } else {
                    Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
                    dialogoAlert.setTitle("Actualizar Producto");
                    dialogoAlert.setHeaderText("Informacion actualización");
                    dialogoAlert.setContentText("El producto no se actualizo");
                    dialogoAlert.initStyle(StageStyle.UTILITY);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {

        }

    }

    @FXML
    private void borrar() {
        Producto pSeleccionado = tblProductos.getSelectionModel().getSelectedItem();

        if (pSeleccionado == null) {
            // ventana de hay que seleccionar usuario en la tabla si no no se puede borrar
            Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
            dialogoAlert.setTitle("Borrar cliente");
            dialogoAlert.setHeaderText(null);
            dialogoAlert.setContentText("Tiene que seleccionar un cliente de la tabla");
            dialogoAlert.initStyle(StageStyle.UTILITY);
            dialogoAlert.showAndWait();
            cargarTablaProductos();
        } else {
            //Ventana de informar que el usuario se elimino correctamente
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Borrar producto");
            alert.setHeaderText("Estas seguro de borrar el producto?");
            alert.setContentText("El producto se eliminará");
            Optional<ButtonType> action = alert.showAndWait();
            //segun lo que respondas en el alert
            if (action.get() == ButtonType.OK) {
                Producto.borrarProducto(pSeleccionado);
                Alert dialogoAlert = new Alert(Alert.AlertType.INFORMATION);
                dialogoAlert.setTitle("Borrar producto");
                dialogoAlert.setHeaderText(null);
                dialogoAlert.setContentText("El producto se borró correctamente");
                dialogoAlert.initStyle(StageStyle.UTILITY);
                dialogoAlert.showAndWait();
                cargarTablaProductos();
            } else {

            }
            limpiar();
            cargarTablaProductos();
        }
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

    @FXML
    private void nuevo(ActionEvent event) {
        limpiar();
    }



}
