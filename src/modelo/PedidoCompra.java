package modelo;

import com.mysql.cj.protocol.Resultset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maria.enriquez
 */
public class PedidoCompra {

    private int id_pedido;
    private int id_proveedor;
    private Timestamp fecha;
    private Timestamp fecha_aniade;
    private Timestamp fecha_borra;
    private Timestamp fecha_mod;
    private int usuario_aniade;
    private int usuario_borra;
    private int usuario_mod;
    private boolean eliminado;
    private double totalPedido;

    public PedidoCompra() {
    }

    public PedidoCompra(int id_pedido, int id_proveedor, boolean eliminado, Timestamp fecha, Timestamp fecha_aniade, Timestamp fecha_borra, Timestamp fecha_mod, int usuario_aniade, int usuario_borra, int usuario_mod, double totalPedido) {
        this.id_pedido = id_pedido;
        this.id_proveedor = id_proveedor;
        this.eliminado = eliminado;
        this.fecha = fecha;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.usuario_aniade = usuario_aniade;
        this.usuario_borra = usuario_borra;
        this.usuario_mod = usuario_mod;
        this.totalPedido = totalPedido;
    }

    public PedidoCompra(Timestamp fecha, int id_proveedor, double totalPedido) {
        this.id_proveedor = id_proveedor;
        this.fecha = fecha;
        this.totalPedido = totalPedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public Timestamp getFecha_aniade() {
        return fecha_aniade;
    }

    public void setFecha_aniade(Timestamp fecha_aniade) {
        this.fecha_aniade = fecha_aniade;
    }

    public Timestamp getFecha_borra() {
        return fecha_borra;
    }

    public void setFecha_borra(Timestamp fecha_borra) {
        this.fecha_borra = fecha_borra;
    }

    public Timestamp getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Timestamp fecha_mod) {
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

}
