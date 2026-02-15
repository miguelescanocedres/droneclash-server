package ConexionServCli.DTO;

import java.util.List;


 // DTO que representa el estado completo del juego para el cliente.

public class EstadoJuego {
    private int turno;
    private String idUnidadActiva;
    private String estadoPartida;
    private String ganador;
    private List<DatosPortaDron> portaDrones;
    private List<DatosDrone> drones;
    private DatosTablero tablero;
    private int tiempoRestante;




    public int getTurno() {
        return turno;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }

    public String getIdUnidadActiva() {
        return idUnidadActiva;
    }

    public void setIdUnidadActiva(String idUnidadActiva) {
        this.idUnidadActiva = idUnidadActiva;
    }

    public String getEstadoPartida() {
        return estadoPartida;
    }

    public void setEstadoPartida(String estadoPartida) {
        this.estadoPartida = estadoPartida;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public List<DatosPortaDron> getPortaDrones() {
        return portaDrones;
    }

    public void setPortaDrones(List<DatosPortaDron> portaDrones) {
        this.portaDrones = portaDrones;
    }

    public List<DatosDrone> getDrones() {
        return drones;
    }

    public void setDrones(List<DatosDrone> drones) {
        this.drones = drones;
    }

    public DatosTablero getTablero() {
        return tablero;
    }

    public void setTablero(DatosTablero tablero) {
        this.tablero = tablero;
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public void setTiempoRestante(int tiempoRestante) {
        this.tiempoRestante = tiempoRestante;
    }
}
