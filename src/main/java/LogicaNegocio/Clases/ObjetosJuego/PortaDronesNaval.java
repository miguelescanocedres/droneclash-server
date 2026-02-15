package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;

import java.util.LinkedList;

public class PortaDronesNaval extends PortaDrones{
    private LinkedList<DronNaval> drones;
    public PortaDronesNaval(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        vida = 3;
    }
    public void recibirDanio (){

    }

}
