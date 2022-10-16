package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import modelo.Global;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class VistaPrincipalController implements Initializable {

    @FXML
    private TabPane tabPane;
    private ObservableList<Tab> listaTabs;
    private String[] pestaniasOperario = {"Productos", "Pedidos compra", "Orden fabricación"};
    private String[] pestaniasAdministrativo = {"Productos", "Pedidos compra", "Pedidos venta","Facturas compra","Facturas venta","Proveedores","Clientes"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Global.usuarioLoginRol);
        System.out.println(Global.usuarioLogueadoNombre);
        listaTabs = tabPane.getTabs();
        switch (Global.usuarioLoginRol) {
            case "Operario":
                comprobarTabs(pestaniasOperario);
                
                break;
            case "Administrativo":
                comprobarTabs(pestaniasAdministrativo);
                break;           
        }
    }

    public void comprobarTabs(String[] lista) {
        
        for (int i = 0; i < listaTabs.size(); i++) {
            boolean existe = false;
            Tab tab = listaTabs.get(i);
            for (int j = 0; j < lista.length && !existe; j++) {
                if(tab.getText().equals(lista[j])){
                    existe = true;
                }
            }
            if(!existe){
                //si no pongo el i-- se saltaria una posición 
                listaTabs.remove(tab);
                i--;
            }
        }
    }

}
