package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.*;
import LogicaNegocio.Enums.EstadoPartida;
import LogicaNegocio.Enums.TipoEquipo;
import LogicaNegocio.Excepciones.ReglaJuegoException;

import java.util.HashMap;
import java.util.Map;

public class Partida {
    public static final int ZONA_ROJO_COL_MIN = 0;
    public static final int ZONA_ROJO_COL_MAX = 12;
    public static final int ZONA_AZUL_COL_MIN = 27;
    public static final int ZONA_AZUL_COL_MAX = 39;

    private Equipo equipoRojo;
    private Equipo equipoAzul;
    private Tablero tablero;
    private TipoEquipo turnoActual;
    private Map<String, Unidad> unidadesPorId;
    private int turno;
    private RelojJuego reloj;
    private EstadoPartida estado;

    public Partida() {
        this.equipoRojo = new Equipo(TipoEquipo.ROJO_AEREO);
        this.equipoAzul = new Equipo(TipoEquipo.AZUL_NAVAL);
        this.tablero = new Tablero();
        this.unidadesPorId = new HashMap<>();
        this.turno = 1;
        this.reloj = new RelojJuego();
        this.estado = EstadoPartida.ESPERANDO_JUGADORES;
    }

    public TipoEquipo agregarJugador(String idJugador) throws ReglaJuegoException {
        if (estado != EstadoPartida.ESPERANDO_JUGADORES) {
            throw new ReglaJuegoException("No se pueden unir jugadores: la partida ya ha comenzado.");
        }
        if (equipoRojo.tieneJugador(idJugador) || equipoAzul.tieneJugador(idJugador)) {
            throw new ReglaJuegoException("El jugador con ID '" + idJugador + "' ya esta en la partida.");
        }

        Jugador nuevoJugador = new Jugador(idJugador, idJugador);

        if (equipoRojo.getCantidadJugadores() <= equipoAzul.getCantidadJugadores()) {
            equipoRojo.agregarJugador(nuevoJugador);
            return TipoEquipo.ROJO_AEREO;
        } else {
            equipoAzul.agregarJugador(nuevoJugador);
            return TipoEquipo.AZUL_NAVAL;
        }
    }

    public void iniciarPartida(Posicion posRojo, Posicion posAzul) throws ReglaJuegoException {
        if (estado != EstadoPartida.ESPERANDO_JUGADORES) {
            throw new ReglaJuegoException("La partida ya fue iniciada.");
        }
        if (equipoRojo.getCantidadJugadores() < 1) {
            throw new ReglaJuegoException("El equipo Rojo necesita al menos 1 jugador.");
        }
        if (equipoAzul.getCantidadJugadores() < 1) {
            throw new ReglaJuegoException("El equipo Azul necesita al menos 1 jugador.");
        }

        validarPosicionEnZona(posRojo, TipoEquipo.ROJO_AEREO);
        validarPosicionEnZona(posAzul, TipoEquipo.AZUL_NAVAL);

        if (!tablero.esPosicionValida(posRojo)) {
            throw new ReglaJuegoException("La posicion del PortaDrones Rojo es invalida o esta ocupada.");
        }
        if (!tablero.esPosicionValida(posAzul)) {
            throw new ReglaJuegoException("La posicion del PortaDrones Azul es invalida o esta ocupada.");
        }

        //Team Rojo - Comunistas
        Jugador liderRojo = equipoRojo.obtenerLider();
        PortaDronesAereo portaRojo = new PortaDronesAereo(posRojo, liderRojo);
        equipoRojo.setPortaDrones(portaRojo);
        tablero.colocarUnidad(portaRojo, posRojo);
        registrarUnidad(portaRojo);

        //Team Azul - Yankees
        Jugador liderAzul = equipoAzul.obtenerLider();
        PortaDronesNaval portaAzul = new PortaDronesNaval(posAzul, liderAzul);
        equipoAzul.setPortaDrones(portaAzul);
        tablero.colocarUnidad(portaAzul, posAzul);
        registrarUnidad(portaAzul);

        this.estado = EstadoPartida.EN_CURSO;
        this.turnoActual = TipoEquipo.ROJO_AEREO;
        this.turno = 1;
        reloj.iniciar();
    }

    // El numero de columnas obviamente lo podemos cambiar. Yo use estos numeros solo para probar
    private void validarPosicionEnZona(Posicion pos, TipoEquipo equipo) throws ReglaJuegoException {
        int columna = pos.getY();
        if (equipo == TipoEquipo.ROJO_AEREO) {
            if (columna < ZONA_ROJO_COL_MIN || columna > ZONA_ROJO_COL_MAX) {
                throw new ReglaJuegoException(
                        "PortaDrones Rojo debe colocarse en columnas 0-12 (zona Roja). Columna recibida: " + columna);
            }
        } else {
            if (columna < ZONA_AZUL_COL_MIN || columna > ZONA_AZUL_COL_MAX) {
                throw new ReglaJuegoException(
                        "PortaDrones Azul debe colocarse en columnas 27-39 (zona Azul). Columna recibida: " + columna);
            }
        }
    }

    public void registrarUnidad(Unidad unidad) {
        unidadesPorId.put(unidad.getId(), unidad);
    }

    public Unidad buscarUnidadPorId(String id) {
        return unidadesPorId.get(id);
    }

    public void eliminarUnidad(Unidad unidad) {
        if (unidad != null) {
            unidadesPorId.remove(unidad.getId());
        }
    }

    public Tablero getTablero() { return tablero; }
    public Map<String, Unidad> getUnidadesPorId() { return unidadesPorId; }
    public int getTurno() { return turno; }
    public void setTurno(int turno) { this.turno = turno; }
    public TipoEquipo getTurnoActual() { return turnoActual; }
    public void setTurnoActual(TipoEquipo turnoActual) { this.turnoActual = turnoActual; }
    public RelojJuego getReloj() { return reloj; }
    public EstadoPartida getEstado() { return estado; }
    public void setEstado(EstadoPartida estado) { this.estado = estado; }
    public Equipo getEquipoRojo() { return equipoRojo; }
    public Equipo getEquipoAzul() { return equipoAzul; }
}
