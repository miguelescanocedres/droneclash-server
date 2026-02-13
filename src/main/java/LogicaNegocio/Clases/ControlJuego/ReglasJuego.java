package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.Dron;
import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Clases.ObjetosJuego.Unidad;

public class ReglasJuego {
    public static final int impactoAereo = 6;
    public static final int impactoNaval = 3;
    public static final int VisionBase = 8;
    public static final double FOVMisil = 0.5;

    public static boolean ValidarTurno(Jugador jugador, Partida partida) {
        return true;
    }

    public static boolean ValidarMovimiento(Unidad unidad, Posicion origen, Posicion destino, Tablero tablero) {
        // 1. Verificar combustible
        if (!unidad.TieneEnergia()) {
            return false;
        }

        // 2. Verificar destino dentro del tablero
        if (!tablero.estaDentroDelTablero(destino.getX(), destino.getY())) {
            return false;
        }

        // 3. Verificar destino no ocupado
        if (tablero.getCelda(destino.getX(), destino.getY()).estaOcupada()) {
            return false;
        }

        // 4. Verificar adyacencia (4 direcciones, sin diagonales)
        int dx = Math.abs(destino.getX() - origen.getX());
        int dy = Math.abs(destino.getY() - origen.getY());
        if (dx + dy != 1) {
            return false;
        }

        return true;
    }

    public static boolean ValidarAtaque(Dron atacante, Unidad objetivo) {
        return true;
    }

    public static void AplicarImpacto(Dron atacante, Unidad objetivo) {
    }
}