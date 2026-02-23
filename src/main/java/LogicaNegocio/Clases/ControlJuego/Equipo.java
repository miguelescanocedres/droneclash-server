package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Clases.ObjetosJuego.PortaDrones;
import LogicaNegocio.Enums.TipoEquipo;

import java.util.LinkedList;

public class Equipo {
    TipoEquipo tipoEquipo;
    LinkedList<Jugador> jugadores;
    PortaDrones portaDrones;
    boolean listoParaJugar = false;

    public void generarFlota(TipoEquipo tipo){

    }

    public void distribuirUnidadesEntreJugadores(){

    }
    public Jugador ObtenerLider (){
        return new Jugador();
    }

    //No toque las clases del Tino pero creo que no las necesitamos

    public Equipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
        this.jugadores = new LinkedList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugador.setEquipo(this.tipoEquipo);
        jugadores.add(jugador);
    }

    public Jugador obtenerLider() {
        if (jugadores.isEmpty()) {
            return null;
        }
        return jugadores.getFirst();
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
}
