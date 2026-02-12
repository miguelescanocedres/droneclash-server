package ServJuego;

import ConexionServCli.DTO.*;
import LogicaNegocio.Clases.ControlJuego.MotorJuego;
import LogicaNegocio.Clases.ControlJuego.Partida;
import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.*;
import LogicaNegocio.Excepciones.ReglaJuegoException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioJuego {

    private static MotorJuego motorJuego;
    private static final int FILAS = 40;
    private static final int COLUMNAS = 40;

    public ServicioJuego() {
        inicializarJuego();
    }

    private void inicializarJuego() {
        motorJuego = new MotorJuego();
        Partida partida = motorJuego.getPartidaActual();

        Jugador jugadorA = new Jugador("playerA", "Player A");

        Posicion posPorta = new Posicion(5, 5);
        PortaDronesAereo porta = new PortaDronesAereo(posPorta, jugadorA);
        partida.getTablero().colocarUnidad(porta, posPorta);
        partida.registrarUnidad(porta);

        Posicion posDron = new Posicion(7, 7);
        DronAereo dron = new DronAereo(posDron, jugadorA);
        partida.getTablero().colocarUnidad(dron, posDron);
        partida.registrarUnidad(dron);
    }


    public static EstadoJuego procesarAccion(AccionJuego accion) throws ReglaJuegoException {

        switch (accion.getAccion()) {
            case "moverDron":
                motorJuego.procesarMoverDron(
                        accion.getIdDron(),
                        accion.getObjetivoX(),
                        accion.getObjetivoY()
                );
                System.out.println("INFO: Acción 'moverDron' procesada con éxito.");
                break;

            case "moverPortaDron":
                motorJuego.procesarMoverPortaDrones(
                        accion.getIdPortaDron(),
                        accion.getObjetivoX(),
                        accion.getObjetivoY()
                );
                System.out.println("INFO: Acción 'moverPortaDron' procesada con éxito.");
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
        estadoJuegoDTO.setEstadoPartida("playing");
        estadoJuegoDTO.setGanador(null);

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
                datosDrone.setEquipo("A");
                datosDrone.setCarga(dron instanceof DronAereo ? "bomba" : "misil");
                datosDrone.setX(dron.getPosicion().getX());
                datosDrone.setY(dron.getPosicion().getY());
                datosDrone.setCombustible(dron.getCombustibleActual());
                datosDrone.setRangoVision(dron.getVisionRango());
                datosDrone.setActivo(true);
                dronesDTO.add(datosDrone);

                celdasOcupadas.add(new DatosCelda(
                        dron.getPosicion().getY(), dron.getPosicion().getX(),
                        true, dron.getId()));

            } else if (unidad instanceof PortaDrones portaDron) {
                DatosPortaDron datosPortaDron = new DatosPortaDron();
                datosPortaDron.setId(portaDron.getId());
                datosPortaDron.setEquipo("A");
                datosPortaDron.setTipo(portaDron instanceof PortaDronesAereo ? "aereo" : "naval");
                datosPortaDron.setX(portaDron.getPosicion().getX());
                datosPortaDron.setY(portaDron.getPosicion().getY());
                datosPortaDron.setVida(portaDron.getVida());
                datosPortaDron.setRangoVision(portaDron.getVisionRango());
                portaDronesDTO.add(datosPortaDron);

                celdasOcupadas.add(new DatosCelda(
                        portaDron.getPosicion().getY(), portaDron.getPosicion().getX(),
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
}
