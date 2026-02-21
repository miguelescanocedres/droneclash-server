package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.Dron;
import LogicaNegocio.Clases.ObjetosJuego.PortaDrones;
import LogicaNegocio.Clases.ObjetosJuego.Unidad;
import LogicaNegocio.Excepciones.ReglaJuegoException;
import LogicaNegocio.Enums.EstadoPartida;
import LogicaNegocio.Enums.TipoEquipo;




public class MotorJuego {
    private Partida partidaActual;

    public MotorJuego() {
        this.partidaActual = new Partida();
        this.partidaActual.getReloj().setAlAgotarse(this::CambiarTurno);
    }


    public Partida getPartidaActual() {
        return partidaActual;
    }

    public TipoEquipo agregarJugador(String idJugador) throws ReglaJuegoException {
        return partidaActual.agregarJugador(idJugador);
    }

    public void iniciarJuego() throws ReglaJuegoException {
        partidaActual.iniciarPartida();
    }

    private void validarPartidaEnCurso() throws ReglaJuegoException {
        if (partidaActual.getEstado() != EstadoPartida.EN_CURSO) {
            throw new ReglaJuegoException("No se puede realizar acciones: la partida no esta en curso.");
        }
    }


    public void procesarMoverDron(String dronId, int targetX, int targetY) throws ReglaJuegoException {
        validarPartidaEnCurso();
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

        boolean esMovimientoValido = ReglasJuego.ValidarMovimiento(dron, origen, destino, partidaActual.getTablero());

        if (!esMovimientoValido) {
            throw new ReglaJuegoException("El movimiento fue invalidado por ReglasJuego.ValidarMovimiento.");
        }
        System.out.println("Movimiento validado");

        partidaActual.getTablero().moverUnidad(dron, origen, destino);
        dron.setPosicion(destino);
        dron.ConsumirCombustible();
        System.out.println("--- MOVIMIENTO DE DRON COMPLETADO ---");
    }


    public void procesarMoverPortaDrones(String portaDronesId, int targetX, int targetY) throws ReglaJuegoException {
        validarPartidaEnCurso();
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
        pd.ConsumirCombustible();
    }

    public void procesarDispararDron (String dronId, int targetX, int targetY) throws  ReglaJuegoException
    {
        validarPartidaEnCurso();
        Unidad unidadAtacante = partidaActual.buscarUnidadPorId(dronId);
        Dron atacante = (Dron) unidadAtacante;
        Posicion posObjetivo = new Posicion(targetX, targetY);


        boolean esDisparoValido = ReglasJuego.ValidarAtaque(atacante,posObjetivo);
        if(!esDisparoValido) {
            throw new ReglaJuegoException("El ataque fue invalidado por Reglas de juego");
        }
        System.out.println("Disparo validado");

        atacante.ConsumirMunicion();
        atacante.ConsumirCombustible();
        Unidad unidadObjetivo = partidaActual.getTablero().getCelda(targetX, targetY).getUnidad();
        ReglasJuego.AplicarImpacto(atacante,unidadObjetivo,partidaActual);

        EvaluarVictoria(); // no estoy seguro si va aca pero creo que no hay otro lugar logico


    }


    public void CambiarTurno() {
        if (partidaActual.getEstado() != EstadoPartida.EN_CURSO) return; // si termina la partdia no quiero cambiar turno

        Partida partida = this.partidaActual;
        if (partida.getTurnoActual() == TipoEquipo.ROJO_AEREO) {
            partida.setTurnoActual(TipoEquipo.AZUL_NAVAL);
        } else {
            partida.setTurnoActual(TipoEquipo.ROJO_AEREO);
        }
        partida.setTurno(partida.getTurno() + 1);
        partida.getReloj().reiniciar();
        System.out.println("--- TURNO CAMBIADO: Turno " + partida.getTurno() + " - Equipo: " + partida.getTurnoActual() + " ---");
    }

    public void EvaluarVictoria() {
        Partida partida = this.partidaActual;
        if (partida.getEstado() != EstadoPartida.EN_CURSO) return;

        PortaDrones portaRojo = partida.getEquipoRojo().getPortaDrones();
        PortaDrones portaAzul = partida.getEquipoAzul().getPortaDrones();

        boolean rojoDestruido = portaRojo != null && portaRojo.getVida() <= 0;
        boolean azulDestruido = portaAzul != null && portaAzul.getVida() <= 0;

        if (rojoDestruido) {
            partida.setGanador(TipoEquipo.AZUL_NAVAL);
            partida.setEstado(EstadoPartida.FINALIZADA);
            partida.getReloj().detener();
            System.out.println("¡VICTORIA! El equipo AZUL NAVAL ha ganado la partida.");
        } else if (azulDestruido) {
            partida.setGanador(TipoEquipo.ROJO_AEREO);
            partida.setEstado(EstadoPartida.FINALIZADA);
            partida.getReloj().detener();
            System.out.println("¡VICTORIA! El equipo ROJO AEREO ha ganado la partida.");
        }
    }
}
