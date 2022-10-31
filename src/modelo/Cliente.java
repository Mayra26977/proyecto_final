package modelo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Mayra
 */
public class Cliente {

    private int idCliente;
    private String nif;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private boolean eliminado;
    private Timestamp fechaAniade;
    private Timestamp fechaBorra;
    private Timestamp fechaMod;
    private int usaurioAniade;
    private int usaurioBorra;
    private int usaurioMod;

    public Cliente() {
    }

    public Cliente(int idCliente, String nif, String nombre, String apellidos, String direccion, String email, String telefono, boolean eliminado, Timestamp fechaAniade, Timestamp fechaBorra, Timestamp fechaMod, int usaurioAniade, int usaurioBorra, int usaurioMod) {
        this.idCliente = idCliente;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.eliminado = eliminado;
        this.fechaAniade = fechaAniade;
        this.fechaBorra = fechaBorra;
        this.fechaMod = fechaMod;
        this.usaurioAniade = usaurioAniade;
        this.usaurioBorra = usaurioBorra;
        this.usaurioMod = usaurioMod;
    }

    public Cliente(int idCliente, String nif, String nombre, String apellidos, String direccion, String email, String telefono) {
        this.idCliente = idCliente;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public Timestamp getFechaAniade() {
        return fechaAniade;
    }

    public void setFechaAniade(Timestamp fechaAniade) {
        this.fechaAniade = fechaAniade;
    }

    public Timestamp getFechaBorra() {
        return fechaBorra;
    }

    public void setFechaBorra(Timestamp fechaBorra) {
        this.fechaBorra = fechaBorra;
    }

    public Timestamp getFechaMod() {
        return fechaMod;
    }

    public void setFechaMod(Timestamp fechaMod) {
        this.fechaMod = fechaMod;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public int getUsaurioAniade() {
        return usaurioAniade;
    }

    public void setUsaurioAniade(int usaurioAniade) {
        this.usaurioAniade = usaurioAniade;
    }

    public int getUsaurioBorra() {
        return usaurioBorra;
    }

    public void setUsaurioBorra(int usaurioBorra) {
        this.usaurioBorra = usaurioBorra;
    }

    public int getUsaurioMod() {
        return usaurioMod;
    }

    public void setUsaurioMod(int usaurioMod) {
        this.usaurioMod = usaurioMod;
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
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.clientes WHERE eliminado = 0")) {

            while (result.next()) {

                int id = result.getInt("id_cliente");
                String nif = result.getString("nif");
                String nombre = result.getString("nombre");
                String apellidos = result.getString("apellidos");
                String direccion = result.getString("direccion");
                String email = result.getString("email");
                String telefono = result.getString("telefono");

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
                    "SELECT id_cliente FROM backup21_mayra.clientes WHERE nombre = '" + cliente + "'")) {
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
            //INSERT INTO `cliente` (`idCliente`, `nif`, `nombre`, `apellidos`, `direccion`, `email`, `telefono`, 
            //`fechaAniade`, `fechaBorra`, `fechaMod`, `eliminado`, `usuario_aniade`, `usuario_borra`, 
            //`usuario_mod`) VALUES (NULL, '12345678A', 'Maria', 'Gonzalez Ferrer', 'C/ Angel 12', 
            //'mariagonzalez@gmail.com', '123456789', '2022-10-17 15:00:00', NULL, NULL, '0', '7', NULL, NULL);
            String sql = "INSERT INTO backup21_mayra.clientes (id_cliente, nif, nombre, apellidos, direccion, email, telefono, "
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
        //UPDATE `cliente` SET `fechaBorra` = '2022-10-17 15:32:00', `eliminado` = '1', `usuario_borra` = '7' WHERE `cliente`.`idCliente` = 2;
        try {
            int id = Cliente.obtenerId(cliente.getNombre());
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "UPDATE backup21_mayra.clientes SET fecha_borra = '" + Timestamp.valueOf(LocalDateTime.now()) + "', eliminado = '1', usuario_borra = " + Global.usuarioLogueadoId
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
            String sql = "UPDATE backup21_mayra.clientes SET nif = '" + cliente.getNif() + "', nombre = '" + cliente.getNombre()
                    + "', apellidos = '" + cliente.getApellidos() + "' , direccion =  '" + cliente.getDireccion()
                    + "', fecha_mod = '" + Timestamp.valueOf(LocalDateTime.now())
                    + "', usuario_mod = " + Global.usuarioLogueadoId
                    + " WHERE id_cliente = " + cliente.getIdCliente();
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

    @Override
    public String toString() {

        return this.nombre;
    }

}
