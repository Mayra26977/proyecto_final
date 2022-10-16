package modelo;

import java.sql.Date;

/**
 *
 * @author maria.enriquez
 */
public class Pedido_Venta {
    private int id_pedido;
    private int id_cliente;
    private int id_linea;
    private Date fecha;
    private Date fecha_aniade;
    private Date fecha_borra;
    private Date fecha_mod;
    private double total_facturar;
    private boolean eliminado;
    private int usuario_aniade;    
    private int usuario_borra;    
    private int usuario_mod;

    public Pedido_Venta() {
    }

    public Pedido_Venta(int id_pedido, int id_cliente, int id_linea, Date fecha, Date fecha_aniade, Date fecha_borra, Date fecha_mod, double total_facturar, boolean eliminado, int usuario_aniade, int usuario_borra, int usuario_mod) {
        this.id_pedido = id_pedido;
        this.id_cliente = id_cliente;
        this.id_linea = id_linea;
        this.fecha = fecha;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.total_facturar = total_facturar;
        this.eliminado = eliminado;
        this.usuario_aniade = usuario_aniade;
        this.usuario_borra = usuario_borra;
        this.usuario_mod = usuario_mod;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_linea() {
        return id_linea;
    }

    public void setId_linea(int id_linea) {
        this.id_linea = id_linea;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public double getTotal_facturar() {
        return total_facturar;
    }

    public void setTotal_facturar(double total_facturar) {
        this.total_facturar = total_facturar;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
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
    
    
    
    
}
