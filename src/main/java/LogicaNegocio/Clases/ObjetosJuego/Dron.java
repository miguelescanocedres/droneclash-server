package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Enums.TipoArma;
import LogicaNegocio.Enums.TipoEquipo;

public abstract class Dron extends Unidad{
    protected TipoArma tipoArma;
    protected int municion;
    public Dron(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        combustibleMaximo = 4;
        recargaPorTurno = 2;
        // estos dos valores quedan definidos aca por ahora, mas adelante se define si cambia en base al tipo de dron
    }
}
