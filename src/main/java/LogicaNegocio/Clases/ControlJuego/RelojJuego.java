package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Enums.TipoEquipo;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;


public class RelojJuego {

    private Instant inicio;
    private TipoEquipo equipoActual;
    private static Jugador jugadorActual;
    private Jugador jugadorPrevio;
    private String unidadActual; // se utiliza para si ya se movio un dron en este turno, que solo se pueda utilizar ese

    public RelojJuego (LinkedList<Jugador> jugadoresDeEquipoInicial){
        jugadorActual = jugadoresDeEquipoInicial.get(0);
        equipoActual = jugadorActual.getEquipo();
        inicio = Instant.now();
    }

    public static Jugador getJugadorActual() { return jugadorActual; }

    public void PasarTurno(LinkedList<Jugador> jugadoresRojos,LinkedList<Jugador>  jugadoresAzules) {

        equipoActual = equipoActual == TipoEquipo.ROJO_AEREO ? TipoEquipo.AZUL_NAVAL : TipoEquipo.ROJO_AEREO;

        LinkedList<Jugador> listaUsar = equipoActual == TipoEquipo.ROJO_AEREO ? jugadoresRojos : jugadoresAzules;

        int indiceActual = jugadorPrevio != null ? listaUsar.indexOf(jugadorPrevio) : 0;

        jugadorPrevio = jugadorActual;
        jugadorActual = listaUsar.get((indiceActual + 1) % listaUsar.size());
        unidadActual = null;
        inicio = Instant.now();
    }

    public boolean turnoExpirado (){
        return Instant.now().isAfter(inicio.plus(ReglasJuego.duracionTurno));
    }


    public int getSegundosRestantes() {
        Duration restante = getTiempoRestante();
        return Math.max(0, (int) restante.getSeconds());
    }

    private Duration getTiempoRestante() {
        Instant finTurno = inicio.plus(ReglasJuego.duracionTurno);
        Instant ahora = Instant.now();

        if (ahora.isAfter(finTurno)) {
            return Duration.ZERO;
        }

        return Duration.between(ahora, finTurno);
    }

    public String getUnidadActual() {
        return unidadActual;
    }

    public void setUnidadActual(String unidadActual) {
        this.unidadActual = unidadActual;
    }

    public boolean DronValidoParaTomarTurno (String idDron){
        return unidadActual == null || unidadActual.equals(idDron);
    }

    public TipoEquipo getEquipoActual() {return equipoActual;}
    public void setEquipoActual(TipoEquipo equipoActual) {this.equipoActual = equipoActual;}

}

