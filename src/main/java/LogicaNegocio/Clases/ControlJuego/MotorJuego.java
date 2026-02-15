package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.Dron;
import LogicaNegocio.Clases.ObjetosJuego.PortaDrones;
import LogicaNegocio.Clases.ObjetosJuego.Unidad;
import LogicaNegocio.Excepciones.ReglaJuegoException;
import LogicaNegocio.Enums.TipoEquipo;


public class MotorJuego {
    private Partida partidaActual;

    public MotorJuego() {
        this.partidaActual = new Partida();
        // Cuando el reloj llega a 0, cambia el turno automáticamente
        this.partidaActual.getReloj().setAlAgotarse(this::CambiarTurno);


    }

    public Partida getPartidaActual() {
        return partidaActual;
    }

    /**
     * Procesa el movimiento de un dron.
     * No devuelve nada si tiene éxito.
     * @throws ReglaJuegoException si el movimiento no es válido por cualquier motivo.
     */
    public void procesarMoverDron(String dronId, int targetX, int targetY) throws ReglaJuegoException {
        System.out.println("--- PROCESANDO MOVIMIENTO DE DRON ---");
        System.out.println("Buscando dron con ID: '" + dronId + "'");
        Unidad unidad = partidaActual.buscarUnidadPorId(dronId);

        if (unidad == null) {
            throw new ReglaJuegoException("No se encontró ninguna unidad con el ID: " + dronId);
        }
        if (!(unidad instanceof Dron)) {
            throw new ReglaJuegoException("La unidad con ID " + dronId + " no es un Dron, es un " + unidad.getClass().getSimpleName());
        }
        System.out.println("Dron encontrado con éxito.");

        Dron dron = (Dron) unidad;
        Posicion origen = dron.getPosicion();
        Posicion destino = new Posicion(targetX, targetY);

        System.out.println("Validando movimiento desde (" + origen.getX() + "," + origen.getY() + ") hasta (" + destino.getX() + "," + destino.getY() + ")");
        boolean esMovimientoValido = ReglasJuego.ValidarMovimiento(dron, origen, destino, partidaActual.getTablero());

        if (!esMovimientoValido) {
            throw new ReglaJuegoException("El movimiento fue invalidado por ReglasJuego.ValidarMovimiento.");
        }
        System.out.println("Movimiento validado por las reglas.");

        partidaActual.getTablero().moverUnidad(dron, origen, destino);
        dron.setPosicion(destino);
        dron.ConsumirPunto();
        System.out.println("--- MOVIMIENTO DE DRON COMPLETADO ---");
    }

    /**
     * Procesa el movimiento de un porta-drones.
     * No devuelve nada si tiene éxito.
     * @throws ReglaJuegoException si el movimiento no es válido.
     */
    public void procesarMoverPortaDrones(String portaDronesId, int targetX, int targetY) throws ReglaJuegoException {
        Unidad unidad = partidaActual.buscarUnidadPorId(portaDronesId);
        if (unidad == null || !(unidad instanceof PortaDrones)) {
            throw new ReglaJuegoException("No se encontró el PortaDrones con ID: " + portaDronesId);
        }

        PortaDrones pd = (PortaDrones) unidad;
        Posicion origen = pd.getPosicion();
        Posicion destino = new Posicion(targetX, targetY);

        if (!ReglasJuego.ValidarMovimiento(pd, origen, destino, partidaActual.getTablero())) {
            throw new ReglaJuegoException("Movimiento de PortaDrones invalidado por las reglas.");
        }

        partidaActual.getTablero().moverUnidad(pd, origen, destino);
        pd.setPosicion(destino);
        pd.ConsumirPunto();
    }

    public void CambiarTurno() {
        Partida partida = this.partidaActual;
        // Alterna entre equipos
        if (partida.getTurnoActual() == TipoEquipo.ROJO_AEREO) {
            partida.setTurnoActual(TipoEquipo.AZUL_NAVAL);
        } else {
            partida.setTurnoActual(TipoEquipo.ROJO_AEREO);
        }
        partida.setTurno(partida.getTurno() + 1);
        // Reinicia el reloj para el nuevo turno
        partida.getReloj().reiniciar();
        System.out.println("--- TURNO CAMBIADO: Turno " + partida.getTurno() + " - Equipo: " + partida.getTurnoActual() + " ---");
    }


    public void EvaluarVictoria() {
    }
}
