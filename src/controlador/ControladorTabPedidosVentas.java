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
import modelo.Utils;

/**
 * FXML Controller class
 *
 * @author maria.enriquez
 */
public class ControladorTabPedidosVentas implements Initializable {

    @FXML
    private TableView<?> tblPedidos;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colFecha;
    @FXML
    private TableColumn<?, ?> colCliente;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnVer;
    @FXML
    private Button btnNuevo;
    
    private Stage stage;
    private FXMLLoader loader;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void verPedido(ActionEvent event) {
    }

    @FXML
    private void nuevo(ActionEvent event) {
        
        loader = new FXMLLoader(getClass().getResource("/vista/vistaPedidoVenta.fxml"));
        Utils.abrirVentana(loader, stage);
    }
    
}
