package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

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
         ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Pomelo", 13),
                new PieChart.Data("Naranjas", 25),
                new PieChart.Data("Ciruelas", 10),
                new PieChart.Data("Peras", 22),
                new PieChart.Data("Manzanas", 30));
         grafica.setData(pieChartData);
         grafica.setStartAngle(90);
       
       
    }    
    

}
