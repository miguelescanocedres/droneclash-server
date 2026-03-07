package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import LogicaNegocio.Enums.TipoArma;

public abstract class Dron extends Unidad {
    protected TipoArma tipoArma;
    protected int municionMaxima;
    protected int municionActual;

    public Dron(Posicion posicion, Equipo equipo) {
        super(posicion, equipo);
        combustibleMaximo = 4;
        combustibleActual = combustibleMaximo;
    }

    public void ConsumirMunicion() {
        if (municionActual > 0) {
            municionActual--;
        }
    }

    public void RecargarMunicion(){
        this.municionActual = this.municionMaxima;
    }

    public int getMunicionActual() { return municionActual; }
    public void setMunicionPersistencia(int m)
    {
        this.municionMaxima = m;
        this.municionActual = m;
    }
}
