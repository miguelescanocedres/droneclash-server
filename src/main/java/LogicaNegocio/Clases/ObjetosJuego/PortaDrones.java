package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Enums.EstadoUnidad;

public abstract class PortaDrones extends Unidad{
    protected int vida;
    public PortaDrones(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        combustibleMaximo = 4;
        recargaPorTurno = 2;
        estado = EstadoUnidad.EN_VUELO;
    }
    public void RecibirImpacto (){

    }
    public boolean EstaDestruido (){
        return true;
    }
}
