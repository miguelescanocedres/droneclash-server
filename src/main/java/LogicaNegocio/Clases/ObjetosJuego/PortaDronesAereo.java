package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;

import java.util.LinkedList;

public class PortaDronesAereo extends PortaDrones{
    private LinkedList<DronAereo> drones;
    public PortaDronesAereo(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        vida = 6;
    }
}
