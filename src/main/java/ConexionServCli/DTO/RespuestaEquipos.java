package ConexionServCli.DTO;

import java.util.List;

public class RespuestaEquipos {
    private List<DatosJugador> equipoAereo;
    private List<DatosJugador> equipoNaval;
    private int segundosRestantesInicio;

    public RespuestaEquipos(List<DatosJugador> equipoAereo, List<DatosJugador> equipoNaval) {
        this(equipoAereo, equipoNaval, 0);
    }

    public RespuestaEquipos(List<DatosJugador> equipoAereo, List<DatosJugador> equipoNaval, int segundosRestantesInicio) {
        this.equipoAereo = equipoAereo;
        this.equipoNaval = equipoNaval;
        this.segundosRestantesInicio = segundosRestantesInicio;
    }

    public List<DatosJugador> getEquipoAereo() { return equipoAereo; }
    public void setEquipoAereo(List<DatosJugador> equipoAereo) { this.equipoAereo = equipoAereo; }
    public List<DatosJugador> getEquipoNaval() { return equipoNaval; }
    public void setEquipoNaval(List<DatosJugador> equipoNaval) { this.equipoNaval = equipoNaval; }
    public int getSegundosRestantesInicio() { return segundosRestantesInicio; }
    public void setSegundosRestantesInicio(int segundosRestantesInicio) { this.segundosRestantesInicio = segundosRestantesInicio; }
}
