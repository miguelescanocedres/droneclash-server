package ConexionServCli.Conexion.Persistencia;

import LogicaNegocio.Enums.TipoEquipo;

public class UnidadDTO {

    private String tipoClase;
    private String id;
    private String equipo;
    private int x;
    private int y;

    // no siempre cargadas
    private Integer vida;       // portadron

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

    public Integer getVida() {
        return vida;
    }

    public void setVida(Integer vida) {
        this.vida = vida;
    }

    // getters/setters...
}