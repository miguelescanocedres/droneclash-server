package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Clases.ObjetosJuego.Unidad;
import LogicaNegocio.Enums.TipoEquipo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Partida {
    private LinkedList<Jugador> jugadoresRojos;
    private LinkedList<Jugador> jugadoresAzules;
    private Tablero tablero;
    private TipoEquipo turnoActual;
    private Map<String, Unidad> unidadesPorId;
    private int turno;

    public Partida() {
        this.tablero = new Tablero();
        this.jugadoresRojos = new LinkedList<>();
        this.jugadoresAzules = new LinkedList<>();
        this.unidadesPorId = new HashMap<>();
        this.turno = 1;
    }

    public void registrarUnidad(Unidad unidad) {
        unidadesPorId.put(unidad.getId(), unidad);
    }

    public Unidad buscarUnidadPorId(String id) {
        return unidadesPorId.get(id);
    }

    public Tablero getTablero() { return tablero; }
    public Map<String, Unidad> getUnidadesPorId() { return unidadesPorId; }
    public int getTurno() { return turno; }
    public void setTurno(int turno) { this.turno = turno; }
    public TipoEquipo getTurnoActual() { return turnoActual; }

    public void iniciarPartida() {
    }


    public void eliminarUnidad(Unidad unidad) {
        if (unidad != null) {
            unidadesPorId.remove(unidad.getId());
        }
    }
}