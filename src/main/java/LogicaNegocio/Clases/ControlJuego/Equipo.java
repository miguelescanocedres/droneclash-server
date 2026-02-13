package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ObjetosJuego.Dron;
import LogicaNegocio.Clases.ObjetosJuego.Jugador;
import LogicaNegocio.Clases.ObjetosJuego.PortaDrones;
import LogicaNegocio.Enums.TipoEquipo;

import java.awt.image.Kernel;
import java.util.LinkedList;

public class Equipo {
    TipoEquipo tipoEquipo;
    LinkedList<Jugador> jugadores;
    PortaDrones portaDrones;
    boolean listoParaJugar = false;

    public void generarFlota(TipoEquipo tipo){

    }

    public void distribuirUnidadesEntreJugadores(){

    }
    public Jugador ObtenerLider (){
        return new Jugador();
    }
}
