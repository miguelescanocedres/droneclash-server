package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.Dron;
import LogicaNegocio.Clases.ObjetosJuego.PortaDrones;
import LogicaNegocio.Clases.ObjetosJuego.Unidad;

public class MotorJuego {
    private Partida partidaActual;

    public MotorJuego() {
        this.partidaActual = new Partida();
    }

    public Partida getPartidaActual() {
        return partidaActual;
    }

    public boolean procesarMoverDron(String dronId, int targetX, int targetY) {
        Unidad unidad = partidaActual.buscarUnidadPorId(dronId);
        if (unidad == null || !(unidad instanceof Dron)) {
            return false;
        }

        Dron dron = (Dron) unidad;
        Posicion origen = dron.getPosicion();
        Posicion destino = new Posicion(targetX, targetY);

        if (!ReglasJuego.ValidarMovimiento(dron, origen, destino, partidaActual.getTablero())) {
            return false;
        }

        partidaActual.getTablero().moverUnidad(dron, origen, destino);
        dron.setPosicion(destino);
        dron.ConsumirPunto();
        return true;
    }

    public boolean procesarMoverPortaDrones(String portaDronesId, int targetX, int targetY) {
        Unidad unidad = partidaActual.buscarUnidadPorId(portaDronesId);
        if (unidad == null || !(unidad instanceof PortaDrones)) {
            return false;
        }

        PortaDrones pd = (PortaDrones) unidad;
        Posicion origen = pd.getPosicion();
        Posicion destino = new Posicion(targetX, targetY);

        if (!ReglasJuego.ValidarMovimiento(pd, origen, destino, partidaActual.getTablero())) {
            return false;
        }

        partidaActual.getTablero().moverUnidad(pd, origen, destino);
        pd.setPosicion(destino);
        pd.ConsumirPunto();
        return true;
    }

    public void CambiarTurno() {
    }

    public void EvaluarVictoria() {
    }
}