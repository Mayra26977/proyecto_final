package controlador;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Conexion;
import modelo.LineaOrdenFabricacion;
import modelo.OrdenFabricacion;
import modelo.Producto;
import modelo.Usuario;
import modelo.Utils;

/**
 * FXML Controller class
 *
 * @author maria.enriquez
 */
public class ControladorVistaOrdenFabricacion implements Initializable {

    @FXML
    private TableView<LineaOrdenFabricacion> tblLineasOf;
    @FXML
    private TableColumn<LineaOrdenFabricacion, String> colProducto;
    @FXML
    private TableColumn<LineaOrdenFabricacion, Double> colCantidad;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextField txtOfId;
    @FXML
    private Button btnAniadirLinea;
    @FXML
    private Button btnEliminarLinea;
    @FXML
    private Button btnVolver;
    @FXML
    private ComboBox<Usuario> cmbOperarios;
    @FXML
    private ComboBox<Producto> cmbProductos;
    @FXML
    private DatePicker fechaInicio;
    @FXML
    private DatePicker fechaFin;

    private OrdenFabricacion of;
    private Usuario operarioSeleccionado;
    private Producto productoSeleccionado;
    private ObservableList<Usuario> operarios;
    private ObservableList<Producto> productos;
    private ObservableList<LineaOrdenFabricacion> lineas = FXCollections.observableArrayList();

    public void setOf(OrdenFabricacion of) {
        this.of = of;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarLineas();
        cargarOperarios();
        cargarProductos();
        txtCantidad.textProperty().addListener((observable, oldValue, newValue) -> {
            //comprueba que solo puedas escribir numeros enteros
            if (!newValue.matches("\\d*")) {
                txtCantidad.setText(newValue.replaceAll("[^\\d*]", ""));
            }
            btnAniadirLinea.setDisable(txtCantidad.getText().equals(""));
            btnEliminarLinea.setDisable(tblLineasOf.getSelectionModel().getSelectedItem() == null);

        });
    }

    @FXML
    private void aniadirLinea(ActionEvent event) {
        productoSeleccionado = cmbProductos.getValue();
        System.out.println(productoSeleccionado);
        double cantidad = Double.parseDouble(txtCantidad.getText());
        LineaOrdenFabricacion linea = new LineaOrdenFabricacion(productoSeleccionado.getNombre(), Double.valueOf(cantidad));
        lineas.add(linea);
    }

    @FXML
    private void salirOrdenFabricacion(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir de orden de fabricación");
        alert.setHeaderText("Estas seguro de salir?");
        alert.setContentText("Los datos se perderan");
        Optional<ButtonType> action = alert.showAndWait();
        //segun lo que respondas en el alert
        if (action.get() == ButtonType.OK) {

            modelo.Utils.cerrarVentana(event);

        }
    }

    @FXML
    private void guardarOrdenFabricacion(ActionEvent event) {
        try {
            Timestamp fechaInicial = null;
            Timestamp fechaFinal = null;
            //hacer transaccion crear of
            //creo la of para obtener el id de la of
            //añado a las lineas el id de la of insertar todas las lineas y los demas campos            
            Conexion.obtenerConexion().setAutoCommit(false);
            //pasar la hora de los datepicker para poder insertarla en la of

            if (fechaInicio.getValue() == null) {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fecha vacia");
                alert.setHeaderText("Rellenar fecha de inicio");
                alert.setContentText("La fecha no puede estar vacia");
                alert.showAndWait();
            } else if (fechaFin.getValue() == null) {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fecha vacia");
                alert.setHeaderText("Rellenar fecha de fin");
                alert.setContentText("La fecha no puede estar vacia");
                alert.showAndWait();
            } else {
                LocalDate fechaObtenidaInicial = fechaInicio.getValue();
                System.out.println(fechaObtenidaInicial);
                LocalDateTime localDateTimeI = fechaObtenidaInicial.atTime(LocalTime.now());
                System.out.println(localDateTimeI);
                fechaInicial = Timestamp.valueOf(localDateTimeI);
                System.out.println(fechaInicial);
                LocalDate fechaObtenidaFinal = fechaFin.getValue();
                System.out.println(fechaObtenidaInicial);
                LocalDateTime localDateTimeF = fechaObtenidaFinal.atTime(LocalTime.now());
                System.out.println(localDateTimeF);
                fechaFinal = Timestamp.valueOf(localDateTimeF);
                System.out.println(fechaFinal);
            }

            if (fechaInicio != null && fechaFin != null) {
                Usuario operario = cmbOperarios.getValue();
                OrdenFabricacion of = new OrdenFabricacion(fechaInicial, fechaFinal,operario.getUsuarioId());
                OrdenFabricacion.insertarOrdenFabricacion(of, fechaInicial, fechaFinal, operario, new ArrayList<LineaOrdenFabricacion>(lineas));
                Utils.cerrarVentana(event);
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Insercción orden de fabricación ");
                alert.setHeaderText("Orden de fabricación insertada");
                alert.setContentText("La orden de fabricación se inserto correctamente");
                alert.showAndWait();
            }

        } catch (SQLException ex) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Insercción orden de fabricación ");
            alert.setHeaderText("Orden de fabricación no insertada");
            alert.setContentText("La orden de fabricación no se pudo insertar");
            alert.showAndWait();
            ex.printStackTrace();
        }

    }

    @FXML
    private void eliminarLinea(ActionEvent event) {

        LineaOrdenFabricacion lineaSeleccionada = tblLineasOf.getSelectionModel().getSelectedItem();
        lineas.remove(borrarSeleccion());
        tblLineasOf.setItems(lineas);
        cargarLineas();
    }

    @FXML
    private void limpiarFormulario(ActionEvent event) {
    }

    @FXML
    private void seleccionarOperario(ActionEvent event) {
        this.operarioSeleccionado = cmbOperarios.getSelectionModel().getSelectedItem();
        if (this.operarioSeleccionado == null) {
            cmbOperarios.setDisable(false);
        } else {
            cmbOperarios.setDisable(true);
        }
    }

    private void cargarOperarios() {
        operarios = Usuario.obtenerUsuariosOperario();
        cmbOperarios.setItems(operarios);
    }

    @FXML
    private void seleccionarProducto(ActionEvent event) {
        this.productoSeleccionado = cmbProductos.getSelectionModel().getSelectedItem();

    }

    private void cargarProductos() {
        productos = Producto.obtenerProductos();
        cmbProductos.setItems(productos);
    }

    private void cargarLineas() {
        colProducto.setCellValueFactory(new PropertyValueFactory("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory("cantidad"));
        tblLineasOf.setItems(lineas);
    }

    public int borrarSeleccion() {
        tblLineasOf.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return tblLineasOf.getSelectionModel().getSelectedIndex();
    }
    
//    public void recuperarPedido() {
//        ObservableList<LineaPedidoCompra> lineasPedidoRecuperado = FXCollections.observableArrayList();
//        btnAniadirProd.setDisable(true);
//        btnEliminarLinea.setDisable(true);
//        btnGuardar.setDisable(true);
//        btnLimpiar.setDisable(true);
//        tblProductos.setDisable(true);
//        txtUnidades.setDisable(true);
//        lineas = LineaPedidoCompra.obtenerLineasPedidoConcreto(pedidoCompra);
//
//        txtIDPedido.setText(String.valueOf(pedidoCompra.getIdPedido()));
//        fecha.setValue(pedidoCompra.getFecha().toLocalDateTime().toLocalDate());
//        Proveedor proveedor = Proveedor.obtenerProveedorPorId(pedidoCompra.getIdProveedor());
//        cmbProveedores.setValue(proveedor);
//        //cmbClientes.setDisable(true);
//        txtTotal.setText(String.valueOf(pedidoCompra.getTotalPedido()));
//        cargarLineas();
//
//    }

}
