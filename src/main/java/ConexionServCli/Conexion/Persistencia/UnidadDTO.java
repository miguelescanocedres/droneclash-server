package ConexionServCli.Conexion.Persistencia;

import LogicaNegocio.Enums.TipoEquipo;

public class UnidadDTO {

    private String tipoClase;
    private String id;
    private String equipo;
    private int x;
    private int y;
    private String estado;
    private int combustibleActual;

    // no siempre cargadas
    private Integer vida;       // portadron
    private Integer municion;   // dron

    public UnidadDTO() {}

    public String getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(String tipoClase) {
        this.tipoClase = tipoClase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCombustibleActual() {
        return combustibleActual;
    }

    public void setCombustibleActual(int combustibleActual) {
        this.combustibleActual = combustibleActual;
    }

    public Integer getVida() {
        return vida;
    }

    public void setVida(Integer vida) {
        this.vida = vida;
    }

    public Integer getMunicion() {
        return municion;
    }

    public void setMunicion(Integer municion) {
        this.municion = municion;
    }

    // getters/setters...
}