package modelo;

import java.sql.Date;

/**
 *
 * @author maria.enriquez
 */
public class Cliente {
    private int id_cliente;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private boolean eliminado;
    private Date fecha_aniade;
    private Date fecha_borra;
    private Date fecha_mod;
    private String nif;
    private int usaurio_aniade;
    private int usaurio_borra;
    private int usaurio_mod;
    private String telefono;

    public Cliente() {
    }
    
    

    public Cliente(int id_cliente, String nombre, String apellidos, String direccion, String email, boolean eliminado, Date fecha_aniade, Date fecha_borra, Date fecha_mod, String nif, int usaurio_aniade, int usaurio_borra, int usaurio_mod) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.email = email;
        this.eliminado = eliminado;
        this.fecha_aniade = fecha_aniade;
        this.fecha_borra = fecha_borra;
        this.fecha_mod = fecha_mod;
        this.nif = nif;
        this.usaurio_aniade = usaurio_aniade;
        this.usaurio_borra = usaurio_borra;
        this.usaurio_mod = usaurio_mod;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public int getUsaurio_aniade() {
        return usaurio_aniade;
    }

    public void setUsaurio_aniade(int usaurio_aniade) {
        this.usaurio_aniade = usaurio_aniade;
    }

    public int getUsaurio_borra() {
        return usaurio_borra;
    }

    public void setUsaurio_borra(int usaurio_borra) {
        this.usaurio_borra = usaurio_borra;
    }

    public int getUsaurio_mod() {
        return usaurio_mod;
    }

    public void setUsaurio_mod(int usaurio_mod) {
        this.usaurio_mod = usaurio_mod;
    }
    
    
    
    
    
}
