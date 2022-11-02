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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    @FXML
    private AnchorPane raizHija;

    private ObservableList<Cliente> clientes;
    private Cliente clienteSelec;
    private ObservableList<Producto> productos;
    private ObservableList<LineaPedidoVenta> lineas = FXCollections.observableArrayList();
    private PedidoVenta pedido;
    private FXMLLoader loader;
    private Stage stage;
    private ControladorTabPedidosVentas controladorPadre;

    public void setPedido(PedidoVenta pedido) {
        this.pedido = pedido;
    }

    void setControladorPadre(ControladorTabPedidosVentas controladorPadre) {
        this.controladorPadre = controladorPadre;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!(this.pedido == null)) {
            recuperarPedido();
        } else {
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
        //si la cantidad de producto que hay menos la cantidad que piden es menor de 0 no se puede vender mas de lo que tengo
        if (producto.getCantidad() - cantidad <= 0) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cantidad producto");
            alert.setHeaderText("No hay suficientes unidades");
            alert.setContentText("Obten unidades para poder vender");
            alert.showAndWait();

        } else {
            LineaPedidoVenta linea = new LineaPedidoVenta(producto.getId_producto(), cantidad, cantidad * producto.getPrecio(), producto.getNombre(), producto.getPrecio());
            lineas.add(linea);
            Double totalPedido = Double.parseDouble(txtTotal.getText()) + linea.getImporteTotalLinea();
            txtTotal.setText(String.valueOf(totalPedido));
        }

    }

    @FXML
    private void salirPedidoVenta(ActionEvent event) {
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
    private void guardarPedidoVenta(ActionEvent event) {

        try {
            Timestamp fechaPedido = null;
            //hacer transaccion crear pedido
            //creo el pedido para obtener el id del pedido
            //añado a las lineas el id del pedido insertar todas las lineas y los demas campos            
            Conexion.obtenerConexion().setAutoCommit(false);
            if (fecha.getValue() == null) {
                //si no se pone fecha se informa de que no se puede quedar vacia los pedidos tiene fecha
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fecha vacia");
                alert.setHeaderText("Rellenar fecha");
                alert.setContentText("La fecha no puede estar vacia");
                alert.showAndWait();
            } else {
                //pasar la hora del datepicker para poder insertarla en el pedido
                //se coge la fecha del datapicker
                LocalDate fechaObtenida = fecha.getValue();
                //se transforma a LocalDateTime para añadirle la hora
                LocalDateTime localDateTime = fechaObtenida.atTime(LocalTime.now());
                //y se crea un objeto Timestamp con la fecha para insertar en la tabla 
                fechaPedido = Timestamp.valueOf(localDateTime);
            }
            if (fechaPedido != null) {
                Cliente cliente = cmbClientes.getValue();
                PedidoVenta pedido = new PedidoVenta(fechaPedido, cliente.getIdCliente(), Double.parseDouble(txtTotal.getText()));
                PedidoVenta.insertarPedidoVenta(pedido, fechaPedido, cliente, new ArrayList<LineaPedidoVenta>(lineas));
                //informo de que se ha insertado el pedido correctamente con un alert
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pedido insertado");
                alert.setHeaderText("Pedido insertado");
                alert.setContentText("El pedido se inserto correctamente");
                alert.showAndWait();
                //se cierra la ventana cuando se ha insertado
                modelo.Utils.cerrarVentana(event);
                //queria haber recargado la tabla desde aqui 
                //this.controladorPadre.recargarTabla();  
            }

        } catch (SQLException ex) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pedido no insertado");
            alert.setHeaderText("Pedido no insertado");
            alert.setContentText("El pedido no se inserto pudo insertar");
            alert.showAndWait();
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
//metodo donde seteamos los controles con el valor del pedido que hemos obtenido de la pantalla padre
    public void recuperarPedido() {
        btnAniadirProd.setDisable(true);
        btnEliminarLinea.setDisable(true);
        btnGuardar.setDisable(true);
        
        txtIDPedido.setText(String.valueOf(this.pedido.getIdPedido()));
        fecha.setValue(pedido.getFecha().toLocalDateTime().toLocalDate());
        cmbClientes.setValue(new Cliente(pedido.getIdCliente()));
        cmbClientes.setDisable(true);
        txtTotal.setText(String.valueOf(pedido.getTotalPedido()));
        

    }

}
