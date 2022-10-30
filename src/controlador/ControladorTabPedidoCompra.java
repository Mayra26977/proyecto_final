package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn<PedidoCompra, ?> colId;
    @FXML
    private TableColumn<PedidoCompra, ?> colFecha;
    @FXML
    private TableColumn<PedidoCompra, ?> colProveedor;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnBorrar;
    private Stage stage;
    private FXMLLoader loader;
    @FXML
    private Button btnVer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    @FXML
    private void nuevo(ActionEvent event) {
        loader = new FXMLLoader(getClass().getResource("/vista/vistaPedidoCompra.fxml"));
        Utils.abrirVentana(loader, stage);
    }

    @FXML
    private void verPedido(ActionEvent event) {
    }

}
