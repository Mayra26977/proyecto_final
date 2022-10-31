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
import modelo.Cliente;
import modelo.Conexion;
import modelo.LineaPedidoVenta;
import modelo.PedidoVenta;
import modelo.Producto;

/**
 * FXML Controller class
 *
 * @author maria.enriquez
 */
public class ControladorVistaPedidoVenta implements Initializable {

    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnEliminarLinea;
    @FXML
    private TextField txtUnidades;
    @FXML
    private TableView<Producto> tblProductos;
    @FXML
    private TableColumn<Producto, String> colNombreProducto;
    @FXML
    private TableColumn<Producto, Double> colPrecio;
    @FXML
    private TableView<LineaPedidoVenta> tblLineas;
    @FXML
    private TableColumn<LineaPedidoVenta, String> colNombreLinea;
    @FXML
    private TableColumn<LineaPedidoVenta, Double> colPrecioUnidad;
    @FXML
    private TableColumn<LineaPedidoVenta, Double> colUnidades;
    @FXML
    private TableColumn<LineaPedidoVenta, Double> colTotalLinea;
    @FXML
    private Button btnAniadirProd;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnImprimir;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField txtIDPedido;
    @FXML
    private DatePicker fecha;
    @FXML
    private ComboBox<Cliente> cmbClientes;

    private ObservableList<Cliente> clientes;
    private Cliente clienteSelec;
    private ObservableList<Producto> productos;
    private ObservableList<LineaPedidoVenta> lineas = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarLineas();
        cargarClientes();
        txtTotal.setText("0.0");
        txtUnidades.textProperty().addListener((observable, oldValue, newValue) -> {
            //comprueba que solo puedas escribir numeros enteros
            if (!newValue.matches("\\d*")) {
                txtUnidades.setText(newValue.replaceAll("[^\\d*]", ""));
            }
            btnAniadirProd.setDisable(txtUnidades.getText().equals(""));

        });

    }

    @FXML
    private void limpiarPantalla(ActionEvent event) {
    }

    @FXML
    private void eliminarLinea(ActionEvent event) {
        LineaPedidoVenta lineaSeleccionada = tblLineas.getSelectionModel().getSelectedItem();
        lineas.remove(borrarSeleccion());
        tblLineas.setItems(lineas);
        cargarLineas();
        Double totalPedido = Double.parseDouble(txtTotal.getText()) - lineaSeleccionada.getImporteTotalLinea();
        txtTotal.setText(String.valueOf(totalPedido));
    }

    @FXML
    private void aniadirLinea(ActionEvent event) {
        Producto producto = tblProductos.getSelectionModel().getSelectedItem();
        double cantidad = Double.parseDouble(txtUnidades.getText());
        LineaPedidoVenta linea = new LineaPedidoVenta(producto.getId_producto(), cantidad, cantidad * producto.getPrecio(), producto.getNombre(), producto.getPrecio());
        lineas.add(linea);
        Double totalPedido = Double.parseDouble(txtTotal.getText()) + linea.getImporteTotalLinea();
        txtTotal.setText(String.valueOf(totalPedido));
    }

    @FXML
    private void salirPedidoCompra(ActionEvent event) {
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir pedido venta");
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
            Cliente cliente = cmbClientes.getValue();
            PedidoVenta pedido = new PedidoVenta(fechaPedido, cliente.getIdCliente(), Double.parseDouble(txtTotal.getText()));

            PedidoVenta.insertarPedidoVenta(pedido, fechaPedido, cliente, new ArrayList<LineaPedidoVenta>(lineas));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void seleccionar(ActionEvent event) {
        this.clienteSelec = cmbClientes.getSelectionModel().getSelectedItem();
        cmbClientes.setDisable(true);
        cargarProductos();

    }

    private void cargarClientes() {
        clientes = Cliente.obtenerClientes();
        cmbClientes.setItems(clientes);
    }

    private void cargarProductos() {

        productos = Producto.obtenerProductos();
        PropertyValueFactory p = new PropertyValueFactory("nombre");
        colNombreProducto.setCellValueFactory(p);
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        tblProductos.setItems(productos);
    }

    private void cargarLineas() {
        colNombreLinea.setCellValueFactory(new PropertyValueFactory("nombreProducto"));
        colUnidades.setCellValueFactory(new PropertyValueFactory("cantidad"));
        colTotalLinea.setCellValueFactory(new PropertyValueFactory("importeTotalLinea"));
        colPrecioUnidad.setCellValueFactory(new PropertyValueFactory("precioUnidad"));
        tblLineas.setItems(lineas);
    }

    public int borrarSeleccion() {
        tblLineas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        return tblLineas.getSelectionModel().getSelectedIndex();
    }

}
