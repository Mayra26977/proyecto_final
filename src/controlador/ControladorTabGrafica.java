package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import modelo.Grafica;

/**
 * FXML Controller class
 *
 * @author maria.enriquez
 */
public class ControladorTabGrafica implements Initializable {
    
    
    @FXML   
    private PieChart grafica;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        double totalCompras = Grafica.obtenerTotalCompras();
        double totalVentas = Grafica.obtenerTotalVentas();
        ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
                       new PieChart.Data("Compras", totalCompras),
                       new PieChart.Data("Ventas", totalVentas));                       
                grafica.setData(pieChartData);
                grafica.setStartAngle(90);
           }        

}
