package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;

import java.util.LinkedList;

public class PortaDronesAcuatico extends PortaDrones{
    private LinkedList<DronAcuatico> drones;
    public PortaDronesAcuatico(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        vida = 3;
    }
    public void recibirDanio (){

    }

}
