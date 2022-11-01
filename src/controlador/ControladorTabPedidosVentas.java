package controlador;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private void verPedido(ActionEvent event) {
        loader = new FXMLLoader(getClass().getResource("/vista/vistaPedidoVenta.fxml"));
        //obtengo el controlador de la ventana a la que voy a mandar el pedido
        ControladorVistaPedidoVenta controlador = (ControladorVistaPedidoVenta) loader.getController();
        //pedido venta le doy el valor del pedido de la tabla
        pedidoVenta = tblPedidosVenta.getSelectionModel().getSelectedItem();
        //y seteo el pedido de la ventana al que mando el pedido 
        controlador.setPedidoVenta(pedidoVenta);
        Utils.abrirVentana(loader, stage);
    }

    @FXML
    private void nuevo(ActionEvent event) {

        loader = new FXMLLoader(getClass().getResource("/vista/vistaPedidoVenta.fxml"));
        Utils.abrirVentana(loader, stage);
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

}
