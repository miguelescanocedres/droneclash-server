package ConexionServCli.DTO;

import java.util.List;

public class RespuestaEquipos {
    private List<DatosJugador> equipoAereo;
    private List<DatosJugador> equipoNaval;

    public RespuestaEquipos(List<DatosJugador> equipoAereo, List<DatosJugador> equipoNaval) {
        this.equipoAereo = equipoAereo;
        this.equipoNaval = equipoNaval;
    }

    public List<DatosJugador> getEquipoAereo() { return equipoAereo; }
    public void setEquipoAereo(List<DatosJugador> equipoAereo) { this.equipoAereo = equipoAereo; }
    public List<DatosJugador> getEquipoNaval() { return equipoNaval; }
    public void setEquipoNaval(List<DatosJugador> equipoNaval) { this.equipoNaval = equipoNaval; }
}
