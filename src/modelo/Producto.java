package modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
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

    private int idProducto;
    private String nombre;
    private String descripcion;
    private Timestamp fechaAniade;
    private Timestamp fechaBorra;
    private Timestamp fechaMod;
    private int usuarioAniade;
    private int usuarioBorra;
    private int usuarioMod;
    private double precio;
    private double cantidad;
    private boolean eliminado;
    private Blob imagen;
    private Image imagenProducto;
    private int idProveedor;

    public Producto(int idProducto, String nombre, String descripcion, Timestamp fechaAniade, Timestamp fechaBorra, Timestamp fechaMod, int usuarioAniade, int usuarioBorra, int usuarioMod, double precio, double cantidad, boolean eliminado, Blob imagen, Image imagenProducto, int idProveedor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAniade = fechaAniade;
        this.fechaBorra = fechaBorra;
        this.fechaMod = fechaMod;
        this.usuarioAniade = usuarioAniade;
        this.usuarioBorra = usuarioBorra;
        this.usuarioMod = usuarioMod;
        this.precio = precio;
        this.cantidad = cantidad;
        this.eliminado = eliminado;
        this.imagen = imagen;
        this.imagenProducto = imagenProducto;
        this.idProveedor = idProveedor;
    }

    public Producto(int IdProducto, String nombre, String descripcion, double precio, double cantidad, Image imagen, int idProveedor) {
        this.idProducto = IdProducto;
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

    public Producto(int IdProducto) {
        this.idProducto = IdProducto;
    }

    public Producto(String nombre, Double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public int getUsuarioAniade() {
        return usuarioAniade;
    }

    public void setUsuarioAniade(int usuarioAniade) {
        this.usuarioAniade = usuarioAniade;
    }

    public int getUsuarioBorra() {
        return usuarioBorra;
    }

    public void setUsuarioBorra(int usuarioBorra) {
        this.usuarioBorra = usuarioBorra;
    }

    public int getUsuarioMod() {
        return usuarioMod;
    }

    public void setUsuarioMod(int usuarioMod) {
        this.usuarioMod = usuarioMod;
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

    @Override
    public String toString() {
        return this.nombre;
    }

    //metodo para obtener todos los productos que no tienen el eliminado a 1
    public static ObservableList obtenerProductos() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        Image img;
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.producto WHERE eliminado = 0")) {
            while (result.next()) {
                byte byteImage[];
                int id = result.getInt("id_Producto");
                String nombre = result.getString("nombre");
                String descripcion = result.getString("descripcion");
                double precio = result.getDouble("precio");
                double cantidad = result.getDouble("cantidad");
                Blob imagen = result.getBlob("imagen");
                //cuando no hay imagen en imageView se queda vacio
                if (imagen == null) {
                    img = null;
                } else {
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

    //método para obtener la id de los productos por nombre
    public static int obtenerId(String producto) {
        int IdProducto = 0;
        try {
            try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                    "SELECT id_producto FROM backup21_mayra.producto WHERE nombre = '" + producto + "'")) {
                while (result.next()) {
                    IdProducto = result.getInt("id_producto");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener el id del producto");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return IdProducto;
    }

    //metodo insertar producto en la tabla
    public static boolean insertarProducto(Producto producto) {
        try {
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(
                    "INSERT INTO backup21_mayra.producto ( nombre, descripcion, precio, cantidad, imagen, usuario_aniade, fecha_aniade,  id_proveedor ) "
                    + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setDouble(4, producto.getCantidad());
            if (producto.getImagenProducto() == null) {
                ps.setBlob(5, (Blob) null);
            } else {
                ps.setBlob(5, imagenABlob(producto.getImagenProducto()));
            }
            ps.setInt(6, Global.usuarioLogueadoId);
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
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
    
    //método modificar producto en la tabla
    public static int modificarProducto(Producto producto) throws IOException {
        try {
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("UPDATE backup21_mayra.producto SET nombre = ?, "
                    + "descripcion = ?, precio = ?, cantidad = ?, imagen = ?, fecha_mod = ?, usuario_mod = ?, id_proveedor = ? "
                    + "WHERE id_producto = ?");
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setDouble(4, producto.getCantidad());
            if (producto.getImagenProducto() == null) {
                ps.setNull(5, 0);
            } else {
                ps.setBlob(5, imagenABlob(producto.getImagenProducto()));
            }
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(7, Global.usuarioLogueadoId);
            ps.setInt(8, producto.getIdProducto());
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
            int id = producto.getIdProducto();
            Statement stmt = Conexion.obtenerConexion().createStatement();
            String sql = "UPDATE backup21_mayra.producto SET fecha_borra = '" + Timestamp.valueOf(LocalDateTime.now()) + "', eliminado = '1', usuario_borra = " + Global.usuarioLogueadoId
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

    //método para obtener los productos de un proveedor en concreto
    public static ObservableList obtenerProductosProveedor(int idProveedor) {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        Image img;
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                "SELECT * FROM backup21_mayra.producto WHERE eliminado = 0 AND id_proveedor =" + idProveedor)) {
            while (result.next()) {
                byte byteImage[];
                int id = result.getInt("id_producto");
                String nombre = result.getString("nombre");
                String descripcion = result.getString("descripcion");
                double precio = result.getDouble("precio");
                double cantidad = result.getDouble("cantidad");
                Blob imagen = result.getBlob("imagen");
                if (imagen == null) {
                    img = null;
                } else {
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

    //método para obtener el nombre y el precio de un producto por si id
    public static Producto obtenerProductoPorId(int idProducto) {
        Producto producto = null;
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery(
                "SELECT nombre,precio FROM backup21_mayra.producto WHERE eliminado = 0 AND id_producto = " + idProducto)) {            
            while (result.next()) {
                String nombre = result.getString("nombre");
                Double precio = result.getDouble("precio");
                
                producto = new Producto(nombre, precio);
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los productos");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return producto;
    }
}
