package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import LogicaNegocio.Enums.EstadoUnidad;

public abstract class PortaDrones extends Unidad {
    protected int vida;

    public PortaDrones(Posicion posicion, Equipo equipo) {
        super(posicion, equipo);
        combustibleMaximo = 4;
        recargaPorTurno = 2;
        combustibleActual = combustibleMaximo;
        estado = EstadoUnidad.EN_VUELO;
    }

    public void RecibirImpacto() {
        vida--;
        if (vida <= 0) {
            estado = EstadoUnidad.DESTRUIDO;
        }
    }
    public void reducirVida(){
        vida--;
    }

    public boolean EstaDestruido() {
        return estado == EstadoUnidad.DESTRUIDO;
    }

    public int getVida() { return vida; }
}
