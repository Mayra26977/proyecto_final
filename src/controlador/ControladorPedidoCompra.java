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
import modelo.LineaPedidoCompra;
import modelo.PedidoCompra;
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
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn<Producto, String> colNombreProducto;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TextField txtUnidades;
    @FXML
    private Button btnEliminarLinea;
    @FXML
    private TableColumn<LineaPedidoCompra, Double> colPrecioUnidad;
    @FXML
    private TableColumn<LineaPedidoCompra, Double> colUnidades;
    @FXML
    private TableColumn<LineaPedidoCompra, Double> colTotalLinea;
    @FXML
    private TableView<LineaPedidoCompra> tblLineas;
    @FXML
    private TableColumn<LineaPedidoCompra, String> colNombreLinea;
    private ObservableList<Proveedor> proveedores;
    private ObservableList<Producto> productos;
    private Proveedor proveedorSelec;
    private ObservableList<LineaPedidoCompra> lineas = FXCollections.observableArrayList();
    @FXML
    private Button btnLimpiar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLineas();
        cargarProveedores();
        txtTotal.setText("0.0");
        txtUnidades.textProperty().addListener((observable, oldValue, newValue) -> {
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
        cmbProveedores.setDisable(true);
        if (!proveedorSelec.equals(null)) {
            cargarProductos();
        }
    }

    private void cargarProductos() {
        if (!proveedorSelec.equals(null)) {
            productos = Producto.obtenerProductosProveedor(this.proveedorSelec.getId_proveedor());
        }
        PropertyValueFactory p = new PropertyValueFactory("nombre");
        colNombreProducto.setCellValueFactory(p);
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tblProductos.setItems(productos);
    }

    @FXML
    private void aniadirLinea(ActionEvent event) {
        Producto producto = tblProductos.getSelectionModel().getSelectedItem();
        int cantidad = Integer.parseInt(txtUnidades.getText());
        LineaPedidoCompra linea = new LineaPedidoCompra(producto.getId_producto(), Double.valueOf(cantidad), cantidad * producto.getPrecio(), producto.getNombre(), producto.getPrecio());
        lineas.add(linea);
            Double totalPedido = Double.parseDouble(txtTotal.getText()) + linea.getImporteTotalLinea();
            txtTotal.setText(String.valueOf(totalPedido));
      

    }

    private void cargarLineas() {
        colNombreLinea.setCellValueFactory(new PropertyValueFactory("nombreProducto"));
        colUnidades.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colTotalLinea.setCellValueFactory(new PropertyValueFactory("importeTotalLinea"));
        colPrecioUnidad.setCellValueFactory(new PropertyValueFactory("precioUnidad"));
        tblLineas.setItems(lineas);
    }

    @FXML
    private void guardarPedidoCompra(ActionEvent event) {
        try {
            //hacer transaccion crear pedido
            //creo el pedido para obtener el id del pedido
            //añado a las lineas el id del pedido insertar todas las lineas y los demas campos            
            Conexion.obtenerConexion().setAutoCommit(false);
            //pasar la hora del datepicker para poder insertarla en el pedido
            LocalDate fechaObtenida = fecha.getValue();
            System.out.println(fechaObtenida);
            LocalDateTime localDateTime = fechaObtenida.atTime(LocalTime.now());
            System.out.println(localDateTime);
            Timestamp fechaPedido = Timestamp.valueOf(localDateTime);
            System.out.println(fechaPedido);
            Proveedor proveedor = cmbProveedores.getValue();
            PedidoCompra pedido = new PedidoCompra(fechaPedido, proveedor.getId_proveedor(), Double.parseDouble(txtTotal.getText()));

            PedidoCompra.insertarPedidoCompra(pedido, fechaPedido, proveedor, new ArrayList<LineaPedidoCompra>(lineas));
            limpiarVista();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void salirPedidoCompra(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir pedido compra");
        alert.setHeaderText("Estas seguro de salir?");
        alert.setContentText("Los datos se perderan");
        Optional<ButtonType> action = alert.showAndWait();
        //segun lo que respondas en el alert
        if (action.get() == ButtonType.OK) {

            modelo.Utils.cerrarVentana(event);

        } else {

        }

    }

    @FXML
    private void eliminarLinea(ActionEvent event) {
        LineaPedidoCompra lineaSeleccionada = tblLineas.getSelectionModel().getSelectedItem();
        lineas.remove(borrarSeleccion());
        tblLineas.setItems(lineas);
        cargarLineas();
        Double totalPedido = Double.parseDouble(txtTotal.getText()) - lineaSeleccionada.getImporteTotalLinea();
        txtTotal.setText(String.valueOf(totalPedido));

    }

    public int borrarSeleccion() {
        tblLineas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return tblLineas.getSelectionModel().getSelectedIndex();
    }

    private void limpiarVista() {
        if (fecha != null) {
            fecha.setValue(null);
        }
        if (!txtIDPedido.equals("")) {
            txtIDPedido.setText("");
        }
        if (cmbProveedores.getValue() != null) {
            //cmbProveedores.getSelectionModel().clearSelection();
            cmbProveedores.setValue(null);
        }
        if (!tblLineas.getItems().isEmpty()) {
            tblLineas.setItems(null);
        }

        if (!tblProductos.getItems().isEmpty()) {
            tblProductos.setItems(null);
        }
        if (!txtUnidades.getText().isEmpty()) {
            txtUnidades.setText("");
        }
        if (!txtTotal.getText().isEmpty()) {
            txtTotal.setText("");
        }
        cmbProveedores.setDisable(false);

    }

    @FXML
    private void limpiarPantalla(ActionEvent event) {
        limpiarVista();
    }

}
