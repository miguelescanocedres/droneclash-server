package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import LogicaNegocio.Enums.TipoArma;

public abstract class Dron extends Unidad {
    protected TipoArma tipoArma;
    protected int municion;

    public Dron(Posicion posicion, Equipo equipo) {
        super(posicion, equipo);
        combustibleMaximo = 4;
        recargaPorTurno = 2; // estos dos valores quedan definidos aca por ahora, mas adelante se define si cambia en base al tipo de dron
        combustibleActual = combustibleMaximo;
    }

    public void ConsumirMunicion() {
        if (municion > 0) {
            municion--;
        }
    }


    public TipoArma getTipoArma() { return tipoArma; }
    public int getMunicion() { return municion; }
}
