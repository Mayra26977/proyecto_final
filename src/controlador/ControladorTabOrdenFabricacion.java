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
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.OrdenFabricacion;
import modelo.Utils;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class ControladorTabOrdenFabricacion implements Initializable {

    @FXML
    private TableView<OrdenFabricacion> tblOfs;
    @FXML
    private TableColumn<OrdenFabricacion, Integer> colOf;
    @FXML
    private TableColumn<OrdenFabricacion, Timestamp> colFechaInicio;
    @FXML
    private TableColumn<OrdenFabricacion, Timestamp> colFechaFin;
    @FXML
    private TableColumn<OrdenFabricacion, Integer> colOperario;
    @FXML
    private Button btnModi;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnRefrescar;
    @FXML
    private Button btnNuevo;

    private Stage stage;
    private FXMLLoader loader;
    private ObservableList<OrdenFabricacion> ofs;
    private OrdenFabricacion of;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTablaOfs();
    }

    //metodo que te abre nueva ventana y te muestra la of seleccionada en la tabla
    @FXML
    private void ver(ActionEvent event) {
        of = tblOfs.getSelectionModel().getSelectedItem();
        if (of == null) {
            Alert alert;
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Orden de fabricación seleccionada");
            alert.setHeaderText("Orden de fabricación seleccionada");
            alert.setContentText("Debes seleccionar una orden de frabicación en la tabla para consultarla");
            alert.showAndWait();
        } else {
            try {
                loader = new FXMLLoader(getClass().getResource("/vista/vistaOrdenFabricacion.fxml"));
                ControladorVistaOrdenFabricacion controller = new ControladorVistaOrdenFabricacion();
                controller.setOf(of);
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
    }

    //método para borrar una orden de fabricación
    @FXML
    private void borrarOf(ActionEvent event) {
        OrdenFabricacion of = tblOfs.getSelectionModel().getSelectedItem();
        Alert alert;
        if (of == null) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Orden de fabricación seleccionada");
            alert.setHeaderText("Orden de fabricación seleccionada");
            alert.setContentText("Debes seleccionar una orden de frabicación en la tabla para borrarla");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Borrar orden de fabricación");
            alert.setHeaderText("Vas a borrar la orden de fabricación");
            alert.setContentText("Estas seguro de borrar la orden de fabricación?");
            Optional<ButtonType> action = alert.showAndWait();
            if (OrdenFabricacion.borrarOf(of) && action.get() == ButtonType.OK) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Borrar orden de fabricación");
                alert.setHeaderText("Orden de fabricación borrada");
                alert.setContentText("La orden de fabricación se borró correctamente");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Borrar orden de fabricación");
                alert.setHeaderText("Orden de fabricación no borrada");
                alert.setContentText("La orden de fabricación no se borró");
                alert.showAndWait();
            }
            cargarTablaOfs();
        }
    }
    
    //método para actualizar la tabla de Of
    @FXML
    private void actualizarTablaOf(ActionEvent event) {
        ofs = OrdenFabricacion.obtenerOfs();
        tblOfs.setItems(ofs);
    }
    
    //método que abre una ventana para crear un Of
    @FXML
    private void nuevo(ActionEvent event) {
        loader = new FXMLLoader(getClass().getResource("/vista/vistaOrdenFabricacion.fxml"));
        //creo controlador de la ventana hija para pasarle el pedido
        ControladorVistaOrdenFabricacion controladorHija = new ControladorVistaOrdenFabricacion();
        loader.setController(controladorHija);
        stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        Utils.abrirVentana(loader, stage);
    }
    
    //método que carga con los datos la tabla de Ofs
    private void cargarTablaOfs() {
        colOf.setCellValueFactory(new PropertyValueFactory("idOf"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory("fechaInicio"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory("fechaFin"));
        colOperario.setCellValueFactory(new PropertyValueFactory("operario"));
        ofs = OrdenFabricacion.obtenerOfs();
        tblOfs.setItems(ofs);
    }
}
