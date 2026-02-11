package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Enums.EstadoUnidad;
import LogicaNegocio.Enums.TipoEquipo;

public abstract class Unidad {
    protected Posicion posicion;
    protected Jugador propietario;
    protected EstadoUnidad estado;
    protected int visionRango;
    protected int combustibleMaximo; // El maximo y recarga por turno, no cambia segun el tipo de unidad?
                                   // yo lo definira en las clases hijos, idem vision pero eso ya se definio
    protected int recargaPorTurno;

    public  Unidad (Posicion posicion,Jugador propietario){
            this.posicion = posicion;
            this.propietario = propietario;
            // cuando se crea se encuentra en el portadrones, a revisar
    }

    public void RecargarTurno(){

    }

    public void ConsumirPunto (){

    }

    public boolean TieneEnergia (){
        return true;
    }
}
