package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import LogicaNegocio.Enums.EstadoUnidad;

public abstract class PortaDrones extends Unidad {
    protected int vida;

    public PortaDrones(Posicion posicion, Equipo equipo) {
        super(posicion, equipo);
        combustibleMaximo = 4;
        combustibleActual = combustibleMaximo;
        estado = EstadoUnidad.EN_VUELO;
        visionRango = 4;
    }

    public void reducirVida(){
        vida--;
    }

    public int getVida() { return vida; }
    public void setVidaPersistencia(int v) { this.vida = v; }
}
