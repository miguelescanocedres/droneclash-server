package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.*;
import LogicaNegocio.Enums.EstadoPartida;
import LogicaNegocio.Enums.TipoEquipo;
import LogicaNegocio.Excepciones.ReglaJuegoException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Partida {
    private static final int ANCHO_ZONA_DESPLIEGUE = Math.max(1, Tablero.COLUMNAS / 3);
    public static final int ZONA_ROJO_COL_MIN = 0;
    public static final int ZONA_ROJO_COL_MAX = ANCHO_ZONA_DESPLIEGUE - 1;
    public static final int ZONA_AZUL_COL_MIN = Tablero.COLUMNAS - ANCHO_ZONA_DESPLIEGUE;
    public static final int ZONA_AZUL_COL_MAX = Tablero.COLUMNAS - 1;

    private Equipo equipoRojo;
    private Equipo equipoAzul;
    private Tablero tablero;
    private TipoEquipo turnoActual;
    private Map<String, Unidad> unidadesPorId;
    private int turno;
    private RelojJuego reloj;
    private EstadoPartida estado;
    private TipoEquipo ganador;

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

    public void iniciarPartida() throws ReglaJuegoException {
        if (estado != EstadoPartida.ESPERANDO_JUGADORES) {
            throw new ReglaJuegoException("La partida ya fue iniciada.");
        }
        if (equipoRojo.getCantidadJugadores() == 0 || equipoAzul.getCantidadJugadores() == 0) {
            throw new ReglaJuegoException("No hay suficientes jugadores para iniciar la partida.");
        }

        // Colocación del equipo ROJO
        Posicion posPortaRojo = generarPosicionAleatoriaEnZona(TipoEquipo.ROJO_AEREO);
        PortaDronesAereo portaRojo = new PortaDronesAereo(posPortaRojo, equipoRojo);
        this.registrarUnidad(portaRojo);
        this.tablero.colocarUnidad(portaRojo, posPortaRojo);
        equipoRojo.setPortaDrones(portaRojo);
        generarDronesAlrededor(portaRojo, equipoRojo);

        //  Colocación del equipo AZUL
        Posicion posPortaAzul = generarPosicionAleatoriaEnZona(TipoEquipo.AZUL_NAVAL);
        PortaDronesNaval portaAzul = new PortaDronesNaval(posPortaAzul, equipoAzul);
        this.registrarUnidad(portaAzul);
        this.tablero.colocarUnidad(portaAzul, posPortaAzul);
        equipoAzul.setPortaDrones(portaAzul);
        generarDronesAlrededor(portaAzul, equipoAzul);

        // Cambiar el estado de la partida
        this.setEstado(EstadoPartida.EN_CURSO);
        this.setTurnoActual(TipoEquipo.ROJO_AEREO);
        this.setTurno(1);
        this.reloj.iniciar();

    }

    private Posicion generarPosicionAleatoriaEnZona(TipoEquipo equipo) {

        Random rand = new Random();

        //  genera un número entre 0 y FILAS_TABLERO - 1.
        int fila = rand.nextInt(Tablero.FILAS);
        int columna;
        //  Determinamos el rango de columnas según el equipo.
        if (equipo == TipoEquipo.ROJO_AEREO) {
            // rand.nextInt(min, max + 1) genera un número entre min (incluido) y max (incluido).
            columna = rand.nextInt(ZONA_ROJO_COL_MIN, ZONA_ROJO_COL_MAX + 1);
        } else {
            // Si es el equipo Azul, generamos una columna en su zona de despliegue.
            columna = rand.nextInt(ZONA_AZUL_COL_MIN, ZONA_AZUL_COL_MAX + 1);
        }

        return new Posicion(fila, columna);
    }

    private void generarDronesAlrededor(PortaDrones portaDrones, Equipo equipo) {
        int cantidadDronesACrear = (equipo.tipoEquipo== TipoEquipo.ROJO_AEREO) ? 12 : 6;

        int dronesCreados = 0;
        int radioDeBusqueda = 1; // Empezamos buscando en las casillas adyacentes (radio 1 = cuadrado 3x3)

        Posicion centro = portaDrones.getPosicion();


        while (dronesCreados < cantidadDronesACrear) {

            // Recorremos un cuadrado definido por el radio de búsqueda actual.
            for (int x = centro.getX() - radioDeBusqueda; x <= centro.getX() + radioDeBusqueda; x++) {
                for (int y = centro.getY() - radioDeBusqueda; y <= centro.getY() + radioDeBusqueda; y++) {

                    if (dronesCreados >= cantidadDronesACrear) {
                        return;
                    }

                    // --- Comprobaciones para cada casilla ---
                    if (!tablero.estaDentroDelTablero(x, y)) {
                        continue;
                    }
                    if (tablero.getCelda(x, y).estaOcupada()) {
                        continue;
                    }
                    // Si la casilla es válida, creamos un dron
                    Posicion posDron = new Posicion(x, y);
                    Dron nuevoDron;

                    if (equipo.getTipoEquipo() == TipoEquipo.ROJO_AEREO) {
                        nuevoDron = new DronAereo(posDron,equipo);
                    } else {
                        nuevoDron = new DronNaval(posDron,equipo);
                    }
                    this.registrarUnidad(nuevoDron);
                    this.tablero.colocarUnidad(nuevoDron, posDron);
                    dronesCreados++;


                }
            }
            // Si terminamos de recorrer el cuadrado y aún faltan drones,
            // aumentamos el radio para buscar en un área más grande en la siguiente iteración.
            radioDeBusqueda++;
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
    public TipoEquipo getGanador() { return ganador; }
    public void setGanador(TipoEquipo ganador) { this.ganador = ganador; }

}
