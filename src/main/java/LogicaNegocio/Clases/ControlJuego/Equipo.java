package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Clases.ObjetosJuego.PortaDrones;
import LogicaNegocio.Enums.TipoEquipo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.LinkedList;

public class Equipo {
    TipoEquipo tipoEquipo;
    LinkedList<Jugador> jugadores;
    PortaDrones portaDrones;
    boolean listoParaJugar = false;




    public Equipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
        this.jugadores = new LinkedList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugador.setEquipo(this.tipoEquipo);
        jugadores.add(jugador);
    }


    public boolean tieneJugador(String idJugador) {
        return jugadores.stream().anyMatch(j -> j.getId().equals(idJugador)); // jugadores.find(idJugador));
    }

    public int getCantidadJugadores() {
        return jugadores.size();
    }

    public TipoEquipo getTipoEquipo() { return tipoEquipo; }
    public LinkedList<Jugador> getJugadores() { return jugadores; }
    public PortaDrones getPortaDrones() { return portaDrones; }
    public void setPortaDrones(PortaDrones portaDrones) { this.portaDrones = portaDrones; }

    public TipoEquipo  getTipo() {
        return this.tipoEquipo;
    }
}
