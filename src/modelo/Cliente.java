package modelo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author Mayra
 */
public class Cliente {
    
    private int id_cliente;
    private String nif;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private boolean eliminado;
    private Date fecha_aniade;
    private Date fecha_borra;
    private Date fecha_mod;
    private int usaurio_aniade;
    private int usaurio_borra;
    private int usaurio_mod;

    public Cliente() {
    }

    public Cliente(int id_cliente, String nif, String nombre, String apellidos, String direccion, String email, String telefono, boolean eliminado, Date fecha_aniade, Date fecha_borra, Date fecha_mod, int usaurio_aniade, int usaurio_borra, int usaurio_mod) {
        this.id_cliente = id_cliente;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.eliminado = eliminado;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.usaurio_aniade = usaurio_aniade;
        this.usaurio_borra = usaurio_borra;
        this.usaurio_mod = usaurio_mod;
    }

    public Cliente(int id_cliente, String nif, String nombre, String apellidos, String direccion, String email, String telefono) {
        this.id_cliente = id_cliente;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Date getFecha_aniade() {
        return fecha_aniade;
    }

    public void setFecha_aniade(Date fecha_aniade) {
        this.fecha_aniade = fecha_aniade;
    }

    public Date getFecha_borra() {
        return fecha_borra;
    }

    public void setFecha_borra(Date fecha_borra) {
        this.fecha_borra = fecha_borra;
    }

    public Date getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Date fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public int getUsaurio_aniade() {
        return usaurio_aniade;
    }

    public void setUsaurio_aniade(int usaurio_aniade) {
        this.usaurio_aniade = usaurio_aniade;
    }

    public int getUsaurio_borra() {
        return usaurio_borra;
    }

    public void setUsaurio_borra(int usaurio_borra) {
        this.usaurio_borra = usaurio_borra;
    }

    public int getUsaurio_mod() {
        return usaurio_mod;
    }

    public void setUsaurio_mod(int usaurio_mod) {
        this.usaurio_mod = usaurio_mod;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    //metodo para obtener todos los clientes que no tienen el eliminado a 1
    public static ObservableList obtenerClientes() {
        ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM cliente WHERE eliminado = 0")) {

            while (result.next()) {

                int id = result.getInt("id_cliente");
                String nif = result.getString("nif");
                String nombre = result.getString("nombre");
                String apellidos = result.getString("apellidos");
                String direccion = result.getString("direccion");
                String email = result.getString("email");
                String telefono = result.getString("telefono");

                //Usuario u = new Usuario(nombre, rol);
                listaClientes.add(new Cliente(id, nif, nombre, apellidos, direccion, email, telefono));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los clientes");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return listaClientes;
    }

    //metodo para obtener la id de los clientes con ese nombre
    public static int obtenerId(String cliente) {
        int id_cliente = 0;

        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT id_cliente FROM cliente WHERE nombre = '" + cliente + "'")) {
                while (result.next()) {
                    id_cliente = result.getInt("id_cliente");

                }
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener el id del cliente");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }

        return id_cliente;
    }

    //metodo insertar usuario en la tabla
    public static boolean insertarCliente(String nif, String nombre, String apellidos, String direccion, String email, String telefono) {
        try {
            Statement stmt = Conexion.obtenerConexion().createStatement();
            //INSERT INTO `cliente` (`id_cliente`, `nif`, `nombre`, `apellidos`, `direccion`, `email`, `telefono`, 
            //`fecha_aniade`, `fecha_borra`, `fecha_mod`, `eliminado`, `usuario_aniade`, `usuario_borra`, 
            //`usuario_mod`) VALUES (NULL, '12345678A', 'Maria', 'Gonzalez Ferrer', 'C/ Angel 12', 
            //'mariagonzalez@gmail.com', '123456789', '2022-10-17 15:00:00', NULL, NULL, '0', '7', NULL, NULL);
            String sql = "INSERT INTO cliente (id_cliente, nif, nombre, apellidos, direccion, email, telefono, "
                    + "fecha_aniade, fecha_borra, fecha_mod, eliminado, usuario_aniade, usuario_borra, usuario_mod) "
                    + "VALUES (NULL, '" + nif + "', '" + nombre + "', '" + apellidos
                    + "', '" + direccion + "', '" + email + "', '" + telefono
                    + "', '" + Timestamp.valueOf(LocalDateTime.now()) + "', NULL, NULL, DEFAULT, " + Global.usuarioLogueadoId
                    + ", NULL, NULL)";

            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al insertar el cliente");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }

    }

    //metodo borrar cliente seleccionado en la tabla
    public static boolean borrarCliente(Cliente cliente) {
        //UPDATE `cliente` SET `fecha_borra` = '2022-10-17 15:32:00', `eliminado` = '1', `usuario_borra` = '7' WHERE `cliente`.`id_cliente` = 2;
        try {
            int id = Cliente.obtenerId(cliente.getNombre());
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "UPDATE cliente SET fecha_borra = '" + Timestamp.valueOf(LocalDateTime.now()) + "', eliminado = '1', usuario_borra = " + Global.usuarioLogueadoId
                    + " WHERE id_cliente = " + id;
            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al borrar el cliente");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }

    }
    
    //método para modificar cliente
    public static boolean modificarCliente(Cliente cliente) {
        Statement stmt = null;
        try {
            String sql = "UPDATE cliente SET nif = '" + cliente.getNif() + "', nombre = '" + cliente.getNombre()
                    + "', apellidos = '" + cliente.getApellidos() + "' , direccion =  '" + cliente.getDireccion() 
                    + "', fecha_mod = '" + Timestamp.valueOf(LocalDateTime.now())
                    + "', usuario_mod = " + Global.usuarioLogueadoId 
                    + " WHERE id_cliente = " + cliente.getId_cliente();
            stmt = Conexion.obtenerConexion().createStatement();
            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al actualizar el cliente");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }

}
