package ConexionServCli.DTO;

/**
 DTO que representa los datos de un Dron para el cliente.
 */
public class DatosDrone {
    private String id;
    private String equipo; // El equipo al que pertenece
    private String carga; // "bomba" | "misil"
    private String idJugador; // jugador que lo controla
    private int x;
    private int y;
    private int combustible;
    private int rangoVision;
    private boolean activo;

    // Getters y Setters
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

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
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

    public int getCombustible() {
        return combustible;
    }

    public void setCombustible(int combustible) {
        this.combustible = combustible;
    }

    public int getRangoVision() {
        return rangoVision;
    }

    public void setRangoVision(int rangoVision) {
        this.rangoVision = rangoVision;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
