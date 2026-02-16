package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.*;

public class ReglasJuego {
    public static final int impactoAereo = 6;
    public static final int impactoNaval = 3;
    public static final int VisionBase = 8;
    public static final double FOVMisil = 0.5;

    public static boolean ValidarTurno(Jugador jugador, Partida partida) {
        return true;
    }

    public static boolean ValidarMovimiento(Unidad unidad, Posicion origen, Posicion destino, Tablero tablero) {

        if (!unidad.TieneEnergia()) {
            return false;
        }

        if (!tablero.estaDentroDelTablero(destino.getX(), destino.getY())) {
            return false;
        }

        if (tablero.getCelda(destino.getX(), destino.getY()).estaOcupada()) {
            return false;
        }

        int dx = Math.abs(destino.getX() - origen.getX());
        int dy = Math.abs(destino.getY() - origen.getY());
        if (dx + dy != 1) {
            return false;
        }

        return true;
    }

    public static boolean ValidarAtaque(Dron atacante,Posicion posicion) {


        if (atacante.getMunicion() <= 0) {
            System.err.println("Validación Fallida: El dron no tiene munición.");
            return false;
        }
        if (atacante.getCombustibleActual() == 0) {
            System.err.println("Validación Fallida: El dron no tiene combustible/puntos de acción.");
            return false;
        }

        if (atacante instanceof DronAereo) {
            return validarAtaqueBomba((DronAereo) atacante, posicion);
        } else if (atacante instanceof DronNaval) {
            return validarAtaqueMisil((DronNaval) atacante, posicion);
        }
        System.err.println("No existe el tipo de dron.");
        return false;
    }

    private static boolean validarAtaqueBomba(DronAereo atacante, Posicion posObjetivo) {
        Posicion posAtacante = atacante.getPosicion();
        int atacanteX = posAtacante.getX();
        int atacanteY = posAtacante.getY();
        int objetivoX = posObjetivo.getX();
        int objetivoY = posObjetivo.getY();

        boolean xValida = (objetivoX >= atacanteX - 1) && (objetivoX <= atacanteX + 1);
        boolean yValida = (objetivoY >= atacanteY - 1) && (objetivoY <= atacanteY + 1);

        if (xValida && yValida) {
            return true;
        } else {
            System.err.println("Fallido (Bomba): El objetivo está fuera del rango de 3x3.");
            return false;
        }
    }

    private static boolean validarAtaqueMisil(DronNaval atacante, Posicion posObjetivo) {
        final int alcanceMaximo = 4;
        Posicion posAtacante = atacante.getPosicion();

        if (posAtacante.getX() == posObjetivo.getX() && posAtacante.getY() == posObjetivo.getY()) {
            System.err.println("Fallido (Misil): No se puede disparar a la propia casilla.");
            return false;
        }

        int distanciaX = Math.abs(posObjetivo.getX() - posAtacante.getX());
        int distanciaY = Math.abs(posObjetivo.getY() - posAtacante.getY());

        boolean esLineaRecta = (distanciaX == 0 || distanciaY == 0);
        boolean esDiagonal = (distanciaX == distanciaY);


        if (!esLineaRecta && !esDiagonal) {
            System.err.println("Fallida (Misil): El objetivo no está en una línea recta o diagonal.");
            return false;
        }

        int distanciaReal = Math.max(distanciaX, distanciaY);

        if (distanciaReal <= alcanceMaximo) {
            return true;
        } else {
            System.err.println(" El objetivo está fuera del alcance máximo.");
            return false;
        }
    }


    public static void AplicarImpacto(Dron atacante, Unidad objetivo, Partida partida) {

        if (objetivo == null) {
            System.out.println("Disparo a casilla vacia");
            return;
        }

        if (atacante.getPropietario().equals(objetivo.getPropietario())) {
            System.out.println("Intento de ataque a una unidad aliada");
            return;
        }

        boolean atacanteEsAereo = atacante instanceof DronAereo;
        boolean objetivoEsAereo = objetivo instanceof DronAereo || objetivo instanceof PortaDronesAereo;

        if (atacanteEsAereo == objetivoEsAereo) {
            System.out.println("ERROR, Las unidades son del mismo tipo (aire/mar).");
            return;
        }

        if (objetivo instanceof Dron) {
            System.out.println("IMPACTO: ¡Dron enemigo destruido!");
            partida.eliminarUnidad(objetivo);
        } else if (objetivo instanceof PortaDrones portaDronObjetivo) {

            portaDronObjetivo.reducirVida();
            System.out.println("PortaDrones enemigo dañado.");

            if (portaDronObjetivo.getVida() <= 0) {
                System.out.println("PortaDrones enemigo destruido!");
                partida.eliminarUnidad(portaDronObjetivo);
            }
        }
    }
}
