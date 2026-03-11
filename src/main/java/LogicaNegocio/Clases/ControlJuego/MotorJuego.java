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
    }


    public Partida GetPartidaActual() {
        return partidaActual;
    }

    private void validarPartidaEnCurso() throws ReglaJuegoException {
        if (partidaActual.getEstado() != EstadoPartida.EN_CURSO) {
            throw new ReglaJuegoException("No se puede realizar acciones: la partida no esta en curso.");
        }
    }


    public synchronized void ProcesarMoverDron(String dronId, int targetX, int targetY) throws ReglaJuegoException {
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
        partidaActual.getReloj().setUnidadActual(dron.getId());



        if(dron.SinMovimientos()) {
            partidaActual.getReloj().PasarTurno(partidaActual.getEquipoRojo().getJugadores(), partidaActual.getEquipoAzul().getJugadores());
            dron.RecargarTurno();
            dron.RecargarMunicion();
            partidaActual.EvaluarVictoria();
        }


        System.out.println("--- MOVIMIENTO DE DRON COMPLETADO ---");
    }


    public synchronized void ProcesarMoverPortaDrones(String portaDronesId, int targetX, int targetY) throws ReglaJuegoException {
        validarPartidaEnCurso();
        Unidad unidad = partidaActual.buscarUnidadPorId(portaDronesId);
        if (!(unidad instanceof PortaDrones)) {
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
        partidaActual.getReloj().setUnidadActual(pd.getId());
        if(pd.SinMovimientos()) {
            partidaActual.getReloj().PasarTurno(partidaActual.getEquipoRojo().getJugadores(), partidaActual.getEquipoAzul().getJugadores());
            pd.RecargarTurno();
        }
    }

    public synchronized void ProcesarDispararDron(String dronId, int targetX, int targetY) throws ReglaJuegoException {
        validarPartidaEnCurso();
        Unidad unidadAtacante = partidaActual.buscarUnidadPorId(dronId);
        Dron atacante = (Dron) unidadAtacante;
        Posicion posObjetivo = new Posicion(targetX, targetY);


        boolean esDisparoValido = ReglasJuego.ValidarAtaque(atacante, posObjetivo);
        if (!esDisparoValido) {
            throw new ReglaJuegoException("El ataque fue invalidado por Reglas de juego");
        }
        System.out.println("Disparo validado");
        Unidad unidadObjetivo = partidaActual.getTablero().getCelda(targetX, targetY).getUnidad();

        if(unidadObjetivo.getEquipo().getTipoEquipo() == unidadAtacante.getEquipo().getTipoEquipo())
            throw new ReglaJuegoException("No se puede atacar a unidades de tu mismo equipo");

        atacante.ConsumirMunicion();
        atacante.ConsumirCombustible();

        ReglasJuego.AplicarImpacto(atacante, unidadObjetivo, partidaActual);
        partidaActual.getReloj().setUnidadActual(unidadAtacante.getId());

        if(unidadAtacante.SinMovimientos() && partidaActual.getEstado() == EstadoPartida.EN_CURSO) {
            TipoEquipo equipoAntes = partidaActual.getReloj().getEquipoActual();
            partidaActual.getReloj().PasarTurno(partidaActual.getEquipoRojo().getJugadores(), partidaActual.getEquipoAzul().getJugadores());
            unidadAtacante.RecargarTurno();
            ((Dron) unidadAtacante).RecargarMunicion();
            partidaActual.EvaluarVictoria();
        }
             // no estoy seguro si va aca pero creo que no hay otro lugar logico



    }




    private void EvaluarVictoria() {
        Partida partida = this.partidaActual;
        if (partida.getEstado() != EstadoPartida.EN_CURSO) return;

        PortaDrones portaRojo = partida.getEquipoRojo().getPortaDrones();
        PortaDrones portaAzul = partida.getEquipoAzul().getPortaDrones();

        boolean rojoDestruido = partida.getEquipoRojo().getPortaDrones() == null  || portaRojo.getVida() <= 0;
        boolean azulDestruido = portaAzul == null || portaAzul.getVida() <= 0;
        if (partida.isUltimoTurno()) {
            if(rojoDestruido && azulDestruido){
                partida.setEstado(EstadoPartida.EMPATE);
                partida.detenerLoop();
                System.out.println("Ambos equipos destruyeron el portadron, empate");
            }else if (rojoDestruido) {
                partida.setGanador(TipoEquipo.AZUL_NAVAL);
                partida.setEstado(EstadoPartida.FINALIZADA);
                partida.detenerLoop();
                System.out.println("¡VICTORIA! El equipo AZUL NAVAL ha ganado la partida.");
            } else if (azulDestruido) {
                partida.setGanador(TipoEquipo.ROJO_AEREO);
                partida.setEstado(EstadoPartida.FINALIZADA);
                partida.detenerLoop();
                System.out.println("¡VICTORIA! El equipo ROJO AEREO ha ganado la partida.");
            }
        }
        else if(rojoDestruido || azulDestruido)
                partida.setUltimoTurno(true);
    }

    public synchronized void ProcesarPasarTurno() throws ReglaJuegoException {
        validarPartidaEnCurso();
        TipoEquipo equipoAntes = partidaActual.getReloj().getEquipoActual();
        String idUnidadActual = partidaActual.getReloj().getUnidadActual();
        if (idUnidadActual != null) {
            Unidad unidad = partidaActual.buscarUnidadPorId(idUnidadActual);
            if (unidad != null) unidad.RecargarTurno();
        }
        partidaActual.getReloj().PasarTurno(
                partidaActual.getEquipoRojo().getJugadores(),
                partidaActual.getEquipoAzul().getJugadores()
        );
    }

    public void SobreEscribirPartida (Partida partida) {
        this.partidaActual = partida;
    }
}
