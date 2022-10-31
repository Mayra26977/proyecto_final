package modelo;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author maria.enriquez
 */
public class PedidoCompra {

    private int idPedido;
    private int idProveedor;
    private Timestamp fecha;
    private Timestamp fechaAniade;
    private Timestamp fechaBorra;
    private Timestamp fechaMod;
    private int usuarioAniade;
    private int usuarioBorra;
    private int usuarioMod;
    private boolean eliminado;
    private double totalPedido;

    public PedidoCompra() {
    }

    public PedidoCompra(int idPedido, int idProveedor, boolean eliminado, Timestamp fecha, Timestamp fechaAniade, Timestamp fechaBorra, Timestamp fechaMod, int usuarioAniade, int usuarioBorra, int usuarioMod, double totalPedido) {
        this.idPedido = idPedido;
        this.idProveedor = idProveedor;
        this.eliminado = eliminado;
        this.fecha = fecha;
        this.fechaAniade = fechaAniade;
        this.fechaBorra = fechaBorra;
        this.fechaMod = fechaMod;
        this.usuarioAniade = usuarioAniade;
        this.usuarioBorra = usuarioBorra;
        this.usuarioMod = usuarioMod;
        this.totalPedido = totalPedido;
    }

    public PedidoCompra(Timestamp fecha, int idProveedor, double totalPedido) {
        this.idProveedor = idProveedor;
        this.fecha = fecha;
        this.totalPedido = totalPedido;
    }
    public PedidoCompra(int idPedido, Timestamp fecha, int idProveedor, double totalPedido) {
        this.idPedido = idPedido;
        this.idProveedor = idProveedor;
        this.fecha = fecha;
        this.totalPedido = totalPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
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

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }
    

    public static boolean insertarPedidoCompra(PedidoCompra pedido, Timestamp fechaPedido, Proveedor proveedor,ArrayList <LineaPedidoCompra> lineas) {
        try {
            Conexion.obtenerConexion().setAutoCommit(false);
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("INSERT INTO backup21_mayra.pedidos_compras ( fecha, id_proveedor,  usuario_aniade, fecha_aniade, total_pedido ) VALUES ( ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, fechaPedido);
            ps.setInt(2, proveedor.getId_proveedor());
            ps.setInt(3, Global.usuarioLogueadoId);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setDouble(5, pedido.getTotalPedido());
            ps.execute();
            //devuelve las claves primarias que se generan del PreparedStatement
            ResultSet ids = ps.getGeneratedKeys();
            int idPedido;
            if(ids.next()){
                idPedido = ids.getInt(1);
            }else{
                throw new SQLException("No se ha obtenido el id");
            }
            //insertar las lineas
            for (int i = 0; i < lineas.size(); i++) {
               PreparedStatement psLinea = Conexion.obtenerConexion().prepareStatement("INSERT INTO backup21_mayra.linea_pedido_compra ( id_producto, id_pedido_compra,  precio_total_linea_pedido_compra, unidades) VALUES ( ?, ?, ?, ?)");
               psLinea.setInt(1, lineas.get(i).getId_producto());
               psLinea.setInt(2, idPedido);
               psLinea.setDouble(3, lineas.get(i).getImporteTotalLinea());
               psLinea.setDouble(4, lineas.get(i).getCantidad());
               psLinea.execute();

               //actualizar la cantidad de unidades de ese producto
               PreparedStatement psProducto = Conexion.obtenerConexion().prepareStatement("UPDATE backup21_mayra.producto SET cantidad = cantidad + ?");
               psProducto.setDouble(1, lineas.get(i).getCantidad());
               psProducto.execute();
            }
            Conexion.obtenerConexion().commit();
            Conexion.obtenerConexion().setAutoCommit(true);

            return true;
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al insertar el pedido");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            try {
                Conexion.obtenerConexion().rollback();
                Conexion.obtenerConexion().setAutoCommit(true);
            } catch (SQLException ex1) {
                ex.printStackTrace();
            }
            return false;
        }

    }
    
     public static ObservableList obtenerPedidos() {
        ObservableList<PedidoCompra> pedidos = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.pedidos_compras WHERE eliminado = 0")) {

            while (result.next()) {

                int id = result.getInt("id_pedido_compra");
                Timestamp fecha = result.getTimestamp("fecha");
                int proveedor = result.getInt("id_proveedor");
                double total_pedido = result.getDouble("total_pedido");
               

                pedidos.add(new PedidoCompra(id, fecha, proveedor, total_pedido));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los clientes");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return pedidos;
    }

}
