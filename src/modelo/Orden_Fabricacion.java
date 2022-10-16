package modelo;

import java.sql.Date;

/**
 *
 * @author maria.enriquez
 */
public class Orden_Fabricacion {
    
    private int id_of;
    private Date fecha_aniade;
    private Date fecha_borra;
    private Date fecha_mod;
    private Date fecha_fin;
    private Date fecha_inicio;
    private boolean eliminado;
    private double cantidad;
    private int usuario_aniade;
    private int usuario_borra;
    private int usuario_mod;

    public Orden_Fabricacion() {
    }

    public Orden_Fabricacion(int id_of, Date fecha_aniade, Date fecha_borra, Date fecha_mod, Date fecha_fin, Date fecha_inicio, boolean eliminado, double cantidad, int usuario_aniade, int usuario_borra, int usuario_mod) {
        this.id_of = id_of;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.fecha_fin = fecha_fin;
        this.fecha_inicio = fecha_inicio;
        this.eliminado = eliminado;
        this.cantidad = cantidad;
        this.usuario_aniade = usuario_aniade;
        this.usuario_borra = usuario_borra;
        this.usuario_mod = usuario_mod;
    }

    public int getId_of() {
        return id_of;
    }

    public void setId_of(int id_of) {
        this.id_of = id_of;
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

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
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
