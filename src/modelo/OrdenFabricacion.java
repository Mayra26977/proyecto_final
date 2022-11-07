package modelo;

import java.sql.Date;
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
public class OrdenFabricacion {

    private int idOf;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private double cantidad;
    private int idProducto;
    private int usuarioAniade;
    private int usuarioBorra;
    private int usuarioMod;
    private Timestamp fechaAniade;
    private Timestamp fechaBorra;
    private Timestamp fechaMod;
    private boolean eliminado;

    public OrdenFabricacion() {
    }

    public OrdenFabricacion(int idOf, Timestamp fechaInicio, Timestamp fechaFin, double cantidad, int idProducto, int usuarioAniade, int usuarioBorra, int usuarioMod, Timestamp fechaAniade, Timestamp fechaBorra, Timestamp fechaMod, boolean eliminado) {
        this.idOf = idOf;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
        this.usuarioAniade = usuarioAniade;
        this.usuarioBorra = usuarioBorra;
        this.usuarioMod = usuarioMod;
        this.fechaAniade = fechaAniade;
        this.fechaBorra = fechaBorra;
        this.fechaMod = fechaMod;
        this.eliminado = eliminado;
    }

    public OrdenFabricacion(int idOf, Timestamp fechaInicio, Timestamp fechaFin, int idProducto, double cantidad) {
        this.idOf = idOf;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidad = cantidad;
        this.idProducto = idProducto;
    }

    public OrdenFabricacion(Timestamp fechaInicio, Timestamp fechaFin,int operarioId) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuarioAniade = operarioId;
    }
    

    public int getIdOf() {
        return idOf;
    }

    public void setIdOf(int idOf) {
        this.idOf = idOf;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public static boolean insertarOrdenFabricacion(OrdenFabricacion of, Timestamp fechaInicio, Timestamp fechaFin, Double cantidad, Usuario operario, ArrayList<LineaOrdenFabricacion> lineas) {
        try {
            Conexion.obtenerConexion().setAutoCommit(false);
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("INSERT INTO backup21_mayra.orden_fabricacion (fecha_inicio, fecha_fin , cantidad, id_producto, usuario_aniade, fecha_aniade) VALUES ( ?, ?, ?, ?, ?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, fechaInicio);
            ps.setTimestamp(2, fechaFin);
            ps.setDouble(3, of.getCantidad());
            ps.setInt(4, operario.getUsuarioId());
            ps.setInt(5, Global.usuarioLogueadoId);
            ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            ps.execute();
            //devuelve las claves primarias que se generan del PreparedStatement
            ResultSet ids = ps.getGeneratedKeys();
            int idOf;
            if (ids.next()) {
                idOf = ids.getInt(1);
            } else {
                throw new SQLException("No se ha obtenido el id");
            }
            //insertar las lineas
            for (int i = 0; i < lineas.size(); i++) {
                PreparedStatement psLinea = Conexion.obtenerConexion().prepareStatement("INSERT INTO backup21_mayra.linea_of (id_of, id_producto, cantidad) VALUES ( ?, ?, ?)");
                psLinea.setInt(1, idOf);
                psLinea.setInt(1, lineas.get(i).getIdProducto());                
                psLinea.setDouble(3, lineas.get(i).getCantidad());
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
            System.out.println("Ocurrió un error al insertar la orden de fabricación");
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

    public static ObservableList obtenerOfs() {
        ObservableList<OrdenFabricacion> ofs = FXCollections.observableArrayList();
        try ( ResultSet result = Conexion.obtenerConexion().createStatement().executeQuery("SELECT * FROM backup21_mayra.orden_fabricacion WHERE eliminado = 0")) {

            while (result.next()) {

                int id = result.getInt("id_of");
                Timestamp fechaInicio = result.getTimestamp("fecha_inicio");
                Timestamp fechaFin = result.getTimestamp("fecha_fin");
                int idProducto = result.getInt("id_producto");
                double cantidad = result.getDouble("cantidad");

                ofs.add(new OrdenFabricacion(id, fechaInicio, fechaFin, idProducto, cantidad));
            }
        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al obtener las ordenes de fabricación");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
        }
        return ofs;
    }

    public static boolean borrarOf(OrdenFabricacion of) {
        try {
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("UPDATE backup21_mayra.orden_fabricacion SET usuario_borra = ?, fecha_borra = ?, eliminado = ? WHERE id_of = ?");
            ps.setInt(1, Global.usuarioLogueadoId);
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(3, true);
            ps.setInt(4, of.getIdOf());
            ps.execute();
            actualizarCantidades(of);
            return true;

        } catch (SQLException ex) {
            System.out.println("Ocurrió un error al borrar la orden de fabricación");
            System.out.println("Mensaje del error " + ex.getMessage());
            System.out.println("Detalles del error ");
            ex.printStackTrace();
            return false;
        }
    }
        private static boolean actualizarCantidades(OrdenFabricacion of) {
        try {
            Conexion.obtenerConexion().setAutoCommit(false);
            //recuperamos cada linea de la orden de fabricacion que tiene y las unidades que tiene
            PreparedStatement ps = Conexion.obtenerConexion().prepareStatement("SELECT id_producto, cantidad FROM backup21_mayra.linea_of where id_of = ?");
            ps.setInt(1, of.getIdOf());
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                //mientras en la orden de fabricacion haya lineas con productos y unidades se actualizaran esos productos
                PreparedStatement psProducto = Conexion.obtenerConexion().prepareStatement("UPDATE backup21_mayra.producto SET cantidad = cantidad - ? where id_producto = ?");

                psProducto.setDouble(1, result.getDouble("unidades"));
                psProducto.setInt(2, result.getInt("id_producto"));
                psProducto.execute();
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
