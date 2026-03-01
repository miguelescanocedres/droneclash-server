package ConexionServCli.DTO;

import java.util.UUID;

public class EventoCombate {
    private String idEvento;
    private String tipoEvento; // IMPACTO, DRON_DESTRUIDO, PORTADRON_DESTRUIDO
    private String idAtacante;
    private String idAtacado;
    private String tipoObjetivo; // DRON o PORTADRON
    private String equipoObjetivo; // ROJO_AEREO, AZUL_NAVAL
    private int vidaActualObjetivo; // Vida restante del objetivo

    public EventoCombate(String tipoEvento, String idAtacante, String idAtacado,
                         String tipoObjetivo, String equipoObjetivo, int vidaActualObjetivo) {
        this.idEvento = UUID.randomUUID().toString();
        this.tipoEvento = tipoEvento;
        this.idAtacante = idAtacante;
        this.idAtacado = idAtacado;
        this.tipoObjetivo = tipoObjetivo;
        this.equipoObjetivo = equipoObjetivo;
        this.vidaActualObjetivo = vidaActualObjetivo;
    }

    // Getters
    public String getIdEvento() { return idEvento; }
    public String getTipoEvento() { return tipoEvento; }
    public String getIdAtacante() { return idAtacante; }
    public String getIdAtacado() { return idAtacado; }
    public String getTipoObjetivo() { return tipoObjetivo; }
    public String getEquipoObjetivo() { return equipoObjetivo; }
    public int getVidaActualObjetivo() { return vidaActualObjetivo; }

    // Setters (si son necesarios, aunque para un evento suelen ser inmutables)
    public void setIdEvento(String idEvento) { this.idEvento = idEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }
    public void setIdAtacante(String idAtacante) { this.idAtacante = idAtacante; }
    public void setIdAtacado(String idAtacado) { this.idAtacado = idAtacado; }
    public void setTipoObjetivo(String tipoObjetivo) { this.tipoObjetivo = tipoObjetivo; }
    public void setEquipoObjetivo(String equipoObjetivo) { this.equipoObjetivo = equipoObjetivo; }
    public void setVidaActualObjetivo(int vidaActualObjetivo) { this.vidaActualObjetivo = vidaActualObjetivo; }
}
