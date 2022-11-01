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
import modelo.PedidoCompra;
import modelo.Utils;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class ControladorTabPedidoCompra implements Initializable {

    @FXML
    private TableView<PedidoCompra> tblPedidos;
    @FXML
    private TableColumn<PedidoCompra, Integer> colId;
    @FXML
    private TableColumn<PedidoCompra, Timestamp> colFecha;
    @FXML
    private TableColumn<PedidoCompra, String> colProveedor;
    @FXML
    private TableColumn<PedidoCompra, Double> colImporte;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnVer;

    private Stage stage;
    private FXMLLoader loader;
    private ObservableList<PedidoCompra> pedidos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTablaPedidos();

    }

    @FXML
    private void nuevo(ActionEvent event) {
        loader = new FXMLLoader(getClass().getResource("/vista/vistaPedido_Compra.fxml"));
        Utils.abrirVentana(loader, stage);
    }

    @FXML
    private void verPedido(ActionEvent event) {
    }

    private void cargarTablaPedidos() {

        colId.setCellValueFactory(new PropertyValueFactory("idPedido"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        colProveedor.setCellValueFactory(new PropertyValueFactory("idProveedor"));
        colImporte.setCellValueFactory(new PropertyValueFactory("totalPedido"));
        pedidos = PedidoCompra.obtenerPedidos();
        tblPedidos.setItems(pedidos);

    }

    @FXML
    private void borrarPedido(ActionEvent event) {
        PedidoCompra pedido = tblPedidos.getSelectionModel().getSelectedItem();
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Borrar pedido");
        alert.setHeaderText("Vas a borrar el pedido");
        alert.setContentText("Estas seguro de borrar el pedido?");
         Optional<ButtonType> action = alert.showAndWait();
        if (PedidoCompra.borrarPedido(pedido) && action.get() == ButtonType.OK) {
            
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

}
