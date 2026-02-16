package ConexionServCli.DTO;

/**
 * DTO que representa los datos de un PortaDron para el cliente.
 * Los nombres de los campos están en español.
 */
public class DatosPortaDron {
    private String id;
    private String equipo;
    private String tipo; // "aereo" | "naval"
    private String idJugador; // jugador que lo controla
    private int x;
    private int y;
    private int vida;
    private int rangoVision;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getRangoVision() {
        return rangoVision;
    }

    public void setRangoVision(int rangoVision) {
        this.rangoVision = rangoVision;
    }
}
