package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;

import java.util.LinkedList;

public class PortaDronesAereo extends PortaDrones{
    private LinkedList<DronAereo> drones;
    public PortaDronesAereo(Posicion posicion, Equipo propietario) {
        super(posicion, propietario);
        vida = 6;
    }
}
