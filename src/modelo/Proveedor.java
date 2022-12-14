package modelo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author Mayra
 */
public class Proveedor {

    private int id_proveedor;
    private String nif;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private String telefono;
    private int usuario_aniade;
    private int usuario_borra;
    private int usuario_mod;
    private Date fecha_aniade;
    private Date fecha_borra;
    private Date fecha_mod;
    private boolean eliminado;

    public Proveedor() {
    }

    public Proveedor(int id_proveedor, String nif, String nombre, String apellidos, String direccion, String email, String telefono, int usuario_aniade, int usuario_borra, int usuario_mod, Date fecha_aniade, Date fecha_borra, Date fecha_mod, boolean eliminado) {
        this.id_proveedor = id_proveedor;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.usuario_aniade = usuario_aniade;
        this.usuario_borra = usuario_borra;
        this.usuario_mod = usuario_mod;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.eliminado = eliminado;
    }

    public Proveedor(int id_proveedor, String nif, String nombre, String apellidos, String direccion, String email, String telefono) {
        this.id_proveedor = id_proveedor;
        this.nif = nif;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public Proveedor(int idProveedor) {
        this.id_proveedor = idProveedor;
    }

    public Proveedor(String nombre) {
        this.nombre = nombre;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getUsuario_aniade() {
        return usuario_aniade;
    }

    public void setUsuario_aniade(int usuario_aniade) {
        this.usuario_aniade = usuario_aniade;
    }

    public int getUsuario_borra() {
        return usuario_borra;
    }

    public void setUsuario_borra(int usuario_borra) {
        this.usuario_borra = usuario_borra;
    }

    public int getUsuario_mod() {
        return usuario_mod;
    }

    public void setUsuario_mod(int usuario_mod) {
        this.usuario_mod = usuario_mod;
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

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {

        return this.nombre;
    }

    //metodo para obtener todos los proveedores que no tienen el eliminado a 1
    public static ObservableList obtenerProveedores() {
        ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.proveedores WHERE eliminado = 0")) {
            while (result.next()) {
                int id = result.getInt("id_proveedor");
                String nif = result.getString("nif");
                String nombre = result.getString("nombre");
                String apellidos = result.getString("apellidos");
                String direccion = result.getString("direccion");
                String email = result.getString("email");
                String telefono = result.getString("telefono");

                listaProveedores.add(new Proveedor(id, nif, nombre, apellidos, direccion, email, telefono));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurri?? un error al obtener los proveedor");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return listaProveedores;
    }

    //m??todo para obtener la id de los proveedores con ese nombre
    public static int obtenerId(String proveedor) {
        int id_proveedor = 0;
        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT id_proveedor FROM backup21_mayra.proveedores WHERE nombre = '" + proveedor + "'")) {
                while (result.next()) {
                    id_proveedor = result.getInt("id_proveedor");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ocurri?? un error al obtener el id del proveedor");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return id_proveedor;
    }

    //m??todo insertar proveedor en la tabla
    public static boolean insertarProveedor(String nif, String nombre, String apellidos, String direccion, String email, String telefono) {
        try {
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "INSERT INTO backup21_mayra.proveedores (id_proveedor,nif, nombre, apellidos, direccion, email, telefono, "
                    + "fecha_aniade, fecha_borra, fecha_mod, eliminado, usuario_aniade, usuario_borra, usuario_mod) "
                    + "VALUES (NULL, '" + nif + "', '" + nombre + "', '" + apellidos
                    + "', '" + direccion + "', '" + email + "', '" + telefono
                    + "', '" + Timestamp.valueOf(LocalDateTime.now()) + "', NULL, NULL, DEFAULT, " + Global.usuarioLogueadoId
                    + ", NULL, NULL)";
            return stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Ocurri?? un error al insertar el proveedor");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }

    //m??todo borrar proveedor seleccionado en la tabla
    public static boolean borrarProveedor(Proveedor proveedor) {
        try {
            int id = Proveedor.obtenerId(proveedor.getNombre());
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "UPDATE backup21_mayra.proveedores SET fecha_borra = '" + Timestamp.valueOf(LocalDateTime.now()) + "', eliminado = '1', usuario_borra = " + Global.usuarioLogueadoId
                    + " WHERE id_proveedor = " + id;
            return stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Ocurri?? un error al borrar el proveedor");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }

    //m??todo para modificar proveedor
    public static boolean modificarProveedor(Proveedor proveedor) {
        Statement stmt = null;
        try {
            String sql = "UPDATE backup21_mayra.proveedores SET nif = '" + proveedor.getNif() + "', nombre = '" + proveedor.getNombre()
                    + "', apellidos = '" + proveedor.getApellidos() + "' , direccion =  '" + proveedor.getDireccion()
                    + "', fecha_mod = '" + Timestamp.valueOf(LocalDateTime.now())
                    + "', usuario_mod = " + Global.usuarioLogueadoId
                    + " WHERE id_proveedor = " + proveedor.getId_proveedor();
            stmt = Conexion.obtenerConexion().createStatement();
            return stmt.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Ocurri?? un error al actualizar el proveedor");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }

    //m??todo para obtener nombre de un proveedor por su id
    public static Proveedor obtenerProveedorPorId(int idProveedor) {
        Proveedor proveedor = null;
        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT nombre FROM backup21_mayra.proveedores WHERE eliminado = 0 AND id_proveedor = " + idProveedor)) {
                while (result.next()) {
                    String nombre = result.getString("nombre");
                    proveedor = new Proveedor(nombre);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ocurri?? un error al obtener el id del cliente");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return proveedor;
    }
}
