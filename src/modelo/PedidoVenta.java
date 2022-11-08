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
 * FXML Controller class
 *
 * @author Mayra
 */
public class PedidoVenta {

    private int idPedido;
    private int idCliente;
    private int idLinea;
    private Timestamp fecha;
    private Timestamp fechaAniade;
    private Timestamp fechaBorra;
    private Timestamp fechaMod;
    private double totalPedido;
    private boolean eliminado;
    private int usuarioAniade;
    private int usuarioBorra;
    private int usuarioMod;

    public PedidoVenta() {
    }

    public PedidoVenta(int id_pedido, int id_cliente, int id_linea, Timestamp fecha, Timestamp fecha_aniade, Timestamp fecha_borra, Timestamp fecha_mod, double total_facturar, boolean eliminado, int usuario_aniade, int usuario_borra, int usuario_mod) {
        this.idPedido = id_pedido;
        this.idCliente = id_cliente;
        this.idLinea = id_linea;
        this.fecha = fecha;
        this.fechaAniade = fecha_aniade;
        this.fechaBorra = fecha_borra;
        this.fechaMod = fecha_mod;
        this.totalPedido = total_facturar;
        this.eliminado = eliminado;
        this.usuarioAniade = usuario_aniade;
        this.usuarioBorra = usuario_borra;
        this.usuarioMod = usuario_mod;
    }

    public PedidoVenta(int idPedido, Timestamp fecha, int idCliente, double totalPedido) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.totalPedido = totalPedido;
    }

    public PedidoVenta(Timestamp fecha, int idCliente, double totalPedido) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.totalPedido = totalPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
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

    public double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
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

    //método para insertar un pedido de venta en la base de datos
    public static boolean insertarPedidoVenta(PedidoVenta pedido, Timestamp fechaPedido, Cliente cliente, ArrayList<LineaPedidoVenta> lineas) {
        try {
            Conexion.obtenerConexion().setAutoCommit(false);
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(
                    "INSERT INTO backup21_mayra.pedidos_ventas ( fecha, id_cliente,  usuario_aniade, fecha_aniade, total_pedido ) VALUES ( ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, fechaPedido);
            ps.setInt(2, cliente.getIdCliente());
            ps.setInt(3, Global.usuarioLogueadoId);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setDouble(5, pedido.getTotalPedido());
            ps.execute();
            //devuelve las claves primarias que se generan del PreparedStatement para ponerle id al pedido de venta
            ResultSet ids = ps.getGeneratedKeys();
            int idPedido;
            if (ids.next()) {
                idPedido = ids.getInt(1);
            } else {
                throw new SQLException("No se ha obtenido el id");
            }
            //insertar las lineas
            for (int i = 0; i < lineas.size(); i++) {
                PreparedStatement psLinea = Conexion.obtenerConexion().prepareStatement("INSERT INTO backup21_mayra.linea_pedido_venta ( id_producto, id_pedido_venta,  precio_total_linea_pedido_venta, unidades) VALUES ( ?, ?, ?, ?)");
                psLinea.setInt(1, lineas.get(i).getIdProducto());
                psLinea.setInt(2, idPedido);
                psLinea.setDouble(3, lineas.get(i).getImporteTotalLinea());
                psLinea.setDouble(4, lineas.get(i).getCantidad());
                psLinea.execute();
                //actualizar la cantidad de unidades de ese producto cuando se hace una venta
                PreparedStatement psProducto = Conexion.obtenerConexion().prepareStatement("UPDATE backup21_mayra.producto SET cantidad = cantidad - ?");
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

    //método para obtener los pedidos que tienen eliminado a 0 de la base de datos
    public static ObservableList obtenerPedidos() {
        ObservableList<PedidoVenta> pedidos = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.pedidos_ventas WHERE eliminado = 0")) {
            while (result.next()) {
                int id = result.getInt("id_pedido_venta");
                Timestamp fecha = result.getTimestamp("fecha");
                int proveedor = result.getInt("id_cliente");
                double total_pedido = result.getDouble("total_pedido");

                pedidos.add(new PedidoVenta(id, fecha, proveedor, total_pedido));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener los pedidos");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return pedidos;
    }

    //método para borrar el pedido de la base de datos, no se borra se le pone el eliminado a 1
    public static boolean borrarPedido(PedidoVenta pedido) {
        try {
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement(
                    "UPDATE backup21_mayra.pedidos_ventas SET usuario_borra = ?, fecha_borra = ?, eliminado = ? WHERE id_pedido_venta = ?");
            ps.setInt(1, Global.usuarioLogueadoId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(3, true);
            ps.setInt(4, pedido.getIdPedido());
            ps.execute();
            //se actualizan las cantidades de los productos cuando se borra un pedido
            actualizarCantidades(pedido);
            return true;
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al borrar el pedido");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }

    //se actualizan las cantidades del pedido cuando se borra un pedido, puesto que no se han vendido, se le suma de nuevo a la cantidad que
    //cuando se generó el pedido de venta se le resto
    private static boolean actualizarCantidades(PedidoVenta pedido) {
        try {
            //transacción
            Conexion.obtenerConexion().setAutoCommit(false);
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("SELECT id_producto, unidades FROM backup21_mayra.linea_pedido_venta where id_pedido_venta = ?");
            ps.setInt(1, pedido.getIdPedido());
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                PreparedStatement psproducto = Conexion.obtenerConexion().prepareStatement("UPDATE backup21_mayra.producto SET cantidad = cantidad + ? where id_producto = ?");
                psproducto.setDouble(1, result.getDouble("unidades"));
                psproducto.setInt(2, result.getInt("id_producto"));
                psproducto.execute();
            }
            Conexion.obtenerConexion().commit();
            Conexion.obtenerConexion().setAutoCommit(true);
            return true;
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al actualizar la cantidad");
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
