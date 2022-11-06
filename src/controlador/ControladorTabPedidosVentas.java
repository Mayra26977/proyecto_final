package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.PedidoVenta;
import modelo.Utils;

/**
 * FXML Controller class
 *
 * @author maria.enriquez
 */
public class ControladorTabPedidosVentas implements Initializable {

    @FXML
    private TableView<PedidoVenta> tblPedidosVenta;
    @FXML
    private TableColumn<PedidoVenta, Integer> colId;
    @FXML
    private TableColumn<PedidoVenta, Timestamp> colFecha;
    @FXML
    private TableColumn<PedidoVenta, Integer> colCliente;
    @FXML
    private TableColumn<PedidoVenta, Double> colImporte;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnVer;
    @FXML
    private Button btnNuevo;
    @FXML
    private AnchorPane raizPadre;
    @FXML
    private Button btnActualizarTabla;

    private Stage stage;
    private FXMLLoader loader;
    private ObservableList<PedidoVenta> pedidos;
    private PedidoVenta pedidoVenta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
        cargarTablaPedidos();
    }

    @FXML
    private void nuevo(ActionEvent event) throws IOException {

        loader = new FXMLLoader(getClass().getResource("/vista/vistaPedidoVenta.fxml"));
        //creo controlador de la ventana hija para pasarle el pedido
        ControladorVistaPedidoVenta controladorHija = new ControladorVistaPedidoVenta();
        //le paso a la ventana hija el pedido obtenido de la tabla
        //controladorHija.setPedido(pedidoVenta);
        //controller.setControladorPadre(controladorPadre); queria pasarle el controlador a la hija para recargar desde alli la tabla
        loader.setController(controladorHija);
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            //lo que ocurre cuando se cierra la ventana
//            //que se use en EventHandler y se sobrescribe el metodo handle
//            @Override
//            public void handle(WindowEvent event) {
//                System.out.println("Hola");
//                cargarTablaPedidos();
//            }
//
//        });
        Utils.abrirVentana(loader, stage);

    }

    @FXML
    private void verPedido(ActionEvent event) {
        pedidoVenta = tblPedidosVenta.getSelectionModel().getSelectedItem();

        try {
            loader = new FXMLLoader(getClass().getResource("/vista/vistaPedido_Venta.fxml"));
            ControladorVistaPedidoVenta controller = new ControladorVistaPedidoVenta();
            controller.setPedido(pedidoVenta);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error creando ventana: %s", e.getMessage()));
        }

    }

    @FXML
    private void borrarPedidoVenta(ActionEvent event) {

        PedidoVenta pedidoVenta = tblPedidosVenta.getSelectionModel().getSelectedItem();
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar pedido");
        alert.setHeaderText("Vas a borrar el pedido");
        alert.setContentText("Estas seguro de borrar el pedido?");
        Optional<ButtonType> action = alert.showAndWait();
        if (PedidoVenta.borrarPedido(pedidoVenta) && action.get() == ButtonType.OK) {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Borrar pedido");
            alert.setHeaderText("Pedido borrado");
            alert.setContentText("El pedido se borro correctamente");
            alert.showAndWait();

        } else {

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Borrar pedido");
            alert.setHeaderText("Pedido no borrado");
            alert.setContentText("El pedido no se borró");
            alert.showAndWait();

        }
        cargarTablaPedidos();
    }

    private void cargarTablaPedidos() {

        colId.setCellValueFactory(new PropertyValueFactory("idPedido"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        colCliente.setCellValueFactory(new PropertyValueFactory("idCliente"));
        colImporte.setCellValueFactory(new PropertyValueFactory("totalPedido"));
        pedidos = PedidoVenta.obtenerPedidos();
        tblPedidosVenta.setItems(pedidos);

    }

    @FXML
    private void cargarTabla(ActionEvent event) {
        pedidos = PedidoVenta.obtenerPedidos();
        tblPedidosVenta.setItems(pedidos);
    }

}
