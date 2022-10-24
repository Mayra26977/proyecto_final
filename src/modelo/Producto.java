package modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;


/**
 *
 * @author Mayra
 */
public class Producto {

    private int id_producto;
    private String nombre;
    private String descripcion;
    private Date fecha_aniade;
    private Date fecha_borra;
    private Date fecha_mod;
    private int usuario_aniade;
    private int usuario_borra;
    private int usuario_mod;
    private double precio;
    private double cantidad;
    private boolean eliminado;
    private Blob imagen;
    private Image imagenProducto;
    private int idProveedor;

    public Producto(int id_producto, String nombre, String descripcion, Date fecha_aniade, Date fecha_borra, Date fecha_mod, int usuario_aniade, int usuario_borra, int usuario_mod, double precio, double cantidad, boolean eliminado, Blob imagen, Image imagenProducto, int idProveedor) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.usuario_aniade = usuario_aniade;
        this.usuario_borra = usuario_borra;
        this.usuario_mod = usuario_mod;
        this.precio = precio;
        this.cantidad = cantidad;
        this.eliminado = eliminado;
        this.imagen = imagen;
        this.imagenProducto = imagenProducto;
        this.idProveedor = idProveedor;
        
    }

    public Producto(int id_producto, String nombre, String descripcion, double precio, double cantidad, Image imagen, int idProveedor) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenProducto = imagen;
        this.idProveedor = idProveedor;
    }

    public Producto(String nombre, String descripcion, Double precio, Double cantidad, Image imagenRecogida, int idProveedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenProducto = imagenRecogida;
        this.idProveedor = idProveedor;
        

    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Blob getImagen() {
        return imagen;
    }

    public void setImagen(Blob imagen) {
        this.imagen = imagen;
    }

    public Image getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(Image imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int proveedor) {
        this.idProveedor = proveedor;
    }   

    //metodo para obtener todos los productos que no tienen el eliminado a 1
    public static ObservableList obtenerProductos() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        Image img ;
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM producto WHERE eliminado = 0")) {

            while (result.next()) {
                byte byteImage[];
                int id = result.getInt("id_producto");
                String nombre = result.getString("nombre");
                String descripcion = result.getString("descripcion");
                double precio = result.getDouble("precio");
                double cantidad = result.getDouble("cantidad");                
                Blob imagen = result.getBlob("imagen");                
                if (imagen == null){
                 img = null;
                }else{
                byteImage = imagen.getBytes(1, (int) imagen.length());
                //crear el Image y mostrarlo en el ImageView
                 img = new Image(new ByteArrayInputStream(byteImage));
                }
                int idProveedor = result.getInt("id_proveedor");
                
                listaProductos.add(new Producto(id, nombre, descripcion, precio, cantidad, img, idProveedor));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los productos");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return listaProductos;
    }

    //metodo para obtener la id de los productos por nombre
    public static int obtenerId(String producto) {
        int id_producto = 0;

        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT id_prodcuto FROM producto WHERE nombre = '" + producto + "'")) {
                while (result.next()) {
                    id_producto = result.getInt("id_producto");

                }
            }

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener el id del producto");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }

        return id_producto;
    }
    //metodo insertar producto en la tabla

    public static boolean insertarProducto(Producto producto)  {
        try {
            
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("INSERT INTO producto ( nombre, descripcion, precio, cantidad, imagen, fecha_aniade, usuario_aniade, id_proveedor ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)");
//            String sql = "INSERT INTO producto (id_producto, nombre, descripcion, precio, cantidad, imagen, "
//                    + "fecha_aniade, fecha_borra, fecha_mod, usuario_aniade, usuario_borra, usuario_mod, eliminado) "
//                    + "VALUES (NULL, '" + nombre + "', '" + descripcion + "', '" + precio
//                    + "', '" + cantidad + "', '" + imagen + "', '"
//                    + "', '" + Timestamp.valueOf(LocalDateTime.now()) + "', NULL, NULL, DEFAULT, " + Global.usuarioLogueadoId
//                    + ", NULL, NULL)";
            
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setDouble(4, producto.getCantidad());
            if(producto.getImagenProducto() == null){
                ps.setBlob(5,(Blob) null);
            }else{
                ps.setBlob(5, imagenABlob(producto.getImagenProducto()));
            }
            
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(7, Global.usuarioLogueadoId);
            ps.setInt(8, producto.getIdProveedor());
            return ps.execute();

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al insertar el producto");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }

    }
//método para convertir un tipo Image a tipo Blob para guardar la imagen en la base de datos

    public static Blob imagenABlob(Image i) {
        Blob blob = null;
        BufferedImage bImage = SwingFXUtils.fromFXImage(i, null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "png", s);
            byte[] res = s.toByteArray();

            blob = new SerialBlob(res);
            s.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blob;
    }
    //metodo insertar producto en la tabla

    public static int modificarProducto(Producto producto) throws IOException {
        Statement stmt = null;
        try {
//            String sql = "UPDATE producto SET nombre = '" + producto.getNombre() + "', descripcion = '" + producto.getDescripcion()
//                    + "', precio = '" + producto.getPrecio() + "' , cantidad =  '" + producto.getCantidad()
//                    + "' imagen = '" + imagenABlob(producto.getImagenProducto()) +"', fecha_mod = '" + Timestamp.valueOf(LocalDateTime.now())
//                    + "', usuario_mod = " + Global.usuarioLogueadoId
//                    + " WHERE id_producto = " + producto.getId_producto();
//            stmt = Conexion.obtenerConexion().createStatement();
//            return stmt.execute(sql);
           PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("UPDATE producto SET nombre = ?, "
                   + "descripcion = ?, precio = ?, cantidad = ?, imagen = ?, fecha_mod = ?, usuario_mod = ?, id_proveedor = ? "
                   + "WHERE id_producto = ?");
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setDouble(4, producto.getCantidad());
            ps.setBlob(5, imagenABlob(producto.getImagenProducto()));
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(7, Global.usuarioLogueadoId);
            ps.setInt(8, producto.getId_producto());
            ps.setInt(9, producto.getIdProveedor());

            return ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al actualizar el cliente");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return 0;
        }
    }
        //metodo borrar producto seleccionado en la tabla
    public static boolean borrarProducto(Producto producto) {

        try {
            int id = producto.getId_producto();
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "UPDATE producto SET fecha_borra = '" + Timestamp.valueOf(LocalDateTime.now()) + "', eliminado = '1', usuario_borra = " + Global.usuarioLogueadoId
                    + " WHERE id_producto = " + id;
            return stmt.execute(sql);

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al borrar el producto");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }

    }
    public static ObservableList obtenerProductosProveedor(int idProveedor) {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        Image img ;
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM producto WHERE eliminado = 0 AND id_proveedor" + idProveedor)) {

            while (result.next()) {
                byte byteImage[];
                int id = result.getInt("id_producto");
                String nombre = result.getString("nombre");
                String descripcion = result.getString("descripcion");
                double precio = result.getDouble("precio");
                double cantidad = result.getDouble("cantidad");                
                Blob imagen = result.getBlob("imagen"); 
                if (imagen == null){
                 img = null;
                }else{
                byteImage = imagen.getBytes(1, (int) imagen.length());
                //crear el Image y mostrarlo en el ImageView
                 img = new Image(new ByteArrayInputStream(byteImage));
                }
                idProveedor = result.getInt("id_proveedor");
                listaProductos.add(new Producto(id, nombre, descripcion, precio, cantidad, img, idProveedor));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los productos");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return listaProductos;
    }

}
