package modelo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author maria.enriquez
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

    public Producto(int id_producto, String nombre, String descripcion, Date fecha_aniade, Date fecha_borra, Date fecha_mod, int usuario_aniade, int usuario_borra, int usuario_mod, double precio, double cantidad, boolean eliminado, Blob imagen, Image imagenProducto) {
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
    }

    public Producto(int id_producto, String nombre, String descripcion, double precio, double cantidad, Image imagen) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenProducto = imagen;
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

    //metodo para obtener todos los usuarios que no tienen el eliminado a 1
    public static ObservableList obtenerProductos() {
        ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
        ImageView imagenProducto = null;
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM producto WHERE eliminado = 0")) {

            while (result.next()) {
                byte byteImage[] = null;
                int id = result.getInt("id_producto");
                String nombre = result.getString("nombre");
                String descripcion = result.getString("descripcion");
                double precio = result.getDouble("precio");
                double cantidad = result.getDouble("cantidad");
                Blob imagen = result.getBlob("imagen");
                byteImage = imagen.getBytes(1, (int) imagen.length());
                // crear el Image y mostrarlo en el ImageView
                Image img = new Image(new ByteArrayInputStream(byteImage));
                ImageView imageView = new ImageView(img);
                listaProductos.add(new Producto(id, nombre, descripcion, precio, cantidad, img));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los usuarios");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return listaProductos;
    }

}
