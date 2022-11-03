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
public class Usuario {

    private int usuarioId;
    private String nombreUsuario;
    private String rol;

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public Usuario(int usuarioId, String nombreUsuario, String rol) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
//metodo para obtener todos los usuarios que no tienen el eliminado a 1

    public static ObservableList obtenerUsuarios() {
        ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.usuario WHERE eliminado = 0")) {
            //String sql = "SELECT * FROM usuario WHERE eliminado = 0";
            // ResultSet result = (ResultSet) Conexion.obtenerConexion().createStatement().executeQuery(sql);

            while (result.next()) {
                String nombre = result.getString("nombre_usuario");
                String rol = result.getString("rol");
                int id = result.getInt("id_usuario");

                //Usuario u = new Usuario(nombre, rol);
                listaUsuarios.add(new Usuario(id,nombre, rol));
            }
        } catch (SQLException ex) {
             System.out.println("Ocurrió un error al obtener los usuarios");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return listaUsuarios;
    }
//metodo para verificar que usuario y contraseña coinciden para hacer login

    public static boolean obtenerUsuarioLogueado(String usuario, String pass) {

        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.usuario WHERE nombre_usuario = '" 
                    + usuario + "' AND password = sha1('" + pass + "') AND eliminado = 0")) {
                int contadorResult = 0;
                while (result.next()) {
                    Global.usuarioLoginRol = result.getString("rol");
                    Global.usuarioLogueadoNombre = result.getString("nombre_usuario");
                    Global.usuarioLogueadoId = result.getInt("id_usuario");
                    contadorResult++;
                }
                return contadorResult > 0;
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al loguearse");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
        
    }
//metodo para obtener la id de los usuarios con ese nombre

    public static int obtenerId(String usuario) {
        int id_usuario = 0;

        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT usuario_id FROM backup21_mayra.usuario WHERE nombre_usuario = '" + usuario + "'")) {
                while (result.next()) {
                    id_usuario = result.getInt("usuarioId");

                }
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener el id del usuario");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }

        return id_usuario;
    }

    //metodo para obtener el rol del usuario
    public static String obtenerRol(int id) {
        String rolU = "";
        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT rol FROM backup21_mayra.usuario WHERE usuario_id = '" + id + "'")) {
                while (result.next()) {
                    rolU = result.getString("rol");

                }
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener el rol del usuario");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return rolU;
    }
//metodo insertar usuario en la tabla

    public static boolean insertarUsuario(String nombreUsuario, String contrasenia, String rol) {
        try {
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "INSERT INTO backup21_mayra.usuario (id_usuario, nombre_usuario, password, rol, usuario_aniade, "
                    + "usuario_borra, usuario_mod, fecha_aniade, fecha_borra, fecha_mod, eliminado) "
                    + "VALUES (NULL, '" + nombreUsuario + "', SHA1('" + contrasenia + "'), '" + rol
                    + "', " + Global.usuarioLogueadoId + ", NULL, NULL, '" + Timestamp.valueOf(LocalDateTime.now())
                    + "', NULL, NULL, DEFAULT)";

            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al insertar el usuario");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }

    }

    //metodo borrar usuario seleccionado en la tabla
    public static boolean borrarUsuario(Usuario usuario) {
        //UPDATE `usuario` SET `usuario_mod` = '7', `fecha_borra` = '2022-10-13 12:49:00', `eliminado` = '1' WHERE `usuario`.`usuario_id` = 20;
        try {
            int id = Usuario.obtenerId(usuario.getNombreUsuario());
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "UPDATE backup21_mayra.usuario SET usuario_borra = '" + Global.usuarioLogueadoId + "', fecha_borra = '" + Timestamp.valueOf(LocalDateTime.now()) + "'"
                    + ", eliminado = '1' WHERE usuario_id = " + id;
            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al borrar el usuario");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }

    }
    
    //método para modificar usuario
    public static boolean modificarUsuario(Usuario usuario) {
        Statement stmt = null;
        try {

            String sql = "UPDATE backup21_mayra.usuario SET nombre_usuario = '" + usuario.getNombreUsuario()
                    + "' ,rol = '" + usuario.getRol() + "', usuario_mod = " + Global.usuarioLogueadoId
                    + ", fecha_mod = '" + Timestamp.valueOf(LocalDateTime.now()) + "'"
                    + " WHERE id_usuario = " + usuario.getUsuarioId();
            stmt = Conexion.obtenerConexion().createStatement();
            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al actualizar el usuario");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }

    //método para modificar usuario y contraseña
    public static boolean modificarUsuario(Usuario usuario, String contrasenia) {
         Statement stmt = null;
        try {
            String sql = "UPDATE backup21_mayra.usuario SET nombre_usuario = '" + usuario.getNombreUsuario()
                    + "' , password = sha1('" + contrasenia + "')"
                    + ", rol = '" + usuario.getRol() + "', usuario_mod = " + Global.usuarioLogueadoId
                    + ", fecha_mod = '" + Timestamp.valueOf(LocalDateTime.now()) + "'"
                    + " WHERE usuario_id = " + usuario.getUsuarioId();
            stmt = Conexion.obtenerConexion().createStatement();
            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al actualizar el usuario");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }

}
