package ServJuego;

import ConexionServCli.DTO.*;
import LogicaNegocio.Clases.ControlJuego.Tablero;
import LogicaNegocio.Clases.ControlJuego.MotorJuego;
import LogicaNegocio.Clases.ControlJuego.Partida;
import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Clases.ObjetosJuego.*;
import LogicaNegocio.Excepciones.ReglaJuegoException;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import org.springframework.stereotype.Service;
import LogicaNegocio.Enums.TipoEquipo;
import ConexionServCli.DTO.DatosJugador;
import ConexionServCli.DTO.RespuestaEquipos;
import java.util.HashSet;
import java.util.Set;




import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioJuego {

    private static MotorJuego motorJuego;
    private static final int FILAS = Tablero.FILAS;
    private static final int COLUMNAS = Tablero.COLUMNAS;

    /*public ServicioJuego() {
        inicializarJuego();
    }

    private void inicializarJuego() {
        motorJuego = new MotorJuego();
        Partida partida = motorJuego.getPartidaActual();

        Jugador jugadorA = new Jugador("playerA", "Player A");
        Jugador jugadorB = new Jugador("playerB", "Player B");
        Posicion posDronA = new Posicion(7, 7);
        DronAereo dronA = new DronAereo(posDronA, jugadorA);
        partida.getTablero().colocarUnidad(dronA, posDronA);
        partida.registrarUnidad(dronA);

        Posicion posDronB = new Posicion(7, 10);
        DronNaval dronB = new DronNaval(posDronB, jugadorB);
        partida.getTablero().colocarUnidad(dronB, posDronB);
        partida.registrarUnidad(dronB);

        Posicion posPorta = new Posicion(5, 5);
        PortaDronesAereo porta = new PortaDronesAereo(posPorta, jugadorA);
        partida.getTablero().colocarUnidad(porta, posPorta);
        partida.registrarUnidad(porta);
    }*/

    public ServicioJuego() {
        motorJuego = new MotorJuego();
    }

    public static RespuestaUnirseJugador unirJugador(SolicitudUnirseJugador solicitud)
            throws ReglaJuegoException {

        String idJugador = solicitud.getIdJugador();
        if (idJugador == null || idJugador.trim().isEmpty()) {
            throw new ReglaJuegoException("El idJugador es requerido.");
        }

        TipoEquipo equipoAsignado = motorJuego.agregarJugador(idJugador);

        Partida partida = motorJuego.getPartidaActual();
        Equipo equipo = (equipoAsignado == TipoEquipo.ROJO_AEREO)
                ? partida.getEquipoRojo()
                : partida.getEquipoAzul();

        Jugador jugadorAgregado = equipo.getJugadores().stream()
                .filter(j -> j.getId().equals(idJugador))
                .findFirst()
                .orElseThrow(() -> new ReglaJuegoException("Error interno: jugador no encontrado."));

        return new RespuestaUnirseJugador(
                idJugador,
                equipoAsignado.name(),
                partida.getEquipoRojo().getCantidadJugadores(),
                partida.getEquipoAzul().getCantidadJugadores()
        );
    }

    public static EstadoJuego iniciarPartida()
            throws ReglaJuegoException {

        motorJuego.iniciarJuego();
        return obtenerEstadoJuego(null);
    }



    public static EstadoJuego procesarAccion(AccionJuego accion) throws ReglaJuegoException {

        switch (accion.getAccion()) {
            case "moverDron":
                motorJuego.procesarMoverDron(
                        accion.getIdDron(),
                        accion.getObjetivoY(),
                        accion.getObjetivoX()
                );
                System.out.println("INFO: Acción 'moverDron' prxocesada con éxito.");
                break;

            case "moverPortaDron":
                motorJuego.procesarMoverPortaDrones(
                        accion.getIdPortaDron(),
                        accion.getObjetivoY(),
                        accion.getObjetivoX()
                );
                System.out.println("INFO: Acción 'moverPortaDron' procesada con éxito.");
                break;

            case "dispararDron":
                motorJuego.procesarDispararDron(
                        accion.getIdDron(),
                        accion.getObjetivoY(),
                        accion.getObjetivoX());
                break;


            default:
                // Si la acción no es conocida, también podemos lanzar una excepción.
                throw new ReglaJuegoException("Acción desconocida o no implementada: " + accion.getAccion());
        }

        //Devuelvo el equipo
        String idUnidad = accion.getIdDron() != null ? accion.getIdDron() : accion.getIdPortaDron();
        Unidad actor = motorJuego.getPartidaActual().buscarUnidadPorId(idUnidad);
        TipoEquipo equipoActor = (actor != null) ? actor.getEquipo().getTipoEquipo() : null;
        return obtenerEstadoJuego(equipoActor);

    }

    public static EstadoJuego obtenerEstadoJuego(TipoEquipo equipo) {
        return convertirAEstadoJuego(equipo);
    }


    //Convierte los datos reales de las unidades del tablero a DTO y crea al DTO estado de juego.
    private static EstadoJuego convertirAEstadoJuego(TipoEquipo equipoSolicitante) {
        EstadoJuego estadoJuegoDTO = new EstadoJuego();
        Partida partidaActual = motorJuego.getPartidaActual();

        estadoJuegoDTO.setTurno(partidaActual.getTurno());
        estadoJuegoDTO.setTiempoRestante(partidaActual.getReloj().getTiempoRestante());
        estadoJuegoDTO.setEstadoPartida(partidaActual.getEstado().name());

        TipoEquipo ganador = partidaActual.getGanador();
        estadoJuegoDTO.setGanador(ganador != null ? ganador.name() : null);

        // Calcular FOW: qué celdas ve el equipo solicitante
        Set<String> celdasVisiblesSet = (equipoSolicitante != null)
                ? calcularCeldasVisibles(equipoSolicitante, partidaActual)
                : null; // null = sin filtro (debug / inicio)

        List<DatosDrone> dronesDTO = new ArrayList<>();
        List<DatosPortaDron> portaDronesDTO = new ArrayList<>();
        List<DatosCelda> celdasOcupadas = new ArrayList<>();

        for (Unidad unidad : partidaActual.getUnidadesPorId().values()) {
            boolean esPropia = (equipoSolicitante == null)
                    || unidad.getEquipo().getTipoEquipo() == equipoSolicitante;
            String celdaKey = unidad.getPosicion().getX() + "," + unidad.getPosicion().getY();
            boolean esVisible = esPropia || celdasVisiblesSet == null || celdasVisiblesSet.contains(celdaKey);

            if (!esVisible) continue;

            if (unidad instanceof Dron dron) {
                DatosDrone datosDrone = new DatosDrone();
                datosDrone.setId(dron.getId());
                datosDrone.setEquipo(determinarEquipo(dron));
                datosDrone.setCarga(dron instanceof DronAereo ? "bomba" : "misil");
                datosDrone.setX(dron.getPosicion().getY());
                datosDrone.setY(dron.getPosicion().getX());
                datosDrone.setCombustible(dron.getCombustibleActual());
                datosDrone.setRangoVision(dron.getVisionRango());
                datosDrone.setActivo(true);
                dronesDTO.add(datosDrone);

                celdasOcupadas.add(new DatosCelda(
                        dron.getPosicion().getX(), dron.getPosicion().getY(),
                        true, dron.getId()));

            } else if (unidad instanceof PortaDrones portaDron) {
                DatosPortaDron datosPortaDron = new DatosPortaDron();
                datosPortaDron.setId(portaDron.getId());
                datosPortaDron.setEquipo(determinarEquipo(portaDron));
                datosPortaDron.setTipo(portaDron instanceof PortaDronesAereo ? "aereo" : "naval");
                datosPortaDron.setX(portaDron.getPosicion().getY());
                datosPortaDron.setY(portaDron.getPosicion().getX());
                datosPortaDron.setVida(portaDron.getVida());
                datosPortaDron.setRangoVision(portaDron.getVisionRango());
                portaDronesDTO.add(datosPortaDron);

                celdasOcupadas.add(new DatosCelda(
                        portaDron.getPosicion().getX(), portaDron.getPosicion().getY(),
                        true, portaDron.getId()));
            }
        }

        estadoJuegoDTO.setDrones(dronesDTO);
        estadoJuegoDTO.setPortaDrones(portaDronesDTO);
        estadoJuegoDTO.setTablero(new DatosTablero(FILAS, COLUMNAS, celdasOcupadas));

        // Convertir celdas visibles a coordendas API [columna, fila] para el DTO
        if (celdasVisiblesSet != null) {
            List<int[]> celdasVisiblesList = new ArrayList<>();
            for (String key : celdasVisiblesSet) {
                String[] parts = key.split(",");
                int fila = Integer.parseInt(parts[0]);
                int col  = Integer.parseInt(parts[1]);
                celdasVisiblesList.add(new int[]{col, fila}); // API: x=col, y=fila
            }
            estadoJuegoDTO.setCeldasVisibles(celdasVisiblesList);
        }

        if (!dronesDTO.isEmpty()) {
            estadoJuegoDTO.setIdUnidadActiva(dronesDTO.get(0).getId());
        }

        return estadoJuegoDTO;
    }


    private static String determinarEquipo(Unidad unidad) {
        if (unidad.getEquipo() == null || unidad.getEquipo().getTipoEquipo() == null) {
            return "DESCONOCIDO";
        }
        return unidad.getEquipo().getTipoEquipo().name();
    }

    public static RespuestaEquipos obtenerEquipos() {
        Partida partida = motorJuego.getPartidaActual();

        List<DatosJugador> equipoAereo = new ArrayList<>();
        for (Jugador j : partida.getEquipoRojo().getJugadores()) {
            equipoAereo.add(new DatosJugador(j.getId(), j.getNombre()));
        }

        List<DatosJugador> equipoNaval = new ArrayList<>();
        for (Jugador j : partida.getEquipoAzul().getJugadores()) {
            equipoNaval.add(new DatosJugador(j.getId(), j.getNombre()));
        }

        return new RespuestaEquipos(equipoAereo, equipoNaval);
    }

    private static Set<String> calcularCeldasVisibles(TipoEquipo tipoEquipo, Partida partida) {
        Equipo equipo = (tipoEquipo == TipoEquipo.ROJO_AEREO)
                ? partida.getEquipoRojo() : partida.getEquipoAzul();
        Set<String> visibles = new HashSet<>();

        for (Unidad u : partida.getUnidadesPorId().values()) {
            if (u.getEquipo() == equipo && u.getVisionRango() > 0) {
                int fila  = u.getPosicion().getX();
                int col   = u.getPosicion().getY();
                int rango = u.getVisionRango();
                for (int df = -rango; df <= rango; df++) {
                    for (int dc = -rango; dc <= rango; dc++) {
                        if (Math.sqrt(df * df + dc * dc) <= rango) {  // circular (Euclidiana)
                            int nf = fila + df, nc = col + dc;
                            if (partida.getTablero().estaDentroDelTablero(nf, nc))
                                visibles.add(nf + "," + nc);
                        }
                    }
                }
            }
        }
        return visibles;
    }


}
