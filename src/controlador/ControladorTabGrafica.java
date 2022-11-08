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
 * @author Mayra
 */
public class ControladorTabGrafica implements Initializable {    
    
    @FXML   
    private PieChart grafica;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        //obtenemos el total de las compras
        double totalCompras = Grafica.obtenerTotalCompras();
        //obtenemos el total de las ventas
        double totalVentas = Grafica.obtenerTotalVentas();
        //se insertan los datos en una lista
        ObservableList<PieChart.Data> pieChartData =
        FXCollections.observableArrayList(
                       new PieChart.Data("Compras", totalCompras),
                       new PieChart.Data("Ventas", totalVentas));
        //se setea la grafica con la lista con los datos
                grafica.setData(pieChartData);
                //propiedad de ángulo en la gráfica
                grafica.setStartAngle(90);
           }        

}
