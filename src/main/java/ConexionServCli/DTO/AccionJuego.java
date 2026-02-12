package ConexionServCli.DTO;

/**
 * DTO que representa una acción enviada por el cliente.
 */
public class AccionJuego {
    private String accion; // "moverDron", "moverPortaDron", etc.
    private String idJugador;
    private String idDron;
    private Integer objetivoX;
    private Integer objetivoY;
    private String idPortaDron;
    private String carga; // "bomba" | "misil"

    // Getters y Setters
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public String getIdDron() {
        return idDron;
    }

    public void setIdDron(String idDron) {
        this.idDron = idDron;
    }

    public Integer getObjetivoX() {
        return objetivoX;
    }

    public void setObjetivoX(Integer objetivoX) {
        this.objetivoX = objetivoX;
    }

    public Integer getObjetivoY() {
        return objetivoY;
    }

    public void setObjetivoY(Integer objetivoY) {
        this.objetivoY = objetivoY;
    }

    public String getIdPortaDron() {
        return idPortaDron;
    }

    public void setIdPortaDron(String idPortaDron) {
        this.idPortaDron = idPortaDron;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }
}
