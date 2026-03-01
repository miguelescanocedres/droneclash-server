package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;

import java.util.LinkedList;

public class PortaDronesNaval extends PortaDrones{
    private LinkedList<DronNaval> drones;
    public PortaDronesNaval(Posicion posicion, Equipo propietario) {
        super(posicion, propietario);
        vida = 3;
    }
    public void recibirDanio (){

    }

}
