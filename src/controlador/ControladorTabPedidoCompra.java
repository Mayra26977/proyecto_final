package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modelo.Pedido_Compra;
import modelo.Utils;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class ControladorTabPedidoCompra implements Initializable {

    @FXML
    private TableView<Pedido_Compra> tblPedidos;
    @FXML
    private TableColumn<Pedido_Compra, ?> colId;
    @FXML
    private TableColumn<Pedido_Compra, ?> colFecha;
    @FXML
    private TableColumn<Pedido_Compra, ?> colProveedor;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnBorrar;
    private Stage stage;
    private FXMLLoader loader;

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

}
