package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.Dron;
import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Clases.ObjetosJuego.Unidad;

public class ReglasJuego {
    public static final int impactoAereo = 6;
    public static final int impactoNaval = 3;
    public static final int VisionBase = 8; // valor placeholder a definir
    public static final double FOVMisil = 0.5;

    public static boolean ValidarTurno (Jugador jugador,Partida partida){
        return true;
    }

    public static boolean ValidarMovimiento(Dron dron, Posicion origen,Posicion destino){
        return true;
    }
    public static boolean ValidarAtaque(Dron atacante, Unidad objetivo){
        return true;
    }

    public static void AplicarImpacto(Dron atacante,Unidad objetivo){

    }
}
