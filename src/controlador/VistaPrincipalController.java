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
    //array de tabs que se verá el rol operario
    private String[] pestaniasOperario = {"Productos", "Pedidos compra", "Orden fabricación"};
    //array de tabs que se verá el rol Administrativo
    private String[] pestaniasAdministrativo = {"Productos", "Pedidos compra", "Pedidos venta","Facturas compra","Facturas venta","Proveedores","Clientes"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //lista con las tabs de la pantalla principal       
        listaTabs = tabPane.getTabs();
        //según el rol que tiene el usuario logueado tendrá visible unas tabs u otras
        switch (Global.usuarioLoginRol) {
            case "Operario":
                comprobarTabs(pestaniasOperario);//lo llamamos y comprobamos las tabs                
                break;
            case "Administrativo":
                comprobarTabs(pestaniasAdministrativo);
                break;           
        }
    }
    //método que comprueba las tabs visibles en funcion del rol al que le pasamos un array
    public void comprobarTabs(String[] lista) {
        //para borrar tabs 
        for (int i = 0; i < listaTabs.size(); i++) {
            boolean existe = false;
            Tab tab = listaTabs.get(i);
            for (int j = 0; j < lista.length && !existe; j++) {
                if(tab.getText().equals(lista[j])){
                    existe = true;
                }
            }
            if(!existe){
                //si no pongo el i-- se saltaria una posición cuando se van borrando
                listaTabs.remove(tab);
                i--;
            }
        }
    }
}
