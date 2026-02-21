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
        return obtenerEstadoJuego();
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

        return obtenerEstadoJuego();
    }

    public static EstadoJuego obtenerEstadoJuego() {
        return convertirAEstadoJuego();
    }

    //Convierte los datos reales de las unidades del tablero a DTO y crea al DTO estado de juego.
    private static EstadoJuego convertirAEstadoJuego() {
        EstadoJuego estadoJuegoDTO = new EstadoJuego();
        Partida partidaActual = motorJuego.getPartidaActual();

        estadoJuegoDTO.setTurno(partidaActual.getTurno());
        estadoJuegoDTO.setTiempoRestante(partidaActual.getReloj().getTiempoRestante());
        estadoJuegoDTO.setEstadoPartida(partidaActual.getEstado().name());

        TipoEquipo ganador = partidaActual.getGanador();
        estadoJuegoDTO.setGanador(ganador != null ? ganador.name() : null);


        List<DatosDrone> dronesDTO = new ArrayList<>();
        List<DatosPortaDron> portaDronesDTO = new ArrayList<>();
        List<DatosCelda> celdasOcupadas = new ArrayList<>();

        /*
         Bucle que recorre todo el tablero cargando los datos de drones
          y portadrones en la lista que
         que envia el DTO estado de juego. con la vista de juego actualizada
        */
        for (Unidad unidad : partidaActual.getUnidadesPorId().values()) {
            if (unidad instanceof Dron dron) {
                DatosDrone datosDrone = new DatosDrone();
                datosDrone.setId(dron.getId());
                datosDrone.setEquipo(determinarEquipo(dron));
                datosDrone.setCarga(dron instanceof DronAereo ? "bomba" : "misil");
                // API: x=columna, y=fila. Internamente: x=fila, y=columna.
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


}
